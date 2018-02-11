package net.ukr.jura.compon.interfaces_classes;

import net.ukr.jura.compon.components.ParamModel;

public class ViewHandler {
    public int viewId;
    public enum TYPE {NAME_FRAGMENT, CLOSE_DRAWER, MODEL_PARAM,
        BACK, PREFERENCE_SET_VALUE, PAGER_PLUS,
        FIELD_WITH_NAME_FRAGMENT, SELECT,
        SEND_UPDATE, SEND_CHANGE_BACK}
    public TYPE type;
    public String nameFragment;
    public ParamModel paramModel;
    public SendAndUpdate sendAndUpdate;
    public enum TYPE_PREFER {BOOLEAN, STRING};
    public TYPE_PREFER typePref;
    public enum TYPE_PARAM_FOR_SCREEN {NONE, RECORD, LIST_RECORD};
    public TYPE_PARAM_FOR_SCREEN paramForScreen = TYPE_PARAM_FOR_SCREEN.NONE;
    public boolean pref_value_boolean;
    public String pref_value_string;
    public String namePreference;

    public ViewHandler(String nameField) {
        type = TYPE.FIELD_WITH_NAME_FRAGMENT;
        this.viewId = 0;
        this.nameFragment = nameField;
    }

    public ViewHandler(int viewId, String nameFragment) {
        type = TYPE.NAME_FRAGMENT;
        paramForScreen = TYPE_PARAM_FOR_SCREEN.NONE;
        this.viewId = viewId;
        this.nameFragment = nameFragment;
    }

    public ViewHandler(int viewId, String nameFragment, TYPE_PARAM_FOR_SCREEN paramForScreen) {
        type = TYPE.NAME_FRAGMENT;
        this.paramForScreen = paramForScreen;
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

    public ViewHandler(int viewId, String namePreference, boolean value) {
        this.type = TYPE.PREFERENCE_SET_VALUE;
        this.viewId = viewId;
        this.namePreference = namePreference;
        typePref = TYPE_PREFER.BOOLEAN.BOOLEAN;
        pref_value_boolean = value;
    }

    public ViewHandler(int viewId, String namePreference, String value) {
        this.type = TYPE.PREFERENCE_SET_VALUE;
        this.viewId = viewId;
        this.namePreference = namePreference;
        typePref = TYPE_PREFER.BOOLEAN.STRING;
        pref_value_string = value;
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
