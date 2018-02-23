package net.ukr.jura.compon.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.param.ParamComponent;
import net.ukr.jura.compon.param.ParamModel;
import net.ukr.jura.compon.json_simple.WorkWithRecordsAndViews;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.IPresenterListener;
import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.OnClickItemRecycler;
import net.ukr.jura.compon.interfaces_classes.ParentModel;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.FieldBroadcaster;
import net.ukr.jura.compon.json_simple.JsonSimple;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.presenter.ListPresenter;
import net.ukr.jura.compon.tools.Constants;
import net.ukr.jura.compon.tools.PreferenceTool;

import java.util.List;

public abstract class BaseComponent {
    public abstract void initView();
    public abstract void changeData(Field field);
    public View parentLayout;
    public BaseProvider provider;
    public ListPresenter listPresenter;
    public ParamComponent paramMV;
    public BaseActivity activity;
    public Navigator navigator;
    public MoreWork moreWork;
    public IBase iBase;
    public ViewHandler selectViewHandler;
    public View viewComponent;
    public WorkWithRecordsAndViews workWithRecordsAndViews = new WorkWithRecordsAndViews();

    public BaseComponent(IBase iBase, ParamComponent paramMV){
        this.paramMV = paramMV;
        navigator = paramMV.navigator;
        paramMV.baseComponent = this;
        this.iBase = iBase;
        activity = iBase.getBaseActivity();
        this.parentLayout = iBase.getParentLayout();
        moreWork = null;
        moreWork = paramMV.moreWork;
        if (paramMV.additionalWork != null) {
            try {
                moreWork = (MoreWork) paramMV.additionalWork.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        initView();
        if (paramMV.paramModel != null
                && paramMV.paramModel.method == ParamModel.FIELD) {
            if (paramMV.paramModel.field instanceof FieldBroadcaster) {
                LocalBroadcastManager.getInstance(iBase.getBaseActivity())
                        .registerReceiver(changeFieldValue, new IntentFilter(paramMV.paramModel.field.name));
            }
        }
        if (paramMV.mustValid != null) {
            iBase.addEvent(paramMV.mustValid, this);
        }
        if (paramMV.eventComponent == 0) {
            actual();
        } else {
            iBase.addEvent(paramMV.eventComponent, this);
        }
    }

    public void actualEvent(int sender, Object paramEvent) {
        actual();
    }

    public void actual() {
        if (paramMV.paramModel != null) {
            switch (paramMV.paramModel.method) {
                case ParamModel.PARENT :
                    ParentModel pm = iBase.getParentModel(paramMV.paramModel.url);
                    if (pm.field == null) {
                        for (BaseComponent bc : pm.componentList) {
                            if (bc == this) {
                                return;
                            }
                        }
                        pm.componentList.add(this);
                    } else {
                        setParentData(pm.field);
                    }
                    break;
                case ParamModel.FIELD:
                    changeDataBase(paramMV.paramModel.field);
                    break;
                case ParamModel.ARGUMENTS :
                    Intent intent = activity.getIntent();
                    String st = intent.getStringExtra(Constants.NAME_PARAM_FOR_SCREEN);
                    JsonSimple jsonSimple = new JsonSimple();
                    changeDataBase(jsonSimple.jsonToModel(st));
                    break;
                default:
                    new BasePresenter(iBase, paramMV.paramModel, null, null, listener);
//                    new VolleyPresenter<String>(this, vl);
            }
        } else {
            changeDataBase(null);
        }
    }

    public void setParentData(Field field) {
        if (field != null) {
            if (paramMV.paramModel.param.length() > 0) {
                Field f = ((Record) field.value).getField(paramMV.paramModel.param);
                if (f != null) {
                    changeDataBase(f);
                }
            } else {
                changeDataBase(field);
            }
        }
    }

    public void changeDataPosition(int position, boolean select) {

    }

    private BaseComponent getThis() {
        return this;
    }

    IPresenterListener listener = new IPresenterListener() {
        @Override
        public void onResponse(Field response) {
            if (moreWork != null) {
                moreWork.beforeProcessingResponse(response, getThis());
            }
            String fName = paramMV.paramModel.nameField;
            if (fName != null) {
//                Field field = response;
                String fNameTo = paramMV.paramModel.nameFieldTo;
                if (response.type == Field.TYPE_LIST) {
                    ListRecords listRecords = (ListRecords) response.value;
                    for (Record record : listRecords) {
                        Field f = record.getField(fName);
                        if (f != null) {
                            f.name = fNameTo;
                        }
                    }
                }
            }

            ListRecords lr = null;
            if (paramMV.paramModel.filters != null) {
                lr = new ListRecords();
                ListRecords lrResp = (ListRecords) response.value;
                for (Record rec : lrResp) {
                    if (paramMV.paramModel.filters.isConditions(rec)) {
                        lr.add(rec);
                    }
                }
                response.value = lr;
            }
            if (paramMV.paramModel.nameTakeField == null) {
                changeDataBase((Field) response);
            } else {
                Field f = (Field) response;
                Record r = (Record) f.value;
                changeDataBase(r.getField(paramMV.paramModel.nameTakeField));
            }
        }
    };

    private void changeDataBase(Field field) {
        if (paramMV.paramModel != null && paramMV.paramModel.addRecordBegining != null
                && ((ListRecords) field.value).size() > 0) {
            ((ListRecords) field.value).addAll(0, paramMV.paramModel.addRecordBegining);
        }
        changeData(field);
    }

    private BroadcastReceiver changeFieldValue = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeDataBase(paramMV.paramModel.field);
        }
    };

