package net.ukr.jura.compon.base;

import android.content.Context;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.models.MultiComponents;
import net.ukr.jura.compon.tools.Constants;

import java.util.Map;

public class ListScreens {
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

    protected MultiComponents addActivity(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }
}
