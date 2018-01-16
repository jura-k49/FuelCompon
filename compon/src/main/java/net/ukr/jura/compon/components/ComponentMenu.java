package net.ukr.jura.compon.components;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.base.BaseActivity;
import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseProvider;
import net.ukr.jura.compon.base.BaseProviderAdapter;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.presenter.ListPresenter;
import net.ukr.jura.compon.tools.StaticVM;

public class ComponentMenu extends BaseComponent {
    RecyclerView recycler;
    ListRecords listData;
    BaseProviderAdapter adapter;

    public ComponentMenu(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) StaticVM.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            Log.i("SMPL", "Не найден RecyclerView для Menu в " + paramMV.nameParentComponent);
        }

        for (ViewHandler vh : navigator.viewHandlers) {
            if (vh.viewId == 0 && vh.type == ViewHandler.TYPE.FIELD_WITH_NAME_FRAGMENT) {
                selectViewHandler = vh;
                break;
            }
        }
        Log.d("QWERT","selectViewHandler="+selectViewHandler);
        listData = new ListRecords();
        listPresenter = new ListPresenter(this);
        provider = new BaseProvider(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recycler.setLayoutManager(layoutManager);
        adapter = new BaseProviderAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeData(Field field) {
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        int selectStart = -1;
        int ik = listData.size();
        for (int i = 0; i < ik; i++) {
            Record r = listData.get(i);
            int j = (Integer) r.getValue("select");
            if (j > 1) {
                selectStart = i;
                break;
            }
        }
        listPresenter.changeData(listData, selectStart);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void changeDataPosition(int position, boolean select) {
        adapter.notifyItemChanged(position);
        if (select && selectViewHandler != null) {
            ((BaseActivity) activity).closeDrawer();
            Record record = listData.get(position);
            ComponGlob.getInstance().setParam(record);
            iBase.startFragment(record.getString(selectViewHandler.nameFragment), true);
        }
    }

}
