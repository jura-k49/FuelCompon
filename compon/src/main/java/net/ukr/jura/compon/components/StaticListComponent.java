package net.ukr.jura.compon.components;

import android.util.Log;

import net.ukr.jura.compon.adapters.StaticListAdapter;
import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseProvider;
import net.ukr.jura.compon.custom_components.StaticList;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.param.ParamComponent;
import net.ukr.jura.compon.tools.StaticVM;

public class StaticListComponent extends BaseComponent {
    StaticList staticList;
//    ListRecords listData;
//    BaseProvider provider;
    StaticListAdapter adapter;

    public StaticListComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            staticList = (StaticList) StaticVM.findViewByName(parentLayout, "baseStaticList");
        } else {
            staticList = (StaticList) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (staticList == null) {
            Log.i("SMPL", "Не найден StaticList в " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
        provider = new BaseProvider(listData);
//        provider.setData(listData);
//        provider.setNavigator(paramMV.navigator);
        adapter = new StaticListAdapter(this);
        staticList.setAdapter(adapter, false);
    }

    @Override
    public void changeData(Field field) {
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        adapter.notifyDataSetChanged();
    }
}
