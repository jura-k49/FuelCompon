package net.ukr.jura.compon.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.interfaces_classes.IErrorDialog;
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
                                   View.OnClickListener clickPositive) {
        if (ComponGlob.getInstance().networkParams.classErrorDialog != null) {
            DialogFragment errorDialog = null;
            try {
                errorDialog = (DialogFragment) ComponGlob.getInstance().networkParams.classErrorDialog.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (errorDialog != null) {
                ((IErrorDialog) errorDialog).setTitle(title);
                ((IErrorDialog) errorDialog).setMessage(msg);
                ((IErrorDialog) errorDialog).setOnClickListener(clickPositive);
                errorDialog.show(activity.getFragmentManager(), "dialog");
            }
        }
    }

    public static void  showDialog(Activity activity, int statusCode, String msg,
                                   View.OnClickListener clickPositive) {
        if (ComponGlob.getInstance().networkParams.classErrorDialog != null) {
            DialogFragment errorDialog = null;
            try {
                errorDialog = (DialogFragment) ComponGlob.getInstance().networkParams.classErrorDialog.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (errorDialog != null) {
                String title = "Ошибка statusCode="+statusCode;
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
                ((IErrorDialog) errorDialog).setTitle(title);
                ((IErrorDialog) errorDialog).setMessage(description);
                ((IErrorDialog) errorDialog).setOnClickListener(clickPositive);
                errorDialog.show(activity.getFragmentManager(), "dialog");
            }
        }
    }
}
