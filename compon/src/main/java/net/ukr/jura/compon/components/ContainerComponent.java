package net.ukr.jura.compon.components;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.param.ParamComponent;

public class ContainerComponent extends BaseComponent{

    public ContainerComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
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
