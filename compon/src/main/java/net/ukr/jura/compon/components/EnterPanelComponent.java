package net.ukr.jura.compon.components;

import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.interfaces_classes.OnChangeStatusListener;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.param.ParamComponent;

public class EnterPanelComponent extends BaseComponent {

    public EnterPanelComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        viewComponent = (View) parentLayout.findViewById(paramMV.paramView.viewId);
        if (viewComponent == null) {
            Log.i("SMPL", "Не найдена панель в " + paramMV.nameParentComponent);
        } else {
            if (paramMV.paramModel != null && paramMV.paramModel.method == paramMV.paramModel.FIELD) {
                workWithRecordsAndViews.RecordToView((Record) paramMV.paramModel.field.value,
                        viewComponent, paramMV.navigator, clickView, paramMV.paramView.visibilityArray);
            } else {
                workWithRecordsAndViews.RecordToView(null, viewComponent, paramMV.navigator,
                        clickView, paramMV.paramView.visibilityArray);
            }
            if (navigator != null) {
                for (ViewHandler vh : navigator.viewHandlers) {
                    if (vh.mustValid != null && vh.changeEnabled) {
                        vh.validArray = new boolean[vh.mustValid.length];
                        for (int i = 0; i < vh.validArray.length; i++) {
                            int mv = vh.mustValid[i];
                            View view = viewComponent.findViewById(mv);
                            if (view != null && view instanceof IComponent) {
                                ((IComponent) view).setOnChangeStatusListener(statusListener);
                                vh.validArray[i] = false;
                            } else {
                                vh.validArray[i] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    OnChangeStatusListener statusListener = new OnChangeStatusListener() {
        @Override
        public void changeStatus(View view, Object status) {
//            iBase.sendActualEvent(paramMV.paramView.viewId, status);
            int stat = (Integer) status;
            if (stat == 2 || stat == 3) {
                int viewId = view.getId();
                for (ViewHandler vh : navigator.viewHandlers) {
                    if (vh.mustValid != null && vh.changeEnabled) {
                        for (int i = 0; i < vh.validArray.length; i++) {
                            int mv = vh.mustValid[i];
                            if (mv == viewId) {
                                vh.validArray[i] = stat == 3;
                                break;
                            }
                        }
                        boolean enabled = true;
                        for (boolean bb : vh.validArray) {
                            if ( ! bb) {
                                enabled = false;
                                break;
                            }
                        }
                        View viewNav = viewComponent.findViewById(vh.viewId);
                        if (viewNav != null) {
                            viewNav.setEnabled(enabled);
                        }
                    }
                }
            }
        }
    };

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        Record rec = (Record)field.value;
        workWithRecordsAndViews.RecordToView(rec, viewComponent, paramMV.navigator,
                clickView, paramMV.paramView.visibilityArray);
    }

}
