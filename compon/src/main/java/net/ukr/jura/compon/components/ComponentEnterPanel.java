package net.ukr.jura.compon.components;

import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;

public class ComponentEnterPanel extends BaseComponent {

//    private WorkWithRecordsAndViews workWithRecordsAndViews = new WorkWithRecordsAndViews();
//    private View view;

    public ComponentEnterPanel(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        viewComponent = (View) parentLayout.findViewById(paramMV.paramView.viewId);
        if (viewComponent == null) {
            Log.i("SMPL", "Не найдена панель в " + paramMV.nameParentComponent);
        } else {
            if (paramMV.paramModel != null && paramMV.paramModel.method == paramMV.paramModel.FIELD) {
                workWithRecordsAndViews.RecordToView((Record) paramMV.paramModel.field.value,
                        viewComponent, paramMV.navigator, clickView);
            } else {
                workWithRecordsAndViews.RecordToView(null, viewComponent, paramMV.navigator, clickView);
            }
        }
    }

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        Record rec = (Record)field.value;
        workWithRecordsAndViews.RecordToView(rec, viewComponent, paramMV.navigator, clickView);
    }

//    private View.OnClickListener click = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int vId = v.getId();
//            List<ViewHandler> viewHandlers = paramMV.navigator.viewHandlers;
//            for (ViewHandler vh : viewHandlers) {
//                if (vId == vh.viewId) {
//                    switch (vh.type) {
//                        case SEND_CHANGE_BACK :
//                            Record param = workWithRecordsAndViews.ViewToRecord(viewComponent, vh.paramModel.param);
//                            new VolleyPresenter<String>(iBase, vh.paramModel, setRecord(param), vl);
//                            break;
//                    }
//                }
//            }
//        }
//    };

//    public Record setRecord(Record paramRecord) {
//        Record rec = new Record();
//        for (Field f : paramRecord) {
//            if (f.value == null) {
//                String st = SimpleApp.getInstance().getParamValue(f.name);
//                if (st.length() > 0) {
//                    rec.add(new Field(f.name, Field.TYPE_STRING, st));
//                }
//            } else {
//                rec.add(new Field(f.name, Field.TYPE_STRING, f.value));
//            }
//        }
//        return rec;
//    }
//
//    CopyVolleyRequest.VolleyListener vl = new CopyVolleyRequest.VolleyListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//        }
//        @Override
//        public void onResponse(Object response) {
//            Field f = (Field) response;
//            if (paramMV.paramModel.nameTakeField == null) {
//                paramMV.paramModel.field.value = f.value;
//            } else {
//                if (f.type == Field.TYPE_CLASS) {
//                    paramMV.paramModel.field.setValue(
//                            ((Record) f.value).getField(paramMV.paramModel.nameTakeField).value,
//                            paramMV.paramView.viewId, iBase);
//                } else {
//                    paramMV.paramModel.field.setValue(f.value, paramMV.paramView.viewId, iBase);
//                }
//            }
//            baseActivity.backPressed();
//        }
//    };
}
