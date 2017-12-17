package net.ukr.jura.compon.base;

import android.content.Context;

import net.ukr.jura.compon.models.MultiComponents;

import java.util.Map;

public class ListScreens {
    private Map<String, MultiComponents> MapScreen;
    protected Context context;
    public void initScreen() {
    }

    public ListScreens(Context context) {
        this.context = context;
    }

    public void setMapScreen(Map<String, MultiComponents> MapScreen) {
        this.MapScreen = MapScreen;
    }

    protected MultiComponents addFragment(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.Fragment;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addFragment(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.Fragment;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.Activity;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents addActivity(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.Activity;
        MapScreen.put(name, mc);
        return mc;
    }
}
