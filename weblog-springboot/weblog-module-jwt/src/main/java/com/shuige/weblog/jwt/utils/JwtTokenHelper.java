package com.shuige.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

@Component
public class JwtTokenHelper implements InitializingBean {
    /**
     * 签发人
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * 秘钥
     */

    private Key key;

    /**
     * JWT解析
     */
    private JwtParser jwtParser;

    /**
     * 解码配置文件中的base64编码Key为秘钥
     * @param base64Key
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key){
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 初始化JWTParser
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 考虑到不同服务器之间可能存在时钟偏移，setAllowedClockSkewSeconds 用于设置能够容忍的最大的时钟误差
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 生成Token
     * @param username
     * @return
     */
    public String generateToken(String username){
        LocalDateTime now = LocalDateTime.now();

        //Token一个小时后失效
        LocalDateTime expiredTime = now.plusHours(1);

        return Jwts.builder().setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token){
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 生成一个base64安全秘钥
     * @return
     */
    private static String generateBase64Key(){
        //生成安全秘钥
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        //将秘钥进行base64编码
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return base64Key;

    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println(key);
    }
}
