package com.shuige.weblog.jwt.filter;

import com.shuige.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author felix
 * @date 2024/6/26 14:53
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 处理过滤请求以进行JWT认证。
     * 从请求头中提取JWT令牌，并验证其有效性。如果令牌有效，将根据令牌中的用户名加载用户详细信息，
     * 并在安全上下文中设置认证信息。
     * @param request HTTP请求对象，用于获取请求头中的JWT令牌。
     * @param response HTTP响应对象，用于在认证失败时设置响应状态码和消息。
     * @param filterChain 过滤器链对象，用于继续请求处理。
     * @throws ServletException 如果发生与Servlet相关的异常。
     * @throws IOException 如果发生与IO相关的异常。
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/admin")){
            //从请求头中获取Authorization字段，其中包含JWT令牌。
            //从请求头中获取key为Authorization的值
            String header = request.getHeader(tokenHeaderKey);

            // 检查令牌是否以Bearer开头。
            if(StringUtils.startsWith(header,tokenPrefix)){
                // 提取Bearer后面的JWT令牌。
                String token = StringUtils.substring(header,7);
                log.info("Token:{}",token);

                // 如果令牌不为空，尝试验证令牌的有效性。
                if(StringUtils.isNotBlank(token)){
                    try {
                        // 验证JWT令牌的格式和签名是否正确。
                        jwtTokenHelper.validateToken(token);
                    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                        // 如果令牌格式错误或签名不匹配，表示令牌无效，通过authenticationEntryPoint处理异常。
                        authenticationEntryPoint.commence(request,response,new AuthenticationServiceException("Token不可用"));
                        return;
                    } catch(ExpiredJwtException e){
                        // 如果令牌过期，通过authenticationEntryPoint处理异常。
                        authenticationEntryPoint.commence(request,response,new AuthenticationServiceException("Token已失效"));
                        return;
                    }

                    // 从令牌中提取用户名。
                    String username = jwtTokenHelper.getUsernameByToken(token);

                    // 如果用户名不为空，并且当前安全上下文中没有认证信息，则加载用户详细信息并创建新的认证对象。
                    if(StringUtils.isNotBlank(username)
                            && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // 创建新的认证对象，包含用户详细信息、权限和认证详情。
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        // 设置认证详情，通常包含请求的URL和参数。
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 在安全上下文中设置新的认证对象。
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }

        // 继续处理请求，将请求传递给下一个过滤器或处理器。
        //继续执行下一个过滤器
       filterChain.doFilter(request,response);
    }
}
