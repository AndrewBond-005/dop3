package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class CounterBean implements Serializable {

    private String inputText = "Это пример текста для подсчета.\n" +
            "В нём несколько строк.\n" +
            "И разные слова!";
    private Boolean excludeSpaces = false;
    private String show = "all";


    public void reset() {
        inputText = "";
        excludeSpaces = false;
        show = "all";
    }
    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText != null ? inputText : "";
    }

    public Boolean getExcludeSpaces() {
        return excludeSpaces;
    }

    public void setExcludeSpaces(Boolean excludeSpaces) {
        this.excludeSpaces = excludeSpaces != null ? excludeSpaces : false;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show != null ? show : "all";
    }

}