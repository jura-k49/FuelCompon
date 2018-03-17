package net.ukr.jura.compon.param;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.interfaces_classes.Navigator;

public class ParamComponent <T>{
    public static enum TC {PANEL, PANEL_ENTER, PANEL_MULTI, SPINNER,
        RECYCLER, RECYCLER_HORIZONTAL, RECYCLER_GRID, RECYCLER_EXPANDED, RECYCLER_STICKY,
        MENU, CONTAINER, MAP, SPLASH, BUTTON, PHONE, TOTAL, SEARCH,
        STATIC_LIST, MODEL, PAGER_V, PAGER_F};
    public ParamComponent () {
        additionalWork = null;
    }
    public String nameParentComponent;
    public String name;
    public TC type;
    public int fragmentsContainerId;
    public String nameStartFragment;
//    public BaseComponent component;
    public int eventComponent;
    public MoreWork moreWork;
    public BaseComponent baseComponent;
//    public String additionalWork;
    public ParamModel paramModel;
    public ParamView paramView;
    public ParamMap paramMap;
    public Navigator navigator;
    public String tutorial, auth, main;
    public Class<T> additionalWork;
    public int[] mustValid;
    public int viewSearchId;
}
