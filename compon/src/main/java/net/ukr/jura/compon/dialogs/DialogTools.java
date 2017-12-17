package net.ukr.jura.compon.dialogs;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.JsonSimple;
import net.ukr.jura.compon.json_simple.Record;

import static net.ukr.jura.compon.base.BaseInternetProvider.AUTHFAILURE;
import static net.ukr.jura.compon.base.BaseInternetProvider.ERRORINMESSAGE;
import static net.ukr.jura.compon.base.BaseInternetProvider.NOCONNECTIONERROR;
import static net.ukr.jura.compon.base.BaseInternetProvider.SERVERERROR;
import static net.ukr.jura.compon.base.BaseInternetProvider.TIMEOUT;

public class DialogTools {
    public static void  showDialog(Activity activity, String title, String msg,
                                   BaseDialog.OnClickListener clickPositive,
                                   BaseDialog.OnClickListener clickNegative,
                                   BaseDialog.OnClickListener clickCancel) {
        BaseDialog baseDialog = new BaseDialog();
        baseDialog.setTitle(title);
        baseDialog.setMessage(msg);
        baseDialog.setOnClickListener(clickPositive, clickNegative, clickCancel);
        baseDialog.show(activity.getFragmentManager(), "dialog");
    }

    public static void  showDialog(Activity activity, String title, String msg,
                                   View.OnClickListener clickPositive) {
        BaseDialog baseDialog = new BaseDialog();
        baseDialog.setTitle(title);
        baseDialog.setMessage(msg);
        baseDialog.setOnClickListener(clickPositive);
        baseDialog.show(activity.getFragmentManager(), "dialog");
    }

    public static void  showDialog(Activity activity, int statusCode, String msg,
                                   View.OnClickListener clickPositive) {
        BaseDialog baseDialog = new BaseDialog();
        String title = "Ошибка";
        String description = null;
        switch (statusCode) {
            case ERRORINMESSAGE :
                Log.d("QWERT","showDialog msg="+msg);
                JsonSimple jsonSimple = new JsonSimple();
                Field f = jsonSimple.jsonToModel(msg);
                Record record = (Record) f.value;
                title = record.getString("title");
                description = record.getString("message");
                break;
            case NOCONNECTIONERROR :
                description = "NOCONNECTIONERROR";
                break;
            case TIMEOUT :
                description = "TIMEOUT";
                break;
            case SERVERERROR :
                description = "SERVERERROR";
                break;
            case AUTHFAILURE :
                description = "AUTHFAILURE";
                break;
        }
        baseDialog.setTitle(title);
        baseDialog.setMessage(description);
        baseDialog.setOnClickListener(clickPositive);
        baseDialog.show(activity.getFragmentManager(), "dialog");
    }
}
