package net.ukr.jura.compon.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import net.ukr.jura.compon.base.VolleyPresenter;
import net.ukr.jura.compon.json_simple.JsonSimple;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.volley.CookieManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SimpleRequest<T> extends Request<T> {

    public static final String PROTOCOL_CHARSET = "utf-8";
    public static final String PROTOCOL_CONTENT_TYPE = "application/json";
    public static final int NETWORK_TIMEOUT_LIMIT = 5000;
    public static final int RETRY_COUNT = 1;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Object object;
    private VolleyPresenter listener;
    private JsonSimple js = new JsonSimple();

    public SimpleRequest(int method, String url, VolleyPresenter listener,
                         Map<String, String> params,
                         Map<String, String> headers, Object object) {
        super(method, url, listener);
        Log.d("SMPL", "SimpleRequest method=" + method + " url=" + url);
        this.headers = headers;
        this.params = params;
        this.object = object;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(NETWORK_TIMEOUT_LIMIT,
                RETRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Log.d("SMPL","SimpleRequest Response="+response);
        try {
            String jsonSt = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers)).replaceAll("&quot;", "'");
            Log.d("SMPL", "json=" + jsonSt);
            CookieManager.checkAndSaveSessionCookie(response.headers);
            return Response.success( (T) jsonSt,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("SMPL","VOLLEY UnsupportedEncodingException="+e);
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        Log.d("SMPL","SimpleRequest deliverResponse response="+response);
        listener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() {
//        if (params == null) {
//            params = new HashMap<>();
//        }
        Log.d("SMPL", "VOLLEY Params=" + params);
        return params;
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected String getParamsEncoding() {
        return PROTOCOL_CHARSET;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null) {
            headers = new HashMap<>();
        }
        Log.d("SMPL","headers="+headers);
        return headers;
    }

    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String body = null;
//        body = gson.toJson(object);
        body = js.ModelToJson((Record) object);
        Log.d("SMPL", "VOLLEY getBody=" + new String(body));
        return body.getBytes();
    }
}
