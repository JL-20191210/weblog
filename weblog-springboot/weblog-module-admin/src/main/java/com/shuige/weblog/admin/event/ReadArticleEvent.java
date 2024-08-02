package com.shuige.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author felix
 * @date 2024/8/2 16:50
 */
@Getter
public class ReadArticleEvent extends ApplicationEvent {

    /**
     * 文章ID
     */
    private Long articleId;

    public ReadArticleEvent(Object source,Long articleId){
        super(source);
        this.articleId = articleId;
    }
}
