package net.ukr.jura.compon.interfaces_classes;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import net.ukr.jura.compon.base.BaseActivity;
import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.base.BaseFragment;
import net.ukr.jura.compon.base.BaseInternetProvider;
import net.ukr.jura.compon.components.ComponentMap;
import net.ukr.jura.compon.json_simple.Field;

public interface IBase {
    public BaseActivity getBaseActivity();
    public BaseFragment getBaseFragment();
    public View getParentLayout();
    public void addEvent(int sender, BaseComponent receiver);
    public void sendEvent(int sender);
    public ParentModel getParentModel(String name);
    public Field getProfile();
//    public void startActivitySimple(String nameMVP);
//    public void startFragment(String nameMVP, boolean startFlag);
    public void startScreen(String nameMVP, boolean startFlag);
    public void backPressed();
    public void progressStart();
    public void progressStop();
    public void showDialog(String title, String message, View.OnClickListener click);
    public void showDialog(int statusCode, String message, View.OnClickListener click);
    public boolean isViewActive();
    public void setFragmentsContainerId(int id);
    public Bundle getSavedInstanceState();
//    public void addRequest(Request request);
    public void addInternetProvider(BaseInternetProvider internetProvider);
    public void setGoogleApiClient(GoogleApiClient googleApiClient);
    public void setComponentMap(ComponentMap componentMap);
}
