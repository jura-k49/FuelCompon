package net.ukr.jura.compon.base;

import android.content.Context;
import android.util.Log;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.compon.interfaces_classes.ActionsAfterResponse;
import net.ukr.jura.compon.interfaces_classes.Visibility;
import net.ukr.jura.compon.tools.Constants;

import java.util.Map;

public class ListScreens <T>{
    private Map<String, MultiComponents> MapScreen;
    protected Context context;

    public void initScreen() {
        for (MultiComponents value : MapScreen.values()) {
            String par = value.getParamModel();
            if (par != null && par.length() > 0) {
                String[] param = par.split(Constants.SEPARATOR_LIST);
                int ik = param.length;
                for (int i = 0; i < ik; i++) {
                    ComponGlob.getInstance().addParam(param[i]);
                }
            }
        }
    }

    public ListScreens(Context context) {
        this.context = context;
    }

    public String getString(int id) {
        return context.getString(id);
    }

    public void setMapScreen(Map<String, MultiComponents> MapScreen) {
        this.MapScreen = MapScreen;
    }

    protected MultiComponents addFragment(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addFragment(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addFragment(String name, Class customFragment) {
        MultiComponents mc = new MultiComponents(name, customFragment);
        mc.typeView = MultiComponents.TYPE_VIEW.CUSTOM_FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addCustomFragment(String name) {
        MultiComponents mc = new MultiComponents(name);
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId, Constants.AnimateScreen animate) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        mc.animateScreen = animate;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId, Class<T> additionalWork) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        mc.additionalWork = additionalWork;
        MapScreen.put(name, mc);
        return mc;
    }

    public static ActionsAfterResponse actionsAfterResponse() {
        return new ActionsAfterResponse();
    }

    public static Visibility[] showManager(Visibility ... args) {
        return args;
    }

    public static Visibility visibility(int viewId, String nameField) {
        return new Visibility(0, viewId, nameField);
    }

    public static Visibility enabled(int viewId, String nameField) {
        return new Visibility(1, viewId, nameField);
    }
}