    public View.OnClickListener clickView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            List<ViewHandler> viewHandlers = paramMV.navigator.viewHandlers;
            for (ViewHandler vh : viewHandlers) {
                if (vId == vh.viewId) {
                    switch (vh.type) {
                        case SEND_CHANGE_BACK :
                            Record param = workWithRecordsAndViews.ViewToRecord(viewComponent, vh.paramModel.param);
                            new BasePresenter(iBase, vh.paramModel, null, setRecord(param), listener_send_change);
                            break;
                        case CLICK_SEND :
                            selectViewHandler = vh;
                            param = workWithRecordsAndViews.ViewToRecord(viewComponent, vh.paramModel.param);
                            Record rec = setRecord(param);
                            ComponGlob.getInstance().setParam(rec);
                            new BasePresenter(iBase, vh.paramModel, null, rec, listener_send_back_screen);
                            break;
                    }
                }
            }
        }
    };

    public void clickAdapter(RecyclerView.ViewHolder holder, View view, int position) {
        Record record = provider.get(position);
        if (navigator != null) {
            int id = view == null ? 0 : view.getId();
            for (ViewHandler vh : navigator.viewHandlers) {
                if (vh.viewId == id) {
                    switch (vh.type) {
                        case FIELD_WITH_NAME_FRAGMENT:
                            if (listPresenter != null) {
                                listPresenter.ranCommand(ListPresenter.Command.SELECT,
                                        position, null);
                            }
                            ComponGlob.getInstance().setParam(record);
                            iBase.startScreen((String) record.getValue(vh.nameFragment), false);
                            break;
                        case NAME_FRAGMENT:
                            ComponGlob.getInstance().setParam(record);
                            if (vh.paramForScreen == ViewHandler.TYPE_PARAM_FOR_SCREEN.RECORD) {
                                iBase.startScreen(vh.nameFragment, false, record);
                            } else {
                                iBase.startScreen(vh.nameFragment, false);
                            }
                            break;
                    }
                    break;
                }
            }
        }
    }

    public OnClickItemRecycler clickItem = new OnClickItemRecycler() {
        @Override
        public void onClick(RecyclerView.ViewHolder holder, View view, int position) {
            clickAdapter(holder, view, position);
        }
    };

    public Record setRecord(Record paramRecord) {
        Record rec = new Record();
        for (Field f : paramRecord) {
            if (f.value == null) {
                String st = ComponGlob.getInstance().getParamValue(f.name);
                if (st.length() > 0) {
                    rec.add(new Field(f.name, Field.TYPE_STRING, st));
                }
            } else {
                rec.add(new Field(f.name, Field.TYPE_STRING, f.value));
            }
        }
        return rec;
    }

    IPresenterListener listener_send_back_screen = new IPresenterListener() {
        @Override
        public void onResponse(Field response) {
            if (selectViewHandler.afterResponse != null) {
                for (ViewHandler vh : selectViewHandler.afterResponse.viewHandlers) {
                    switch (vh.type) {
                        case NAME_FRAGMENT:
                            iBase.startScreen(vh.nameFragment, false);
                            break;
                        case PREFERENCE_SET_TOKEN:
                            Record rec = ((Record) response.value);
//                            String st = rec.getString(vh.nameFieldWithValue);
                            String st = "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e";
                            if (st != null) {
                                PreferenceTool.setSessionToken(st);
                            }
                            break;
                        case BACK:
                            iBase.backPressed();
                            break;
                    }
                }
            }
        }
    };

    IPresenterListener listener_send_change =new IPresenterListener() {
        @Override
        public void onResponse(Field response) {
//            Field f = (Field) response;
            if (paramMV.paramModel.nameTakeField == null) {
                paramMV.paramModel.field.value = response.value;
            } else {
                if (response.type == Field.TYPE_CLASS) {
                    paramMV.paramModel.field.setValue(
                            ((Record) response.value).getField(paramMV.paramModel.nameTakeField).value,
                            paramMV.paramView.viewId, iBase);
                } else {
                    paramMV.paramModel.field.setValue(response.value, paramMV.paramView.viewId, iBase);
                }
            }
            iBase.backPressed();
        }
    };

}
