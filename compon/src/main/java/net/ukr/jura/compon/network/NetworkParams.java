package net.ukr.jura.compon.network;

public abstract class NetworkParams<T> {
    public String baseUrl;
    public int paginationPerPage = 20;
    public String paginationNameParamPerPage = "";
    public String paginationNameParamNumberPage = "";
    public int NETWORK_TIMEOUT_LIMIT = 20000; // milliseconds
    public int RETRY_COUNT = 1;
    public int LOG_LEVEL = 3;    // 0 - not, 1 - ERROR, 2 - URL, 3 - URL + jsonResponse
    public String NAME_LOG = "SMPL";
    public Class<T>  classProgress;
    public Class<T>  classErrorDialog;
    public NetworkParams() {
        setParams();
    }
    public String nameTokenInHeader = "";
    public abstract void setParams();
}
