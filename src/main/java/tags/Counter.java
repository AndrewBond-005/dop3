package tags;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

@FacesComponent("counter")
public class Counter extends UIComponentBase {

    @Override
    public String getFamily() {
        return "custom.textAnalyzer";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        String id = (String) getAttributes().get("id");
        Boolean excludeSpaces = (Boolean) getAttributes().get("excludeSpaces");
        String show = (String) getAttributes().get("show");
        String text = getTextFromBean(context);
        if (text == null) text = "";
        if (id == null) id = getClientId();
        if (excludeSpaces == null) excludeSpaces = false;
        if (show == null || show.isEmpty()) show = "all";
        if (text == null) text = "";

        int lines = 0, words = 0, chars = 0;
        if (text != null && !text.isEmpty()) {
            lines = text.split("\n", -1).length;
            String trimmed = text.trim();
            if (!trimmed.isEmpty()) {
                words = trimmed.split("\\s+").length;
            }
            if (excludeSpaces) {
                chars = text.replaceAll("\\s", "").length();
            } else {
                chars = text.length();
            }
        }

        renderResult(writer, id, show, excludeSpaces, lines, words, chars, text);
    }

    private String getTextFromBean(FacesContext context) {
            ELContext elContext = context.getELContext();
            ExpressionFactory factory =
                    context.getApplication().getExpressionFactory();
            ValueExpression ve = factory.createValueExpression(elContext,
                            "#{counterBean.inputText}", String.class);
            Object value = ve.getValue(elContext);
            return value != null ? value.toString() : "";
    }
    private void renderResult(ResponseWriter writer, String id, String show,
                              Boolean excludeSpaces, int lines, int words, int chars,
                              String text) throws IOException {
        writer.startElement("div", this);
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("class", "text-analyzer-result", null);
        writer.writeAttribute("class", "statistics", null);
        if ("all".equals(show) ||"lines".equals(show)) {writer.write("Строк: " + lines+"<br>");}
        if ("all".equals(show) || "words".equals(show)) {writer.write("Слов: " + words+"<br>");}
        if ("all".equals(show) ||"chars".equals(show)) {writer.write("Символов: " + chars+"<br>");}
        if (text == null || text.trim().isEmpty()) {
            text ="";
        }
        writer.startElement("pre", this);
        writer.writeText(text, null);
        writer.endElement("pre");
        writer.endElement("div");
    }
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeChildren(FacesContext context) throws IOException {
    }
}