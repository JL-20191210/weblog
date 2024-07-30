package com.shuige.weblog.web.markdown.provider;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * @author felix
 * @date 2024/7/30 16:38
 */
public class NofollowLinkAttributeProvider implements AttributeProvider {
    /**
     * 网站域名（上线后需要改成自己的域名）
     */
    private final static String DOMAIN = "www.shuige.com";


    @Override
    public void setAttributes(Node node, String s, Map<String, String> attributes) {
        if(node instanceof Link){
            Link LinkNode = (Link)node;
            String href = LinkNode.getDestination();
            // 如果链接不是自己域名，则添加 rel="nofollow" 属性
            if(!href.contains(DOMAIN)){
                attributes.put("rel","nofollow");
            }
        }

    }
}
