package net.ukr.jura.compon.components;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.ParentModel;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.param.ParamComponent;

public class ModelComponent extends BaseComponent {

    @Override
    public void initView() {

    }

    @Override
    public void changeData(Field field) {
        ParentModel pm = iBase.getParentModel(paramMV.name);
        pm.field = field;
        for (BaseComponent bc : pm.componentList) {
            bc.setParentData(field);
        }
    }

    public ModelComponent(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }
}
