package net.ukr.jura.compon.models;

public class ParamView {
    public int viewId;
    public String fieldType;
    public int[] layoutTypeId, layoutFurtherTypeId;
    public int indicatorId;
    public int furtherViewId;
    public int tabId;
    public int arrayLabelId;
    public ParamModel paramModel;
    public String[] nameFragment;

    public ParamView(int viewId) {
        this(viewId, "", null, null);
    }

    public ParamView(int viewId, int layoutItemId) {
        this(viewId, "", new int[] {layoutItemId}, null);
    }

    public ParamView(int viewId, int layoutItemId, int layoutFurtherId) {
        this(viewId, "", new int[] {layoutItemId}, new int[] {layoutFurtherId});
    }

    public ParamView(int viewId, String fieldType, int[] layoutTypeId) {
        this(viewId, fieldType, layoutTypeId, null);
    }

    public ParamView(int viewId, String[] nameFragment) {
        this.viewId = viewId;
        this.fieldType = "";
        this.layoutTypeId = null;
        this.layoutFurtherTypeId = null;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = 0;
        paramModel = null;
        arrayLabelId = 0;
        this.nameFragment = nameFragment;
    }

    public ParamView(int viewId, String fieldType, int[] layoutTypeId, int[] layoutFurtherTypeId) {
        this.viewId = viewId;
        this.fieldType = fieldType;
        this.layoutTypeId = layoutTypeId;
        this.layoutFurtherTypeId = layoutFurtherTypeId;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = 0;
        paramModel = null;
        arrayLabelId = 0;
        nameFragment = null;
    }

    public ParamView setIndicator(int indicatorId) {
        this.indicatorId = indicatorId;
        return this;
    }

    public ParamView setTab(int tabId, ParamModel mp) {
        this.tabId = tabId;
        paramModel = mp;
        return this;
    }

    public ParamView setTab(int tabId, int arrayLabelId) {
        this.tabId = tabId;
        this.arrayLabelId = arrayLabelId;
        return this;
    }

    public ParamView setFurtherView(int furtherViewId) {
        this.furtherViewId = furtherViewId;
        return this;
    }
}
