package net.ukr.jura.compon.models;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;

import java.util.ArrayList;
import java.util.List;

public class ParamModel <T> {
    public int method;
    public String url;
    public String param;
    public long duration;
    public static String PARENT_MODEL = "PARENT_MODEL";
    public static int GET = 0;
    public static int POST = 1;
    public static final int PARENT = 100;
    public static final int FIELD = 101;
    public static int defaultMethod = GET;
    public String nameField, nameFieldTo;
    public String nameTakeField;
    public List<Record> addRecordBegining;
    public Field field;
    public Class<T>  internetProvider;
//    public int progressId;

    public static void setDefaultMethod(int method) {
        defaultMethod = method;
    }

    public ParamModel() {
        this(PARENT, PARENT_MODEL, "", -1);
    }
    public ParamModel(String url) {
        this(url, "", -1);
    }
    public ParamModel(int method, String urlOrNameParent) {
        this(method, urlOrNameParent, "", -1);
    }
    public ParamModel(Field field) {
        this(FIELD, "", "", -1);
        this.field = field;
    }
    public ParamModel(String url, String param) {
        this(url, param, -1);
    }
    public ParamModel(int method, String urlOrNameParent, String paramOrField) {
        this(method, urlOrNameParent, paramOrField, -1);
    }

    public ParamModel(String url, String param, long duration) {
        this(defaultMethod, url, param, duration);
    }

    public ParamModel(int method, String url, String param, long duration) {
        this.method = method;
        if (method == PARENT) {
                if ((url == null || url.length() == 0)) {
                    this.url = PARENT_MODEL;
                }
        } else {
            if (url.startsWith("http")) {
                this.url = url;
            } else {
                this.url = ComponGlob.getInstance().networkParams.baseUrl + url;
            }
//            if (method == POST) {
//                this.url = url;
//            } else {
//                if (url.startsWith("http")) {
//                    this.url = url;
//                } else {
//                    this.url = ComponGlob.getInstance().networkParams.baseUrl + url;
//                }
//            }
        }
        this.param = param;
        this.duration = duration;
        nameField = null;
        nameTakeField = null;
        internetProvider = null;
    }

    public ParamModel internetProvider(Class<T> internetProvider) {
        this.internetProvider = internetProvider;
        return this;
    }

    public ParamModel changeNameField(String nameField, String nameFieldTo) {
        this.nameField = nameField;
        this.nameFieldTo = nameFieldTo;
        return this;
    }

    public ParamModel addToBeginning(Record record) {
        if (addRecordBegining == null) {
            addRecordBegining = new ArrayList<>();
        }
        addRecordBegining.add(record);
        return this;
    }

    public ParamModel addToBeginning(String name, int value) {
        Record record = new Record();
        Field field = new Field(name, Field.TYPE_INTEGER, value);
        record.add(field);
        return addToBeginning(record);
    }

    public ParamModel takeField(String name) {
        nameTakeField = name;
        return this;
    }
}
