package net.ukr.jura.compon.interfaces_classes;

import net.ukr.jura.compon.param.ParamModel;

public class ViewHandler {
    public int viewId;
    public enum TYPE {NAME_FRAGMENT, CLOSE_DRAWER, MODEL_PARAM,
        BACK, PREFERENCE_SET_VALUE, PAGER_PLUS, PREFERENCE_SET_TOKEN,
        FIELD_WITH_NAME_FRAGMENT, SELECT, // SEND_BACK_SCREEN,
        CLICK_SEND, SEND_UPDATE, SEND_CHANGE_BACK}
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
    public int[] mustValid;
    public boolean changeEnabled;
    public boolean[] validArray;
    public String nameFieldWithValue;
    public ActionsAfterResponse afterResponse;

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

    public ViewHandler(int viewId, TYPE type, ParamModel paramModel, String nameScreen) {
        this.type = type;
        this.viewId = viewId;
        this.nameFragment = nameScreen;
        this.paramModel = paramModel;
    }

    public ViewHandler(int viewId, TYPE type, ParamModel paramModel,
                       String nameScreen, boolean changeEnabled, int... mustValid) {
        this.type = type;
        this.viewId = viewId;
        this.nameFragment = nameScreen;
        this.mustValid = mustValid;
        this.changeEnabled = changeEnabled;
        this.paramModel = paramModel;
    }

    public ViewHandler(int viewId, TYPE type, ParamModel paramModel,
                       ActionsAfterResponse afterResponse, boolean changeEnabled, int... mustValid) {
        this.type = type;
        this.viewId = viewId;
        this.afterResponse = afterResponse;
        this.mustValid = mustValid;
        this.changeEnabled = changeEnabled;
        this.paramModel = paramModel;
    }

    public ViewHandler(int viewId, String namePreference, boolean value) {
        this.type = TYPE.PREFERENCE_SET_VALUE;
        this.viewId = viewId;
        this.namePreference = namePreference;
        typePref = TYPE_PREFER.BOOLEAN;
        pref_value_boolean = value;
    }

    public ViewHandler(int viewId, String namePreference, String value) {
        this.type = TYPE.PREFERENCE_SET_VALUE;
        this.viewId = viewId;
        this.namePreference = namePreference;
        typePref = TYPE_PREFER.STRING;
        pref_value_string = value;
    }

    public ViewHandler(int viewId, TYPE type) {
        this.type = type;
        this.viewId = viewId;
        this.paramModel = null;
    }

    public ViewHandler(int viewId, TYPE type, String nameFieldWithValue) {
        this.type = type;
        this.viewId = viewId;
        this.nameFieldWithValue = nameFieldWithValue;
        this.paramModel = null;
    }

    public ViewHandler(int viewId, SendAndUpdate sendAndUpdate) {
        type = TYPE.SEND_UPDATE;
        this.viewId = viewId;
        this.sendAndUpdate = sendAndUpdate;
    }
}
