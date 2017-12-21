package net.ukr.jura.fuelcompon.network;

import net.ukr.jura.compon.network.NetworkParams;
import net.ukr.jura.fuelcompon.dialogs.BaseDialog;
import net.ukr.jura.fuelcompon.dialogs.ProgressDialog;

public class MyNetworkParams extends NetworkParams {
    @Override
    public void setParams() {
        baseUrl = "https://aurafit.com.ua";
        classProgress = ProgressDialog.class;
        classDialog = BaseDialog.class;
    }
}
