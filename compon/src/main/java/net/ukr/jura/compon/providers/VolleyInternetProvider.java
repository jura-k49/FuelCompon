package net.ukr.jura.compon.providers;

import android.util.Log;

import com.android.volley.VolleyError;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.base.BaseInternetProvider;
import net.ukr.jura.compon.interfaces_classes.IVolleyListener;
import net.ukr.jura.compon.tools.PreferenceTool;
import net.ukr.jura.compon.volley.VolleyProvider;
import net.ukr.jura.compon.volley.VolleyRequest;

import java.util.HashMap;
import java.util.Map;

public class VolleyInternetProvider extends BaseInternetProvider {
    VolleyRequest request;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        byte [] dataBytes = null;
        if (data != null) {
            dataBytes = data.getBytes();
        }
//        String st = PreferenceTool.getUserKey();
//        if (st.length() > 0) {
//            if (headers == null) {
//                headers = new HashMap<>();
//            }
//            headers.put("X-Auth-Token", "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e");
//
//        }


//        String nameToken = ComponGlob.getInstance().networkParams.nameTokenInHeader;
//        if (nameToken.length() > 0) {
//            if (headers == null) {
//                headers = new HashMap<>();
//            }
//            headers.put("X-Auth-Token", "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e");
//        }
        request = new VolleyRequest(method, url, listenerVolley,
                headers, dataBytes);
        VolleyProvider.getInstance().addToRequestQueue(request);
    }

    @Override
    public void cancel() {
        super.cancel();
        request.cancel();
    }

    IVolleyListener listenerVolley = new IVolleyListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int statusCode = ERRORINMESSAGE;
            String st = error.toString();
            Log.d("QWERT","VolleyInternetProvider error.toString()="+error.toString()+"< status="
                    +error.networkResponse.statusCode
                    +"< mes="+error.getMessage()
//                    +"< DDD="+error.networkResponse.data
            );
            if (st != null) {
                st = st.toUpperCase();
                if (st.contains("TIMEOUT")) {
                    statusCode = TIMEOUT;
                } else {
                    if (st.contains("NOCONNECTIONERROR")) {
                        statusCode = NOCONNECTIONERROR;
                    } else {
                        if (st.contains("SERVERERROR")) {
                            statusCode = SERVERERROR;
                        } else {
                            if (st.contains("AUTHFAILURE")) {
                                statusCode = AUTHFAILURE;
                            }
                        }
                    }
                }
            }
            listener.error(statusCode, error.getMessage());
        }

        @Override
        public void onResponse(String response) {
            listener.response(response);
        }
    };

}
