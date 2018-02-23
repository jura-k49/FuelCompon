package net.ukr.jura.compon;

import android.content.Context;

import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.compon.param.ParamModel;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.FieldBroadcaster;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.network.CacheWork;
import net.ukr.jura.compon.network.NetworkParams;
import net.ukr.jura.compon.tools.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponGlob {
    private static ComponGlob instance;
    public FieldBroadcaster profile;
    public Context context;
    public CacheWork cacheWork;
    public Map<String, MultiComponents> MapScreen;
    public NetworkParams networkParams;
    public List<String> namesParams = new ArrayList<>();
    public List<String> valuesParams = new ArrayList<>();
    public String token;
    public String language;

    public static ComponGlob getInstance() {
        if (instance == null) {
            instance = new ComponGlob();
        }
        return instance;
    }

    public ComponGlob() {
        instance = this;
        token = "";
        language = "ru";
        MapScreen = new HashMap<String, MultiComponents>();
        profile = new FieldBroadcaster("profile", Field.TYPE_RECORD, null);
    }

    public void setContext(Context context) {
        this.context = context;
        cacheWork = new CacheWork(context);
    }

    public void setParam(Record fields) {
        int ik = namesParams.size();
        for (Field f: fields) {
            String name = f.name;
            for (int i = 0; i < ik; i++) {
                String nameParam = namesParams.get(i);
                if (nameParam.equals(name)) {
                    switch (f.type) {
                        case Field.TYPE_STRING :
                            valuesParams.set(i, new String((String) f.value));
                            break;
                        case Field.TYPE_INTEGER :
                            valuesParams.set(i, String.valueOf((Integer) f.value));
                            break;
                        case Field.TYPE_LONG :
                            valuesParams.set(i, String.valueOf((Long) f.value));
                            break;
                        case Field.TYPE_FLOAT :
                            valuesParams.set(i, String.valueOf((Float) f.value));
                            break;
                        case Field.TYPE_DOUBLE :
                            valuesParams.set(i, String.valueOf((Double) f.value));
                            break;
                        case Field.TYPE_BOOLEAN :
                            valuesParams.set(i, String.valueOf((Boolean) f.value));
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void addParam(String paramName) {
        for (String st : namesParams) {
            if (paramName.equals(st)) {
                return;
            }
        }
        namesParams.add(paramName);
        valuesParams.add("");
    }

    public void addParamValue(String paramName, String paramValue) {
        int ik = namesParams.size();
        for (int i = 0; i < ik; i++) {
            if (paramName.equals(namesParams.get(i))) {
                valuesParams.set(i, paramValue);
                return;
            }
        }
        namesParams.add(paramName);
        valuesParams.add(paramValue);
    }

    public String installParam(String param, ParamModel.TypeParam typeParam, String url) {
        switch (typeParam) {
            case NAME: return installParamName(param, url);
            case SLASH: return installParamSlash(param);
            default: return "";
        }
    }

    public String installParamName(String param, String url) {
        String st = "";
        if (param != null && param.length() > 0) {
            if (url.contains("?")) {
                st = "&";
            } else {
                st = "?";
            }
            String[] paramArray = param.split(Constants.SEPARATOR_LIST);
            int ik = namesParams.size();
            String sep = "";
            for (String par : paramArray) {
                for (int i = 0; i < ik; i++) {
                    if (par.equals(namesParams.get(i))) {
                        st = st + sep + par + "=" + valuesParams.get(i);
                        sep = "&";
                        break;
                    }
                }
            }
        }
        return st;
    }

    public String installParamSlash(String param) {
        String st = "";
        if (param != null && param.length() > 0) {
            String[] paramArray = param.split(Constants.SEPARATOR_LIST);
            int ik = namesParams.size();
            for (String par : paramArray) {
                for (int i = 0; i < ik; i++) {
                    if (par.equals(namesParams.get(i))) {
                        st = st + "/" + valuesParams.get(i);
                        break;
                    }
                }
            }
        }
        return st;
    }

    public String getParamValue(String nameParam) {
        int ik = namesParams.size();
        for (int i = 0; i < ik; i++) {
            if (nameParam.equals(namesParams.get(i))) {
                return valuesParams.get(i);
            }
        }
        return "";
    }
}
