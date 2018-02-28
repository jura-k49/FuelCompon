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

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.interfaces_classes.IVolleyListener;
import net.ukr.jura.compon.network.NetworkParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class VolleyRequest <T> extends Request<T> {

    public static final String PROTOCOL_CHARSET = "utf-8";
    public static final String PROTOCOL_CONTENT_TYPE = "application/json";
    private IVolleyListener listener;
    private Map<String, String> headers;
    private byte[] data;
    private NetworkParams networkParams;

    public VolleyRequest(int method, String url, IVolleyListener listener,
                         Map<String, String> headers, byte[] data) {
        super(method, url, listener);
        networkParams = ComponGlob.getInstance().networkParams;
        if (networkParams.LOG_LEVEL > 1) Log.d(networkParams.NAME_LOG, "method=" + method + " url=" + url);
        this.headers = headers;
        this.listener = listener;
        this.data = data;
        setRetryPolicy(new DefaultRetryPolicy(networkParams.NETWORK_TIMEOUT_LIMIT,
                networkParams.RETRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // Convert from Unicode to UTF-8
//            String string = "\u003c\u0422\u0435\u0441\u0442\u043e\u0432\u0430\u044f";
            String string = "WOG \u0442\u0435\u0441\u0442\u043e\u0432\u044b\u0439";
//            byte[] utf8 = string.getBytes("UTF-8");

            // Convert from UTF-8 to Unicode
            string = new String(string.getBytes("UTF-8"), "UTF-8");
            Log.d("QWERT","ZZZZZZ="+string);
        } catch (UnsupportedEncodingException e) {
            Log.d("QWERT","EEEEEEEEEEE");
        }
        try {
//            String jsonSt = new String(response.data,
//                    HttpHeaderParser.parseCharset(response.headers));
            String jsonSt = new String(response.data);
            jsonSt = new String(jsonSt.getBytes("UTF-8"), "UTF-8");
            if (networkParams.LOG_LEVEL > 2) Log.d(networkParams.NAME_LOG, "Respons json=" + jsonSt);
            CookieManager.checkAndSaveSessionCookie(response.headers);
            Log.d(networkParams.NAME_LOG, "Respons "+HttpHeaderParser.parseCharset(response.headers)+"<< YYYYY json=" + Html.fromHtml(jsonSt).toString());


//            return Response.success( (T) jsonSt,
//                    HttpHeaderParser.parseCacheHeaders(response));
            return Response.success( (T) Html.fromHtml(jsonSt).toString(),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            if (networkParams.LOG_LEVEL > 0) Log.d(networkParams.NAME_LOG, "UnsupportedEncodingException="+e);
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse((String) response);
    }

    @Override
    public void deliverError(VolleyError error) {
        listener.onErrorResponse(error);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Log.d(networkParams.NAME_LOG,"VolleyRequest headers="+headers);
        return headers;
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
    protected Map<String, String> getParams() {
//        Log.d("SMPL", "VOLLEY Params=");
        return null;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Log.d(networkParams.NAME_LOG,"VolleyRequest getBody data="+new String(data));
        return data;
    }
}
