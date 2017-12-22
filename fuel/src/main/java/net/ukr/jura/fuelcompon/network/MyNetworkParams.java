package net.ukr.jura.fuelcompon.network;

import net.ukr.jura.compon.network.NetworkParams;
import net.ukr.jura.fuelcompon.dialogs.ErrorDialog;
import net.ukr.jura.fuelcompon.dialogs.ProgressDialog;

public class MyNetworkParams extends NetworkParams {
    @Override
    public void setParams() {
        baseUrl = "https://aurafit.com.ua";
        classProgress = ProgressDialog.class;
        classErrorDialog = ErrorDialog.class;
    }
}
