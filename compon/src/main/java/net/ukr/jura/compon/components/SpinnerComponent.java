package net.ukr.jura.compon.components;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.adapters.SpinnerAdapter;
import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseProvider;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.param.ParamComponent;

public class SpinnerComponent extends BaseComponent {
    Spinner spinner;
//    ListRecords listData;

    @Override
    public void initView() {
        spinner = (Spinner) parentLayout.findViewById(paramMV.paramView.viewId);
//        String st = baseActivity.installParam(paramMV.paramModel.param);
    }

    @Override
    public void changeData(Field field) {
        listData = (ListRecords) field.value;
        BaseProvider provider = new BaseProvider(listData);
//        provider.setData(listSpinner);
        SpinnerAdapter adapter = new SpinnerAdapter(provider, paramMV);
        spinner.setAdapter(adapter);
//            spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Record record = listData.get(position);
                ComponGlob.getInstance().setParam(record);
                iBase.sendEvent(paramMV.paramView.viewId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public SpinnerComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }
}
