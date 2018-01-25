package net.ukr.jura.compon.base;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.components.ComponentMap;
import net.ukr.jura.compon.interfaces_classes.EventComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.ParentModel;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.compon.tools.StaticVM;

import java.util.ArrayList;
import java.util.List;

//import net.ukr.jura.compon.dialogs.ProgressDialog;

public abstract class BaseFragment extends Fragment implements IBase {
    public abstract void initView(Bundle savedInstanceState);
//    public abstract int getLayoutId();
    protected View parentLayout;
    private Object mObject;
    private int countProgressStart;
    private DialogFragment progressDialog;
//    public List<Request> listRequests;
    public List<BaseInternetProvider> listInternetProvider;
    public MultiComponents mComponent;
    public List<EventComponent> listEvent;
    public List<ParentModel> parentModelList;
    private Bundle savedInstanceState;
    private GoogleApiClient googleApiClient;

    public BaseFragment() {
        mObject = null;
//        listRequests = new ArrayList<>();
        listInternetProvider = new ArrayList<>();
        listEvent = new ArrayList<>();
        parentModelList = new ArrayList<>();
    }

    public BaseFragment getThis() {
        return this;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        if (mComponent == null || mComponent.typeView == MultiComponents.TYPE_VIEW.CUSTOM_FRAGMENT) {
            parentLayout = inflater.inflate(getLayoutId(), null, false);
        } else {
            parentLayout = inflater.inflate(mComponent.fragmentLayoutId, null, false);
            TextView title = (TextView) StaticVM.findViewByName(parentLayout, "title");
            if (title != null) {
                if (mComponent.args != null && mComponent.args.length > 0) {
                    title.setText(String.format(mComponent.title, setFormatParam(mComponent.args)));
                } else {
                    if (mComponent.title.length() > 0) {
                        title.setText(mComponent.title);
                    }
                }
            }
            if (mComponent.navigator != null) {
                for (ViewHandler vh : mComponent.navigator.viewHandlers) {
                    View v = parentLayout.findViewById(vh.viewId);
                    if (v != null) {
                        v.setOnClickListener(navigatorClick);
                    }
                }
            }
        }
        initView(savedInstanceState);
        return parentLayout;
    }

    @Override
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public int getLayoutId() {
        return 0;
    }

    @Override
    public void setComponentMap(ComponentMap componentMap) {
        getBaseActivity().setComponentMap(componentMap);
    }

    View.OnClickListener navigatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            for (ViewHandler vh : mComponent.navigator.viewHandlers) {
                if (vh.viewId == id) {
                    switch (vh.type) {
                        case NAME_FRAGMENT:
//                            ComponGlob.getInstance().setParam(record);
                            getBaseActivity().startScreen(vh.nameFragment, false);
//                            startFragment(vh.nameFragment, false);
                            break;
                    }
                    break;
                }
            }
        }
    };

    public void setModel(MultiComponents mComponent) {
        this.mComponent = mComponent;
    }

    public String setFormatParam(String[] args) {
        String st = "";
        BaseActivity ba = (BaseActivity) getActivity();
        List<String> namesParams = ComponGlob.getInstance().namesParams;
        List<String> valuesParams = ComponGlob.getInstance().valuesParams;
        String sep = "";
        int ik = namesParams.size();
        for (String arg : args) {
            String value = "";
            for (int i = 0; i < ik; i++) {
                if (arg.equals(namesParams.get(i))) {
                    st = sep + valuesParams.get(i);
                    sep = ",";
                    break;
                }
            }
        }
        return st;
    }

    @Override
    public void onStop() {
//        for (Request request : listRequests) {
//            request.cancel();
//        }
        if (listInternetProvider != null) {
            for (BaseInternetProvider provider : listInternetProvider) {
                provider.cancel();
            }
        }
        mObject = null;
        super.onStop();
    }

    @Override
    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
////        if (googleApiClient != null) {
////            googleApiClient.connect();
////        }
//    }
//
//    @Override
//    public void onPause() {
////        if (googleApiClient != null) {
////            googleApiClient.disconnect();
////        }
//        super.onPause();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void setFragmentsContainerId(int id) {

    }

    @Override
    public void addEvent(int sender, BaseComponent receiver) {
        listEvent.add(new EventComponent(sender, receiver));
    }

    @Override
    public Field getProfile() {
        return ComponGlob.getInstance().profile;
    }

    @Override
    public void backPressed() {
        ((BaseActivity) getActivity()).onBackPressed();
    }

    @Override
    public BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }

    @Override
    public BaseFragment getBaseFragment() {
        return this;
    }

//    @Override
//    public void addRequest(Request request) {
//        listRequests.add(request);
//    }

    @Override
    public void addInternetProvider(BaseInternetProvider internetProvider) {
        listInternetProvider.add(internetProvider);
    }

    @Override
    public void sendEvent(int sender) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actual();
            }
        }
    }

    public ParentModel getParentModel(String name) {
        if (parentModelList.size() > 0) {
            for (ParentModel pm : parentModelList) {
                if (pm.nameParentModel.equals(name)) {
                    return pm;
                }
            }
        }
        ParentModel pm = new ParentModel(name);
        parentModelList.add(pm);
        return pm;
    }

    public String getName() {
        return "Base";
    }

    public void setObject(Object o) {
        mObject = o;
    }

    public Object getObject() {
        return mObject;
    }

    @Override
    public View getParentLayout() {
        return parentLayout;
    }

//    @Override
    public void startActivitySimple(String nameMVP) {
        getBaseActivity().startActivitySimple(nameMVP);
    }

    @Override
    public void startScreen(String nameMVP, boolean startFlag) {
        getBaseActivity().startScreen(nameMVP, startFlag);
    }
//    @Override
    public void startFragment(String nameMVP, boolean startFlag) {
        getBaseActivity().startFragment(nameMVP, startFlag);
    }

//    @Override
//    public void progressStart(int progressId ) {
////        getBaseActivity().progressStart(progressId);
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog();
//        }
//        if (countProgressStart == 0) {
//            progressDialog.show(getActivity().getFragmentManager(), "MyProgressDialog");
//        }
//        countProgressStart++;
//    }

    @Override
    public void progressStart() {
        if (ComponGlob.getInstance().networkParams.classProgress != null) {
            if (progressDialog == null) {
//            progressDialog = new ProgressDialog();
                try {
                    progressDialog = (DialogFragment) ComponGlob.getInstance().networkParams.classProgress.newInstance();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (countProgressStart == 0) {
                progressDialog.show(getActivity().getFragmentManager(), "MyProgressDialog");
            }
            countProgressStart++;
        }
    }

    @Override
    public void progressStop() {
        countProgressStart--;
        if (countProgressStart == 0 && progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

//    @Override
//    public void progressStop(int progressId) {
////        getBaseActivity().progressStop(progressId);
//        countProgressStart--;
//        if (countProgressStart == 0) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }

    @Override
    public void showDialog(String title, String message, View.OnClickListener click) {
        getBaseActivity().showDialog(title, message, click);
    }

    @Override
    public void showDialog(int statusCode, String message, View.OnClickListener click) {
        getBaseActivity().showDialog(statusCode, message, click);
    }

    @Override
    public boolean isViewActive() {
        return false;
    }
}
