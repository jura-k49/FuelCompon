package net.ukr.jura.compon.components;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.custom_components.PagerIndicator;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.WorkWithRecordsAndViews;
import net.ukr.jura.compon.param.ParamComponent;
import net.ukr.jura.compon.tools.PreferenceTool;
import net.ukr.jura.compon.tools.StaticVM;

public class PagerVComponent extends BaseComponent {
    ViewPager pager;
    ListRecords listData;
    PagerIndicator indicator;
    private View further;
    int count;
    private LayoutInflater inflater;
    private WorkWithRecordsAndViews modelToFurther = new WorkWithRecordsAndViews();

    public PagerVComponent(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            pager = (ViewPager) StaticVM.findViewByName(parentLayout, "pager");
        } else {
            pager = (ViewPager) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (pager == null) {
            Log.i("SMPL", "Не найден ViewPager в " + paramMV.nameParentComponent);
        }
        listData = new ListRecords();
    }

    @Override
    public void changeData(Field field) {
        if (field != null) {
            listData.clear();
            listData.addAll((ListRecords) field.value);
            count = listData.size();
            if (count > 0) {
                inflater = (LayoutInflater) iBase.getBaseActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                if (paramMV.paramView.indicatorId != 0) {
                    indicator = (PagerIndicator) parentLayout.findViewById(paramMV.paramView.indicatorId);
                    indicator.setCount(count);
                }
                if (paramMV.paramView.furtherViewId != 0) {
                    further = (View) parentLayout.findViewById(paramMV.paramView.furtherViewId);
                    modelToFurther.RecordToView(listData.get(0), further, navigator, listener,
                            paramMV.paramView.visibilityArray);
                }
                pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (indicator != null) {
                            indicator.setSelect(position);
                        }
                        if (further != null) {
                            modelToFurther.RecordToView(listData.get(position), further, navigator, listener,
                                    paramMV.paramView.visibilityArray);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                pager.setAdapter(adapter);
                if (count == 1) {
                    if (further != null) {
                        modelToFurther.RecordToView(listData.get(0), further, navigator, listener, null);
                    }
                }
            }
        }
    }

    PagerAdapter adapter = new PagerAdapter() {
        WorkWithRecordsAndViews modelToView = new WorkWithRecordsAndViews();
        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            ViewGroup v = (ViewGroup) inflater.inflate(paramMV.paramView.layoutTypeId[0], null);
            Record record = listData.get(position);
            modelToView.RecordToView(record, v, navigator, listener, null);
            viewGroup.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int position, Object view) {
            viewGroup.removeView((View) view);
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (navigator != null) {
                int id = v.getId();
                for (ViewHandler vh : navigator.viewHandlers) {
                    if (vh.viewId == id) {
                        switch (vh.type) {
                            case NAME_FRAGMENT:
                                iBase.startScreen(vh.nameFragment, false);
                                break;
                            case PREFERENCE_SET_VALUE:
                                switch (vh.typePref) {
                                    case STRING:
                                        PreferenceTool.setNameString(vh.namePreference, vh.pref_value_string);
                                        break;
                                    case BOOLEAN:
                                        PreferenceTool.setNameBoolean(vh.namePreference, vh.pref_value_boolean);
                                        break;
                                }
                                break;
                            case BACK:
                                iBase.backPressed();
                                break;
                            case PAGER_PLUS:
                                int posit = pager.getCurrentItem() + 1;
                                if (posit < count) {
                                    pager.setCurrentItem(posit);
                                }
                                break;
                        }
                    }
                }
            }
        }
    };
}
