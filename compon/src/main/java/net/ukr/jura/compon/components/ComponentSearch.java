package net.ukr.jura.compon.components;

import android.widget.EditText;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;

public class ComponentSearch extends BaseComponent {

    EditText search;

    public ComponentSearch(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        search = (EditText) parentLayout.findViewById(paramMV.paramView.viewId);
    }

    @Override
    public void changeData(Field field) {

    }
}
