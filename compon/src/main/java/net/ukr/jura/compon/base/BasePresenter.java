package net.ukr.jura.compon.base;

import android.text.Html;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.IPresenterListener;
import net.ukr.jura.compon.json_simple.JsonSimple;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.models.ParamModel;
import net.ukr.jura.compon.providers.VolleyInternetProvider;

import java.util.Map;

public class BasePresenter implements BaseInternetProvider.InternetProviderListener {
    private IBase iBase;
    private ParamModel paramModel;
    private Record data;
    Map<String, String> headers;
    private IPresenterListener listener;
    private boolean isCanceled;
    private BaseInternetProvider internetProvider;
    protected JsonSimple jsonSimple = new JsonSimple();
    protected String nameJson, json, url;
    protected int method;

    public BasePresenter(IBase iBase, ParamModel paramModel,
                         Map<String, String> headers, Record data, IPresenterListener listener) {
        this.iBase = iBase;
        this.paramModel = paramModel;
        this.data = data;
        this.listener = listener;
        this.headers = headers;
        this.method = paramModel.method;
        long duration = paramModel.duration;
        if (method == ParamModel.GET) {
            String st = ComponGlob.getInstance().installParam(paramModel.param);
            url = paramModel.url + st;
        } else {
            url = paramModel.url;
        }
        if (duration > 0) {
            nameJson = url;
            json = ComponGlob.getInstance().cacheWork.getJson(nameJson);
            listener.onResponse(jsonSimple.jsonToModel(Html.fromHtml(json).toString()));
            if (json == null) {
                startInternetProvider();
            }
        } else {
            startInternetProvider();
        }

    }

    public void startInternetProvider() {
        isCanceled = false;
        if (paramModel.internetProvider == null) {
            internetProvider = new VolleyInternetProvider(paramModel.method,
                    url, headers, jsonSimple.ModelToJson(data), this);
        } else {
            internetProvider = paramModel.internetProvider.newInternetProvider(paramModel.method,
                    url, headers, jsonSimple.ModelToJson(data), this);
        }
        iBase.addInternetProvider(internetProvider);
        iBase.progressStart(paramModel.progressId);
    }

    public void cancel() {
        isCanceled = true;
        if (internetProvider != null) {
            internetProvider.cancel();
        }
    }

    @Override
    public void response(String response) {
        iBase.progressStop(paramModel.progressId);
        if (response == null) {
            iBase.showDialog("", "no response", null);
        }
        if (paramModel.duration > 0) {
            ComponGlob.getInstance().cacheWork.addCasche(url,
                    paramModel.duration, response);
        }
        if ( ! isCanceled) {
            listener.onResponse(jsonSimple.jsonToModel(response));
        }
    }

    @Override
    public void error(int statusCode, String message) {
        iBase.progressStop(paramModel.progressId);
        iBase.showDialog(statusCode, message, null);
    }

}
