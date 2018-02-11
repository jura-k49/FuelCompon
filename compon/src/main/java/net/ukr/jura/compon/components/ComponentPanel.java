package net.ukr.jura.compon.components;

import android.util.Log;

import net.ukr.jura.compon.ComponGlob;
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
        if (field.value instanceof Record) {
            Record rec = (Record) field.value;
            viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
//        WorkWithRecordsAndViews rv = new WorkWithRecordsAndViews();
            workWithRecordsAndViews.RecordToView(rec, viewComponent);
        } else {
            Log.d("SMPL", "Тип данных не Record в " + paramMV.nameParentComponent);
        }
    }

    public ComponentPanel(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }
}
