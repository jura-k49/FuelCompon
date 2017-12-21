package net.ukr.jura.fuelcompon;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import net.ukr.jura.compon.base.SetSettings;
import net.ukr.jura.fuelcompon.network.MyNetworkParams;
import net.ukr.jura.fuelcompon.params.MyListScreens;

public class FuelApp extends MultiDexApplication {
    private static FuelApp instance;
    private Context context;

    public static FuelApp getInstance() {
        if (instance == null) {
            instance = new FuelApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        SetSettings.setNetworkParams(new MyNetworkParams());
        SetSettings.setListScreens(new MyListScreens(context));
    }
}
