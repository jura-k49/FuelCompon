package net.ukr.jura.compon.components;

import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.Navigator;

import java.util.ArrayList;
import java.util.List;

public class MultiComponents <T>{
    public String nameComponent;
    public List<ParamComponent> listComponents;
    public String title;
    public String[] args;
    public int fragmentLayoutId;
    public enum TYPE_VIEW {ACTIVITY, FRAGMENT, CUSTOM_FRAGMENT};
    public TYPE_VIEW typeView;
    public Navigator navigator;
    public Class<T> customFragment;

    public MultiComponents(String name, int layoutId, String title, String... args) {
        this.title = title;
        this.args = args;
        this.nameComponent = name;
        this.fragmentLayoutId = layoutId;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name, int layoutId) {
        this.title = "";
        this.args = null;
        this.nameComponent = name;
        this.fragmentLayoutId = layoutId;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name) {
        this.title = "";
        this.args = null;
        this.nameComponent = name;
        typeView = TYPE_VIEW.CUSTOM_FRAGMENT;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name, Class<T> customFragment) {
        this.nameComponent = name;
        this.customFragment = customFragment;
        listComponents = new ArrayList<>();
    }

    public T getCustomFragment() {
        try {
            return (T) customFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setName(String nameComponent) {
        this.nameComponent = nameComponent;
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView) {
        return addComponent(type, paramModel, paramView, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel) {
        return addComponent(type, paramModel, null, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator) {
        return addComponent(type, paramModel, paramView, navigator, 0, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type,
                                        ParamView paramView) {
        return addComponent(type, null, paramView, null, 0, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        int eventComponent) {
        return addComponent(type, paramModel, paramView, null, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        int eventComponent) {
        return addComponent(type, paramModel, null, null, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator,
                                        int eventComponent) {
        return addComponent(type, paramModel, paramView, navigator, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator,
                                        int eventComponent,
                                        Class<T> additionalWork) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = type;
        paramComponent.paramModel = paramModel;
        paramComponent.paramView = paramView;
        paramComponent.navigator = navigator;
        paramComponent.eventComponent = eventComponent;
        listComponents.add(paramComponent);
        paramComponent.nameParentComponent = nameComponent;
        paramComponent.additionalWork = additionalWork;
        return this;
    }

    public MultiComponents addComponentMap(int viewId, ParamModel paramModel, ParamMap paramMap) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.MAP;
        paramComponent.paramView = new ParamView(viewId);
        paramComponent.paramModel = paramModel;
        paramComponent.eventComponent = viewId;
        paramComponent.paramMap = paramMap;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addComponentMap(int viewId, ParamMap paramMap) {
        return addComponentMap(viewId, null, paramMap);
    }

    public MultiComponents addModel(String nameModel, ParamModel paramModel) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.MODEL;
        paramComponent.name = nameModel;
        paramComponent.paramModel = paramModel;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addModel(ParamModel paramModel) {
        return addModel(ParamModel.PARENT_MODEL, paramModel);
    }

    public MultiComponents addFragmentsContainer(int fragmentsContainerId) {
        return addFragmentsContainer(fragmentsContainerId, "");
    }

    public MultiComponents addFragmentsContainer(int fragmentsContainerId, String nameStartFragment) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.CONTAINER;
        paramComponent.fragmentsContainerId = fragmentsContainerId;
        paramComponent.nameStartFragment = nameStartFragment;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addNavigator(Navigator navigator) {
        this.navigator = navigator;
        return this;
    }

    public MultiComponents add(Navigator navigator) {
        this.navigator = navigator;
        return this;
    }

    public void initComponents(IBase iBase) {
//        Log.d("QWERT","__initComponents COUNT="+listComponents.size());
        for (ParamComponent cMV : listComponents) {
//            Log.d("QWERT","___initComponents TYPE="+cMV.type+" PARENT="+cMV.nameParentComponent);
            switch (cMV.type) {
                case PANEL :
                    new ComponentPanel(iBase, cMV);
                    break;
                case PANEL_MULTI :
                    new ComponentMultiPanel(iBase, cMV);
                    break;
                case PANEL_ENTER:
                    new ComponentEnterPanel(iBase, cMV);
                    break;
                case SPINNER :
                    new ComponentSpinner(iBase, cMV);
                    break;
                case RECYCLER_LEVEL:
                case RECYCLER_STICKY:
                case RECYCLER :
                case RECYCLER_HORIZONTAL :
                case RECYCLER_GRID :
                    new ComponentRecycler(iBase, cMV);
                    break;
                case MENU :
                    new ComponentMenu(iBase, cMV);
                    break;
                case STATIC_LIST :
                    new ComponentStaticList(iBase, cMV);
                    break;
                case PAGER_V:
                    new ComponentPagerV(iBase, cMV);
                    break;
                case PAGER_F:
                    new ComponentPagerF(iBase, cMV);
                    break;
                case MODEL:
                    new ComponentModel(iBase, cMV);
                    break;
                case CONTAINER:
                    new ComponentContainer(iBase, cMV);
                    break;
                case MAP:
                    new ComponentMap(iBase, cMV);
                    break;
            }
            cMV.baseComponent.init();
        }
    }

    public String getParamModel () {
        String st = getParamTitle();
        String sep = "";
        if (st.length() > 0) {
            sep = ",";
        }
        for (ParamComponent vp : listComponents) {
            String paramComponent = getParamApi(vp);
            if (paramComponent.length() > 0) {
                st += sep + paramComponent;
                sep = ",";
            }
        }
        return st;
    }

    public String getParamApi(ParamComponent mvp) {
        if (mvp != null && mvp.paramModel != null) {
            return mvp.paramModel.param;
        } else {
            return "";
        }
    }

    public String getParamTitle() {
        String st = "";
        if (args != null) {
            int ik = args.length;
            String sep = "";
            if (ik > 0) {
                for (int i = 0; i < ik; i++) {
                    st += sep + args[i];
                    sep = ",";
                }
            }
        }
        return st;
    }
}
