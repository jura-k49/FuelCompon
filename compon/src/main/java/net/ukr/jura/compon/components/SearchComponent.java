package net.ukr.jura.compon.components;

import android.widget.EditText;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.param.ParamComponent;

public class SearchComponent extends BaseComponent {

    EditText search;

    public SearchComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        search = (EditText) parentLayout.findViewById(paramMV.paramView.viewId);
    }

    @Override
    public void changeData(Field field) {

    }
}
