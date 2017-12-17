package net.ukr.jura.compon.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import net.ukr.jura.compon.ComponGlob;

public class VolleyProvider {
    private static VolleyProvider instance;
    private static Context context;
    private RequestQueue requestQueue;

    private VolleyProvider() {
        context = ComponGlob.getInstance().context;
    }

    public static synchronized VolleyProvider getInstance() {
        if(instance == null) {
            instance = new VolleyProvider();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void clearInstance() {
        requestQueue = null;
    }
}
