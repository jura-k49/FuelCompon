package net.ukr.jura.compon.interfaces_classes;

public interface IComponent {
    public void setData(Object data);
    public String getAlias();
    public Object getData();
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener);
    public String getString();
}
