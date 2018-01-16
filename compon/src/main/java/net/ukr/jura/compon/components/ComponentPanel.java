package net.ukr.jura.compon.components;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;

public class ComponentPanel extends BaseComponent {
    @Override
    public void initView() {
    }

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        Record rec = (Record)field.value;
        viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
//        WorkWithRecordsAndViews rv = new WorkWithRecordsAndViews();
        workWithRecordsAndViews.RecordToView(rec, viewComponent);
    }

    public ComponentPanel(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }
}
