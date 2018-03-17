package net.ukr.jura.fuelcompon.network;

import android.os.Handler;
import android.util.Log;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.base.BaseInternetProvider;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.SimpleRecordToJson;
import net.ukr.jura.compon.tools.Constants;
import net.ukr.jura.fuelcompon.network.Api;

import java.util.Map;

public class TestInternetProvider extends BaseInternetProvider{
    private Handler handler;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        handler = new Handler();
        handler.postDelayed(result, 1000);
    }

    Runnable result = new Runnable() {
        @Override
        public void run() {
            String request = url.replace(ComponGlob.getInstance().networkParams.baseUrl, "");
            int i = request.indexOf("?");
            if (i > -1) {
                request = request.substring(0, i);
            }
            listener.response(jsonResult(request));
        }
    };

    private String jsonResult(String request) {
        if (Api.TICKETS_ACTIVE.contains(request)) {
            return setActive();
        } else {
            switch (request) {
                case Api.TICKETS_ACTIVE:
                    return setActive();
                case Api.MARKER_MAP:
                    return setMarker();
                case Api.TUTORIAL:
                    return setTutorial();
            }
        }
        return null;
    }

    private String setTutorial() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field f = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));

        Record record;
        record = new Record();
        record.add(new Field("text", Field.TYPE_STRING, "Талоны дешевле на 12%, чем на АЗС"));
        record.add(new Field("img", Field.TYPE_STRING, "tutorial_1"));
        record.add(new Field("skip", Field.TYPE_STRING, "Пропустить знакомство"));
        record.add(new Field("contin", Field.TYPE_STRING, "Продолжить"));
        record.add(new Field("proceed", Field.TYPE_STRING, ""));
        lr.add(record);

        record = new Record();
        record.add(new Field("text", Field.TYPE_STRING, "Электронные талоны всегда с собой"));
        record.add(new Field("img", Field.TYPE_STRING, "tutorial_2"));
        record.add(new Field("skip", Field.TYPE_STRING, "Пропустить знакомство"));
        record.add(new Field("contin", Field.TYPE_STRING, "Продолжить"));
        record.add(new Field("proceed", Field.TYPE_STRING, ""));
        lr.add(record);

        record = new Record();
        record.add(new Field("text", Field.TYPE_STRING, "Становитесь агентом и зарабатывайте"));
        record.add(new Field("img", Field.TYPE_STRING, "tutorial_3"));
        record.add(new Field("skip", Field.TYPE_STRING, "Пропустить знакомство"));
        record.add(new Field("contin", Field.TYPE_STRING, "Продолжить"));
        record.add(new Field("proceed", Field.TYPE_STRING, ""));
        lr.add(record);

        record = new Record();
        record.add(new Field("text", Field.TYPE_STRING, "Навигатор укажет путь к ближайшей заправке"));
        record.add(new Field("img", Field.TYPE_STRING, "tutorial_4"));
        record.add(new Field("skip", Field.TYPE_STRING, ""));
        record.add(new Field("contin", Field.TYPE_STRING, ""));
        record.add(new Field("proceed", Field.TYPE_STRING, "Приступить"));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
//        Log.d("QWERT","1111 RESULT="+recordToJson.modelToJson(f));
        return recordToJson.modelToJson(f);
    }

    private String setActive() {
//        Log.d("QWERT","setActive setActive setActive");
//        Record rec = new Record();
//        ListRecords lr = new ListRecords();
////        Field f = new Field("", Field.TYPE_LIST, lr);
//        Field f = new Field("", Field.TYPE_RECORD, rec);
//        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
//
//        Record record;
//        record = new Record();
//        record.add(new Field("type", Field.TYPE_INTEGER, 1));
//        record.add(new Field("amount_confirm", Field.TYPE_INTEGER, 5));
//        record.add(new Field("amount_expect", Field.TYPE_INTEGER, 3));
//        lr.add(record);
//
//        record = new Record();
//        record.add(new Field("id", Field.TYPE_INTEGER, 0));
//        record.add(new Field("volume", Field.TYPE_INTEGER, 100));
//        record.add(new Field("fuelingName", Field.TYPE_STRING, "ОККО"));
//        record.add(new Field("network_icon", Field.TYPE_STRING, "azs_active"));
//        record.add(new Field("fuelTypeName", Field.TYPE_STRING, "92"));
//        record.add(new Field("fuel_icon", Field.TYPE_STRING, "a92_evro"));
//        record.add(new Field("due_date", Field.TYPE_STRING, "2019-05-24"));
//        lr.add(record);
//
//        record = new Record();
//        record.add(new Field("id", Field.TYPE_INTEGER, 1));
//        record.add(new Field("volume", Field.TYPE_INTEGER, 200));
//        record.add(new Field("fuelingName", Field.TYPE_STRING, "ОККО"));
//        record.add(new Field("network_icon", Field.TYPE_STRING, "azs_active"));
//        record.add(new Field("fuelTypeName", Field.TYPE_STRING, "95"));
//        record.add(new Field("fuel_icon", Field.TYPE_STRING, "a95_evro"));
//        record.add(new Field("due_date", Field.TYPE_STRING, "2019-01-12"));
//        lr.add(record);
//
//        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
//        Log.d("QWERT","1111 RESULT="+recordToJson.modelToJson(f));
//        return recordToJson.modelToJson(f);
        return null;
    }

    private String setMarker() {
        int i = url.indexOf("?");
        String par = "";
        if (i > -1) {
            par = url.substring(i + 1);
        }
        String[] parA = par.split("&");
        String[] latLon = parA[0].split("=");
        double lat = Double.valueOf(latLon[1]);
        latLon = parA[1].split("=");
        double lon = Double.valueOf(latLon[1]);
        Record rec = new Record();
        ListRecords lr = new ListRecords();
//        Field f = new Field("", Field.TYPE_LIST, lr);
        Field f = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));

        Record record;
        record = new Record();
        record.add(new Field(Constants.MARKER_LAT, Field.TYPE_DOUBLE, lat + 0.001));
        record.add(new Field(Constants.MARKER_LON, Field.TYPE_DOUBLE, lon + 0.001));
        record.add(new Field(Constants.MARKER_NAME_NUMBER, Field.TYPE_INTEGER, 1));
        lr.add(record);

        record = new Record();
        record.add(new Field(Constants.MARKER_LAT, Field.TYPE_DOUBLE, lat - 0.001));
        record.add(new Field(Constants.MARKER_LON, Field.TYPE_DOUBLE, lon - 0.001));
        record.add(new Field(Constants.MARKER_NAME_NUMBER, Field.TYPE_INTEGER, 0));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
//        Log.d("QWERT","RESULT="+recordToJson.modelToJson(f));
        return recordToJson.modelToJson(f);
    }

}
