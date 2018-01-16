package net.ukr.jura.compon.interfaces_classes;

import net.ukr.jura.compon.components.ParamModel;

public class ViewHandler {
    public int viewId;
    public enum TYPE {NAME_FRAGMENT, CLOSE_DRAWER, MODEL_PARAM,
        BACK,
        FIELD_WITH_NAME_FRAGMENT, SELECT,
        SEND_UPDATE, SEND_CHANGE_BACK}
    public TYPE type;
    public String nameFragment;
    public ParamModel paramModel;
    public SendAndUpdate sendAndUpdate;

    public ViewHandler(String nameField) {
        type = TYPE.FIELD_WITH_NAME_FRAGMENT;
        this.viewId = 0;
        this.nameFragment = nameField;
    }

    public ViewHandler(int viewId, String nameFragment) {
        type = TYPE.NAME_FRAGMENT;
        this.viewId = viewId;
        this.nameFragment = nameFragment;
    }

    public ViewHandler(int viewId, ParamModel paramModel) {
        type = TYPE.MODEL_PARAM;
        this.viewId = viewId;
        this.paramModel = paramModel;
    }

    public ViewHandler(int viewId, TYPE type, ParamModel paramModel) {
        this.type = type;
        this.viewId = viewId;
        this.paramModel = paramModel;
    }

    public ViewHandler(int viewId, TYPE type) {
        this.type = type;
        this.viewId = viewId;
        this.paramModel = null;
    }

    public ViewHandler(int viewId, SendAndUpdate sendAndUpdate) {
        type = TYPE.SEND_UPDATE;
        this.viewId = viewId;
        this.sendAndUpdate = sendAndUpdate;
    }
}
