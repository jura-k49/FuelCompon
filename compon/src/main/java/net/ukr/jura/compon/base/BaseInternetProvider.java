package net.ukr.jura.compon.base;

import java.util.Map;

public class BaseInternetProvider {
    protected int method;
    protected String url;
    protected String data;
    protected InternetProviderListener listener;
    protected boolean isCanceled;
    public static final int ERRORINMESSAGE = 700;
    public static final int NOCONNECTIONERROR = 701;
    public static final int TIMEOUT = 702;
    public static final int SERVERERROR = 703;
    public static final int AUTHFAILURE = 704;

    public BaseInternetProvider(int method, String url, Map<String, String> headers,
                                String data, InternetProviderListener listener) {
        this.method = method;
        this.url = url;
        this.data = data;
        this.listener = listener;
        isCanceled = false;
    }

    public void cancel() {
        isCanceled = true;
    }

    public static BaseInternetProvider newInternetProvider(int method,String url,
                        Map<String, String> headers,
                        String data, InternetProviderListener listener) {
        return new BaseInternetProvider(method, url, headers, data, listener);
    }

    public interface InternetProviderListener {
        void response(String response);
        void error(int statusCode, String message);
    }
}
