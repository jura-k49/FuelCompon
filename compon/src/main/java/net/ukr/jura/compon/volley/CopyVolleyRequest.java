package net.ukr.jura.compon.volley;

import android.text.Html;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import net.ukr.jura.compon.base.VolleyPresenter;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.JsonSimple;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CopyVolleyRequest<T> extends Request<T> {
    public static final String PROTOCOL_CHARSET = "utf-8";

    public static final String PROTOCOL_CONTENT_TYPE = "application/json";

    public static final int NETWORK_TIMEOUT_LIMIT = 30000;
    public static final int RETRY_COUNT = 2;
    protected JsonSimple json = new JsonSimple();
//    Class<T> clazzprivate final Class<T> clazz;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Object object;
    private VolleyPresenter listener;

    public CopyVolleyRequest(int method, String url, Class<T> clazz, VolleyPresenter listener,
                             Map<String, String> params, Map<String, String> headers, Object object) {
        super(method, url, listener);
        Log.d("ASDFG", "Volley_1Request method=" + method + " url=" + url);
//        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.object = object;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(NETWORK_TIMEOUT_LIMIT,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonSt = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers)).replaceAll("&quot;", "'");
//            if (jsonSt.indexOf("cards") > -1) {
//                jsonSt = "{" +jsonSt.substring(jsonSt.indexOf("]")+2);
//            }
            Log.d("ASDFG", "VOLLEY json=" + jsonSt);
            CookieManager.checkAndSaveSessionCookie(response.headers);
            Field ff = json.jsonToModel(Html.fromHtml(jsonSt).toString());
            return Response.success((T)ff,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
//            Log.d("ASDFG","VOLLEY UnsupportedEncodingException="+e);
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
//        Log.d("ASDFG","VOLLEY deliverResponse");
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
//        Log.d("ASDFG","VOLLEY deliverError error="+error);
        listener.onErrorResponse(error);
    }

    @Override
    protected Map<String, String> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        Log.d("ASDFG", "VOLLEY getParams=" + params);
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
//        else {
//            Log.d("ASDFG", "VOLLEY getHeaders=" + headers);
//        }
        return headers;
    }

//    @Override
//    protected Map<String, String> getPostParams() throws AuthFailureError {
//        return getParams();
//    }

//    @Override
//    protected String getPostParamsEncoding() {
//        return getParamsEncoding();
//    }

    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String body = null;
//        body = gson.toJson(object);
//        Log.d("ASDFG", "VOLLEY getBody=" + new String(body));
        return body.getBytes();
    }

//    public interface VolleyListener<T> {
//        public void onErrorResponse(VolleyError error);
//
//        public void onResponse(T response);
//    }
}
