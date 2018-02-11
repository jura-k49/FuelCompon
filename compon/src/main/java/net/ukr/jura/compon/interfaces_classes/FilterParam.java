package net.ukr.jura.compon.interfaces_classes;

public class FilterParam {
    public String nameField;
    public enum Operation {equally, more, less};
    public Operation oper;
    public Object value;

    public FilterParam(String nameField, Operation oper, Object value) {
        this.nameField = nameField;
        this.oper = oper;
        this.value = value;
    }
}
