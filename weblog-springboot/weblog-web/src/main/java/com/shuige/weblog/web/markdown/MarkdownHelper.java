package com.shuige.weblog.web.markdown;

import com.shuige.weblog.web.markdown.provider.NofollowLinkAttributeProvider;
import com.shuige.weblog.web.markdown.renderer.ImageNodeRenderer;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

/**
 * @author felix
 * @date 2024/7/29 10:28
 */
public class MarkdownHelper {

    /* Markdown解析器 */
    private final static Parser PARSER;

    /* HTML渲染器 */
    private final static HtmlRenderer HTML_RENDERER;

    /* 初始化Markdown解析器 HTML渲染器 */
    static {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),// 表格拓展
                HeadingAnchorExtension.create(),// 标题锚定项
                ImageAttributesExtension.create(),// 图片宽高
                TaskListItemsExtension.create()// 任务列表
        );
        PARSER = Parser.builder().extensions(extensions).build();
        HTML_RENDERER = HtmlRenderer.builder()
                .extensions(extensions)
                .attributeProviderFactory(context->new NofollowLinkAttributeProvider())
                .nodeRendererFactory(context->new ImageNodeRenderer(context))
                .build();
    }

    public static String convertMarkdown2Html(String markdown) {
        // markdown 扩展
        Node parse = PARSER.parse(markdown);
        return HTML_RENDERER.render(parse);
    }

    public static void main(String[] args) {
        String markdown = "![图 1-1 技术栈](https://img.quanxiaoha.com/quanxiaoha/169560181378937 \"图 1-1 技术栈\"){width=100 height=100}";
        System.out.println(MarkdownHelper.convertMarkdown2Html(markdown));
    }
}
