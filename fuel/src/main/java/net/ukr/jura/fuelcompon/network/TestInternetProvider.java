package net.ukr.jura.fuelcompon.network;

import android.os.Handler;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.base.BaseInternetProvider;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.SimpleRecordToJson;
import net.ukr.jura.compon.tools.Constants;

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
        switch (request) {
            case Api.TICKETS_ACTIVE : return setActive();
            case Api.MARKER_MAP : return setMarker();
        }
        return null;
    }

    private String setActive() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field f = new Field("", Field.TYPE_LIST, lr);
//        Field f = new Field("", Field.TYPE_RECORD, rec);
//        rec.add(new Field("data", Field.TYPE_LIST, lr));

        Record record;
        record = new Record();
        record.add(new Field("ticketId", Field.TYPE_INTEGER, 0));
        record.add(new Field("amount", Field.TYPE_INTEGER, 100));
        record.add(new Field("fuelingName", Field.TYPE_STRING, "ОККО"));
        record.add(new Field("fuelingImg", Field.TYPE_STRING, "test_okko"));
        record.add(new Field("fuelTypeName", Field.TYPE_STRING, "92"));
        record.add(new Field("fuelTypeImg", Field.TYPE_STRING, "test_a92_r"));
        record.add(new Field("shelfLife", Field.TYPE_STRING, "12 февраля"));
        lr.add(record);

        record = new Record();
        record.add(new Field("ticketId", Field.TYPE_INTEGER, 1));
        record.add(new Field("amount", Field.TYPE_INTEGER, 200));
        record.add(new Field("fuelingName", Field.TYPE_STRING, "ОККО"));
        record.add(new Field("fuelingImg", Field.TYPE_STRING, "test_okko"));
        record.add(new Field("fuelTypeName", Field.TYPE_STRING, "95"));
        record.add(new Field("fuelTypeImg", Field.TYPE_STRING, "test_a95"));
        record.add(new Field("shelfLife", Field.TYPE_STRING, "20 марта"));
        lr.add(record);



        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
//        Log.d("QWERT","1111 RESULT="+recordToJson.modelToJson(f));
        return recordToJson.modelToJson(f);
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
        Field f = new Field("", Field.TYPE_LIST, lr);
//        Field f = new Field("", Field.TYPE_RECORD, rec);
//        rec.add(new Field("data", Field.TYPE_LIST, lr));

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
