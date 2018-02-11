package net.ukr.jura.compon.interfaces_classes;

import net.ukr.jura.compon.components.ParamModel;

import java.util.ArrayList;
import java.util.List;

public class Navigator {
    public List<ViewHandler> viewHandlers = new ArrayList<>();

    public Navigator add(String fieldNameFragment) {
        viewHandlers.add(new ViewHandler(fieldNameFragment));
        return this;
    }

    public Navigator add(int viewId, String nameFragment) {
        viewHandlers.add(new ViewHandler(viewId, nameFragment));
        return this;
    }

    public Navigator add(int viewId, String nameFragment, ViewHandler.TYPE_PARAM_FOR_SCREEN paramForScreen) {
        viewHandlers.add(new ViewHandler(viewId, nameFragment, paramForScreen));
        return this;
    }

    public Navigator add(int viewId, ParamModel paramModel) {
        viewHandlers.add(new ViewHandler(viewId, paramModel));
        return this;
    }

    public Navigator add(int viewId, ViewHandler.TYPE type, ParamModel paramModel) {
        viewHandlers.add(new ViewHandler(viewId, type, paramModel));
        return this;
    }

    public Navigator add(int viewId, String namePreference, boolean value) {
        viewHandlers.add(new ViewHandler(viewId, namePreference, value));
        return this;
    }

    public Navigator add(int viewId, String namePreference, String value) {
        viewHandlers.add(new ViewHandler(viewId, namePreference, value));
        return this;
    }

    public Navigator add(int viewId, ViewHandler.TYPE type) {
        viewHandlers.add(new ViewHandler(viewId, type));
        return this;
    }

    public Navigator add(int viewId, SendAndUpdate sendAndUpdate) {
        viewHandlers.add(new ViewHandler(viewId, sendAndUpdate));
        return this;
    }

    public Navigator addAll(ViewHandler[] viewHandlers) {
        for (ViewHandler vh : viewHandlers) {
            this.viewHandlers.add(vh);
        }
        return this;
    }
}
