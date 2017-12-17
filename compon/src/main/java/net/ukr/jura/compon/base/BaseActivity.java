package net.ukr.jura.compon.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.R;
import net.ukr.jura.compon.dialogs.DialogTools;
import net.ukr.jura.compon.dialogs.ProgressDialog;
import net.ukr.jura.compon.functions_fragment.ComponentsFragment;
import net.ukr.jura.compon.interfaces_classes.EventComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.models.MultiComponents;
import net.ukr.jura.compon.tools.Constants;
import net.ukr.jura.compon.tools.PreferenceTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.View.inflate;

public abstract class BaseActivity extends FragmentActivity implements IBase {

    public abstract int getProgressLayout();
    public abstract int getDialogLayout();
    public abstract int getLayoutId();
    public abstract String getBaseUrl();
    protected abstract void initFragments();
    public abstract void initView();
//    public abstract void closeDrawer();
//    public Map<String, MultiComponents> mapFragment = new HashMap<String, MultiComponents>();
    public Map<String, MultiComponents> mapFragment;
    private ProgressDialog progressDialog;
    private int countProgressStart;
//    public List<Request> listRequests;
    public List<BaseInternetProvider> listInternetProvider;
    public List<EventComponent> listEvent;
    private View parentLayout;
    public int containerFragmentId;
    protected String nameDrawer;
    private boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponGlob.getInstance().progressId = getProgressLayout();
        ComponGlob.getInstance().dialogId = getDialogLayout();
        mapFragment = ComponGlob.getInstance().MapScreen;
//        ComponGlob.getInstance().baseURL = getBaseUrl();
        countProgressStart = 0;
        listInternetProvider = new ArrayList<>();
//        listRequests = new ArrayList<>();
        PreferenceTool.setUserKey("3d496f249f157fdea7681704abf2b4d74b20c619a3e979dc790c43dc27c26aa6");
        initFragments();
        for (MultiComponents value : mapFragment.values()) {
            String par = value.getParamModel();
            if (par != null && par.length() > 0) {
                String[] param = par.split(Constants.SEPARATOR_LIST);
                int ik = param.length;
                for (int i = 0; i < ik; i++) {
                    ComponGlob.getInstance().addParam(param[i]);
                }
            }
        }
        View v = getContentView(savedInstanceState);
        containerFragmentId = R.id.content_frame;
        setContentView(v);
        parentLayout = findViewById(android.R.id.content).getRootView();
        initView();
        String start = startFragment();
        Log.d("QWERT","BaseActivity onCreate start="+start);
        if (start != null) {
            startFragment(start, false);
        }
    }

    protected View getContentView(Bundle savedInstanceState) {
        View view = inflate(this, R.layout.activity_drawer, null);
        return view;
    }

    protected String startFragment() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
//        if (listRequests != null) {
//            for (Request request : listRequests) {
//                request.cancel();
//            }
//        }
        if (listInternetProvider != null) {
            for (BaseInternetProvider provider : listInternetProvider) {
                provider.cancel();
            }
        }
        isActive = false;
        super.onStop();
    }

    @Override
    public Field getProfile() {
        return ComponGlob.getInstance().profile;
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public BaseFragment getBaseFragment() {
        return null;
    }

    @Override
    public boolean isViewActive() {
        return isActive;
    }

    @Override
    public void startActivitySimple(Object clazz) {

    }

    public void closeDrawer() {

    }

    public void showDialog(String title, String message, View.OnClickListener click) {
        DialogTools.showDialog(this, title, message, click);
    }

    @Override
    public void showDialog(int statusCode, String message, View.OnClickListener click) {
        DialogTools.showDialog(this, statusCode, message, click);
    }

    @Override
    public void progressStart(int progressId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog();
        }
        if (countProgressStart == 0) {
            progressDialog.show(getFragmentManager(), "MyProgressDialog");
        }
        countProgressStart++;
    }

    @Override
    public void progressStop(int progressId) {
        countProgressStart--;
        if (countProgressStart == 0) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

//    public MultiComponents addFragment(String name, int layoutId, String title, String... args) {
//        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
//        mapFragment.put(name, mc);
//        return mc;
//    }
//
//    public MultiComponents addFragment(String name, int layoutId) {
//        MultiComponents mc = new MultiComponents(name, layoutId);
//        mapFragment.put(name, mc);
//        return mc;
//    }

    public void startDrawerFragment(String nameMVP, int containerFragmentId) {
        MultiComponents model = mapFragment.get(nameMVP);
        BaseFragment fragment = new ComponentsFragment();
        fragment.setModel(model);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerFragmentId, fragment, nameMVP);
        transaction.commit();
    }

    public void startFragment(String nameMVP, boolean startFlag) {
        BaseFragment fr = (BaseFragment) getSupportFragmentManager().findFragmentByTag(nameMVP);
        int count = (fr == null) ? 0 : 1;
        if (startFlag) {
            clearBackStack(count);
        }
        BaseFragment fragment = (fr != null) ? fr : new ComponentsFragment();
        Log.d("QWERT","mapFragment size="+mapFragment.size());
        for (String key : mapFragment.keySet()) {
            System.out.println("Key: " + key);
        }

        fragment.setModel(mapFragment.get(nameMVP));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerFragmentId, fragment, nameMVP)
//                .addToBackStack(nameMVP)
                .commit();
    }

    public void clearBackStack(int count) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > count) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(count);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void addParamValue(String name, String value) {
        ComponGlob.getInstance().addParamValue(name, value);
    }


    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void addInternetProvider(BaseInternetProvider internetProvider) {
        listInternetProvider.add(internetProvider);
    }

//    @Override
//    public void addRequest(Request request) {
//        listRequests.add(request);
//    }

    @Override
    public View getParentLayout() {
        return parentLayout;
    }

    @Override
    public void addEvent(int sender, BaseComponent receiver) {
        listEvent.add(new EventComponent(sender, receiver));
    }

    @Override
    public void sendEvent(int sender) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actual();
            }
        }
    }
}
