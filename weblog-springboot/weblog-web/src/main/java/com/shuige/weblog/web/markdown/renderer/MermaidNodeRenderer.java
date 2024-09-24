package com.shuige.weblog.web.markdown.renderer;

import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Set;

/**
 * @author felix
 * @date 2024/9/24 12:39
 */
public class MermaidNodeRenderer implements NodeRenderer {

    private final HtmlNodeRendererContext context;

    public MermaidNodeRenderer(HtmlNodeRendererContext context) {
        this.context = context;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        // 这个渲染器只处理 FencedCodeBlock 节点
        return Collections.singleton(FencedCodeBlock.class);
    }

    @Override
    public void render(Node node) {
        FencedCodeBlock codeBlock = (FencedCodeBlock) node;
        if ("mermaid".equals(codeBlock.getInfo())) {
            // 如果代码块类型是 mermaid，则包裹在 <div class="mermaid"> 中
            context.getWriter().line();
            context.getWriter().tag("div", Collections.singletonMap("class", "mermaid"));
            context.getWriter().raw(codeBlock.getLiteral());  // 输出 Mermaid 图的内容
            context.getWriter().tag("/div");
            context.getWriter().line();
        } else {
            // 对于其他类型的代码块，保持默认渲染
            context.getWriter().tag("pre");
            context.getWriter().tag("code");
            context.getWriter().text(codeBlock.getLiteral());
            context.getWriter().tag("/code");
            context.getWriter().tag("/pre");
        }
    }

    // 工厂类，用于创建 MermaidNodeRenderer 实例
    public static class Factory implements HtmlNodeRendererFactory {
        @Override
        public NodeRenderer create(HtmlNodeRendererContext context) {
            return new MermaidNodeRenderer(context);
        }
    }
}
