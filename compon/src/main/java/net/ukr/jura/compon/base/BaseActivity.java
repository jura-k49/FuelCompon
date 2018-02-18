package net.ukr.jura.compon.base;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.R;
import net.ukr.jura.compon.components.MapComponent;
import net.ukr.jura.compon.dialogs.DialogTools;
import net.ukr.jura.compon.functions_fragment.ComponentsFragment;
import net.ukr.jura.compon.interfaces_classes.EventComponent;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.ParentModel;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.SimpleRecordToJson;
import net.ukr.jura.compon.tools.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.View.inflate;

public abstract class BaseActivity extends FragmentActivity implements IBase {

    public abstract MultiComponents getScreen();

    public Map<String, MultiComponents> mapFragment;
    private DialogFragment progressDialog;
    private int countProgressStart;
    public List<BaseInternetProvider> listInternetProvider;
    public List<EventComponent> listEvent;
    private View parentLayout;
    public MultiComponents mComponent;
    public int containerFragmentId;
    protected String nameDrawer;
    private boolean isActive;
    public List<ParentModel> parentModelList;
    private Bundle savedInstanceState;
    private GoogleApiClient googleApiClient;
    private MapComponent mapComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        parentModelList = new ArrayList<>();
        mapFragment = ComponGlob.getInstance().MapScreen;
        countProgressStart = 0;
        listInternetProvider = new ArrayList<>();
        listEvent = new ArrayList<>();
//        PreferenceTool.setUserKey("3d496f249f157fdea7681704abf2b4d74b20c619a3e979dc790c43dc27c26aa6");
        mComponent = getScreen();
        if (mComponent == null) {
            Intent intent = getIntent();
            String nameMVP = intent.getStringExtra(Constants.NAME_MVP);
            mComponent = getComponent(nameMVP);
        }
        if (mComponent != null) {
            parentLayout = inflate(this, mComponent.fragmentLayoutId, null);
            setContentView(parentLayout);
            if (mComponent.navigator != null) {
                for (ViewHandler vh : mComponent.navigator.viewHandlers) {
                    View v = findViewById(vh.viewId);
                    if (v != null) {
                        v.setOnClickListener(navigatorClick);
                    }
                }
            }
            mComponent.initComponents(this);
        } else {
            parentLayout = inflate(this, getLayoutId(), null);
        }
        initView();
    }

    @Override
    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public int getLayoutId() {
        return 0;
    }

    public void initView() {

    }

    @Override
    public void setMapComponent(MapComponent mapComponent) {
        this.mapComponent = mapComponent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.MAP_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && mapComponent != null) {
                mapComponent.locationSettings();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.MAP_REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                mapComponent.setLocationServices();
            }
        }
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
                            startScreen(vh.nameFragment, false);
