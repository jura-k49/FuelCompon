package net.ukr.jura.compon.components;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;

public class ComponentContainer extends BaseComponent{

    public ComponentContainer(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        iBase.setFragmentsContainerId(paramMV.fragmentsContainerId);
        if (paramMV.nameStartFragment != null && paramMV.nameStartFragment.length() > 0) {
            iBase.startScreen(paramMV.nameStartFragment, false);
        }
    }

    @Override
    public void changeData(Field field) {

    }
}
