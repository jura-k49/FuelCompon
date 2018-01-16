package net.ukr.jura.compon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseProvider;
import net.ukr.jura.compon.custom_components.BaseStaticListAdapter;
import net.ukr.jura.compon.components.ParamComponent;
import net.ukr.jura.compon.components.WorkWithRecordsAndViews;

public class StaticListAdapter extends BaseStaticListAdapter {

    private ParamComponent mvParam;
    private BaseProvider provider;
    private WorkWithRecordsAndViews modelToView;
    private Context context;

    public StaticListAdapter(BaseComponent baseComponent) {
        this.provider = baseComponent.provider;
        context = baseComponent.iBase.getBaseActivity();
        mvParam = baseComponent.paramMV;
        modelToView = new WorkWithRecordsAndViews();
    }
    @Override
    public int getCount() {
        return provider.getCount();
    }

    @Override
    public View getView(int position) {
        View view = LayoutInflater.from(context).inflate(mvParam.paramView.layoutTypeId[0], null);
        modelToView.RecordToView(provider.get(position), view, null, null);
        return view;
    }

    @Override
    public void onClickView(View view, View viewElrment, int position) {

    }
}