//                            startFragment(vh.nameFragment, false);
                            break;
                        case BACK:
                            onBackPressed();
                            break;
                    }
                    break;
                }
            }
        }
    };

    public MultiComponents getComponent(String name) {
        return ComponGlob.getInstance().MapScreen.get(name);
    }

    @Override
    public void setFragmentsContainerId(int id) {
        containerFragmentId = id;
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
        if (listInternetProvider != null) {
            for (BaseInternetProvider provider : listInternetProvider) {
                provider.cancel();
            }
        }
        isActive = false;
        super.onStop();
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
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
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

//    @Override
    public void startActivitySimple(String nameMVP, Object object) {
        Intent intent = new Intent(this, ComponBaseStartActivity.class);
        intent.putExtra(Constants.NAME_MVP, nameMVP);
        if (object != null) {
            SimpleRecordToJson recordToJson = new SimpleRecordToJson();
            Field f = new Field();
            f.value = object;
            if (object instanceof Record) {
                f.type = Field.TYPE_RECORD;
                intent.putExtra(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            } else if (object instanceof ListRecords) {
                f.type = Field.TYPE_LIST;
                intent.putExtra(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            }
        }
        startActivity(intent);
    }

    public void closeDrawer() {

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

    public void showDialog(String title, String message, View.OnClickListener click) {
        DialogTools.showDialog(this, title, message, click);
    }

    @Override
    public void showDialog(int statusCode, String message, View.OnClickListener click) {
        DialogTools.showDialog(this, statusCode, message, click);
    }

    @Override
    public void progressStart() {
        if (ComponGlob.getInstance().networkParams.classProgress != null) {
            if (progressDialog == null) {
                try {
                    progressDialog = (DialogFragment) ComponGlob.getInstance().networkParams.classProgress.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (countProgressStart == 0) {
                progressDialog.show(getFragmentManager(), "MyProgressDialog");
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

    public void startDrawerFragment(String nameMVP, int containerFragmentId) {
        MultiComponents model = mapFragment.get(nameMVP);
        BaseFragment fragment = new ComponentsFragment();
        fragment.setModel(model);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerFragmentId, fragment, nameMVP);
        transaction.commit();
    }

    @Override
    public void startScreen(String nameMVP, boolean startFlag) {
        startScreen(nameMVP, startFlag, null);
    }

    @Override
    public void startScreen(String nameMVP, boolean startFlag, Object object) {
        MultiComponents mComponent = mapFragment.get(nameMVP);
//        if (mComponent.typeView == MultiComponents.TYPE_VIEW.Activity) {
//            startActivitySimple(nameMVP);
//        } else {
//            startFragment(nameMVP, startFlag);
//        }
        if (mComponent == null) {
            Log.d("SMPL", "Нет Screens с именем " + nameMVP);
        }
        switch (mComponent.typeView) {
            case ACTIVITY:
                startActivitySimple(nameMVP, object);
                break;
            case FRAGMENT:
                startFragment(nameMVP, startFlag, object);
                break;
            case CUSTOM_FRAGMENT:
                startCustomFragment(nameMVP, object);
                break;
        }
    }

    public void startCustomFragment(String nameMVP, Object object) {
        MultiComponents multiComponents = mapFragment.get(nameMVP);
        BaseFragment bf = null;
        try {
            bf = (BaseFragment)multiComponents.customFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (bf != null) {
            bf.setModel(multiComponents);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerFragmentId, bf.getThis(), nameMVP)
//                .addToBackStack(nameMVP)
                    .commit();
        }
    }

    public void startFragment(String nameMVP, boolean startFlag, Object object) {
        BaseFragment fr = (BaseFragment) getSupportFragmentManager().findFragmentByTag(nameMVP);
        int count = (fr == null) ? 0 : 1;
        if (startFlag) {
            clearBackStack(count);
        }
        BaseFragment fragment = (fr != null) ? fr : new ComponentsFragment();
//        for (String key : mapFragment.keySet()) {
//            System.out.println("Key: " + key);
//        }
        Bundle bundle;
        if (object != null) {
            bundle = new Bundle();
            SimpleRecordToJson recordToJson = new SimpleRecordToJson();
            Field f = new Field();
            f.value = object;
            if (object instanceof Record) {
                f.type = Field.TYPE_RECORD;
                bundle.putString(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            } else if (object instanceof ListRecords) {
                f.type = Field.TYPE_LIST;
                bundle.putString(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            }
            fragment.setArguments(bundle);
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

    @Override
    public View getParentLayout() {
        return parentLayout;
    }

    @Override
    public void addEvent(int sender, BaseComponent receiver) {
        listEvent.add(new EventComponent(sender, receiver));
    }

    @Override
    public void addEvent(int[] senderList, BaseComponent receiver) {
        for (int sender : senderList) {
            listEvent.add(new EventComponent(sender, receiver));
        }
    }

    @Override
    public void sendEvent(int sender) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actual();
            }
        }
    }

    @Override
    public void sendActualEvent(int sender, Object paramEvent) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actualEvent(sender, paramEvent);
            }
        }
    }
}
