package net.ukr.jura.fuelcompon.network;

import net.ukr.jura.compon.network.NetworkParams;
import net.ukr.jura.fuelcompon.R;
import net.ukr.jura.fuelcompon.dialogs.ErrorDialog;
import net.ukr.jura.fuelcompon.dialogs.ProgressDialog;

public class MyNetworkParams extends NetworkParams {
    @Override
    public void setParams() {
        baseUrl =  "http://stage.toplivo.branderstudio.com:8086/api/v1/";
        nameTokenInHeader = "X-Auth-Token";
        nameLanguageInHeader = "Accept-Language";
        paginationPerPage = 30;
        paginationNameParamPerPage = "itemsPerPage";
        paginationNameParamNumberPage = "page";
        classProgress = ProgressDialog.class;
        classErrorDialog = ErrorDialog.class;
        errorDialogViewId = R.id.error_dialog;
    }
}
