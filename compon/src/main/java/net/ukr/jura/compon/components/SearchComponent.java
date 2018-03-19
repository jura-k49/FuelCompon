package net.ukr.jura.compon.components;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseProvider;
import net.ukr.jura.compon.base.BaseProviderAdapter;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.param.ParamComponent;
import net.ukr.jura.compon.tools.StaticVM;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchComponent extends BaseComponent {

    public View viewSearch;
    RecyclerView recycler;
    BaseProviderAdapter adapter;
    Handler handler = new Handler();

    public SearchComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        viewSearch = parentLayout.findViewById(paramMV.viewSearchId);
        if (viewSearch instanceof IComponent) {

        } else if (viewSearch instanceof EditText){
            ((EditText) viewSearch).addTextChangedListener(watcher);
        } else {
            Log.i("SMPL", "View для поиска должно быть IComponent или EditText в " + paramMV.nameParentComponent);
            return;
        }
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) StaticVM.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            Log.i("SMPL", "Не найден RecyclerView в " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
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
        adapter.notifyDataSetChanged();
        int splash = paramMV.paramView.splashScreenViewId;
        if (splash != 0) {
            View v_splash = parentLayout.findViewById(splash);
            if (v_splash != null) {
                if (listData.size() > 0) {
                    v_splash.setVisibility(GONE);
                } else {
                    v_splash.setVisibility(VISIBLE);
                }
            } else {
                Log.i("SMPL", "Не найден SplashView в " + paramMV.nameParentComponent);
            }
        }
        iBase.sendEvent(paramMV.paramView.viewId);
    }

    private TextWatcher watcher = new TextWatcher() {

        //        private boolean validEdit;
        private Runnable task;

        @Override
        public void afterTextChanged(Editable s) {
            final String st = s.toString();
            if(task != null) {
                handler.removeCallbacks(task);
            }
            task = new Runnable() {
                @Override
                public void run() {
                    String[] stAr = paramMV.paramModel.param.split(",");
                    String nameParam = stAr[0];
                    ComponGlob.getInstance().addParamValue(nameParam, st);
                    actual();
//                    if(changeSearchText != null) {
////                        if (validEdit) {
//                        changeSearchText.onChange(st);
////                        } else {
////                            changeSearchText.onChange("");
////                        }
//                    }
                }
            };
            handler.postDelayed(task, 700);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            validEdit = after > 0;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };
}
