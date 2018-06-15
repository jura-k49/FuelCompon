package net.ukr.jura.fuelcompon.flawsBackEnd;

import android.util.Log;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.Filters;
import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListFields;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.SimpleRecordToJson;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;

public class FuelMoreWork extends MoreWork{
    @Override
    public void beforeProcessingResponse(Field response, BaseComponent baseComponent) {
        String name = baseComponent.paramMV.nameParentComponent;
        switch (name) {
            case "active_tickets" :
                active_tickets(response);
                break;
            case "awaits_payment" :
                awaits_payment(response);
                break;
            case "choice_fuel" :
                choice_fuel(response);
                break;
            case "tickets_buy" :
                tickets_buy(response);
                break;
            case "map" :
                map(response);
                break;
        }

     }

    private void  map(Field response) {
        ListRecords lr = (ListRecords) response.value;
        Record record = new Record();
        record.add(new Field("type", Field.TYPE_INTEGER, 1));
        record.add(new Field("name", Field.TYPE_STRING, "Все партнеры"));
        lr.add(0, record);
        response.value = lr;
    }

     private void tickets_buy(Field response) {
         ListRecords lrN = new ListRecords();
         Record recN;
         Record rec = (Record) response.value;
         ListFields lf = (ListFields) rec.getValue("fixed_ticket_volumes");
         for (Field ff : lf) {
             recN = new Record();
             recN.add(new Field("nominal", Field.TYPE_LONG, ff.value));
             recN.add(new Field("count", Field.TYPE_LONG, new Long(0)));
             lrN.add(recN);
         }
         response.value = lrN;
     }

    private void  choice_fuel(Field response) {
//        ListRecords lr = (ListRecords) response.value;
        ListRecords lrN = new ListRecords();
        Record recN;
//        for (Record rec : lr) {
        Record rec = (Record) response.value;
        recN = new Record();
        recN.add(new Field("type", Field.TYPE_INTEGER, 1));
        recN.add(new Field("name", Field.TYPE_STRING, rec.getString("name")));
        lrN.add(recN);
        long idL = (Long)rec.getValue("id");
        Integer id = (int)idL;

        recN.add(new Field("idNetwork", Field.TYPE_LONG, idL));
        ListRecords listR = (ListRecords) rec.getValue("fuels");
        if (listR != null) {
            for (Record recFuel : listR) {
                recN = new Record();
                recN.add(new Field("type", Field.TYPE_INTEGER, 0));
                recN.add(new Field("idNetwork", Field.TYPE_INTEGER, id));
                recN.add(new Field("price", Field.TYPE_DOUBLE, recFuel.getDouble("price")));
                recN.add(new Field("fuel_icon", Field.TYPE_STRING, recFuel.getString("icon")));
                lrN.add(recN);
            }
        }
//        }
        response.value = lrN;
    }

    private void active_tickets(Field response) {
        ListRecords lr = (ListRecords) response.value;
        int payment = 0, pending = 0;
        ListRecords lrN = new ListRecords();
        for (Record rec : lr) {
            Double price_azs = rec.getDouble("price_at_network");
            Double price = rec.getDouble("price");
            Long count = (Long) rec.getValue("volume");
            if (price != null && price_azs != null && count != null) {
                Double cost = price * count;
                rec.add(new Field("cost", Field.TYPE_DOUBLE, cost));
                Double cost_azs = price_azs * count;
                rec.add(new Field("cost_azs", Field.TYPE_DOUBLE, cost_azs));
                Double econom = cost_azs - cost;
                rec.add(new Field("econom", Field.TYPE_DOUBLE, econom));
            }
            rec.add(new Field("qr_img", Field.TYPE_STRING,
                    "http://stage.toplivo.branderstudio.com:8086/media/cache/icon_thumbnail/public/img/uploads/fuel_fuel/5a6856ca15381241163731.png"));
            rec.add(new Field("qr_num", Field.TYPE_STRING,"1234 5678 9021 2344"));
            String status = rec.getString("status");
            switch (status) {
                case "pending" :
                    pending++;
                    break;
                case "awaits_payment" :
                    payment++;
                    break;
//                case "available" :
//                    lrN.add(rec);
//                    break;
            }
            lrN.add(rec);
        }
        Record record;
        if (lrN.size() == 0) {
            record = new Record();
            record.add(new Field("type", Field.TYPE_INTEGER, 2));
            lrN.add(record);
        }
        record = new Record();
        record.add(new Field("type", Field.TYPE_INTEGER, 1));
        record.add(new Field("awaits_payment", Field.TYPE_INTEGER, payment));
        record.add(new Field("pending", Field.TYPE_INTEGER, pending));
        lrN.add(0, record);
        response.value = lrN;
//        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
    }

    private void awaits_payment(Field response) {
        ListRecords lr = (ListRecords) response.value;
//        int payment = 0, pending = 0;
        ListRecords lrN = new ListRecords();
        for (Record rec : lr) {
            String status = rec.getString("status");
            switch (status) {
                 case "awaits_payment" :
                     lrN.add(rec);
                     float am = rec.getFloat("price");
                     Long volume = (Long) rec.getValue("volume");
                     Field f = new Field("amount", Field.TYPE_FLOAT, am * volume);
                     rec.add(f);
                    break;
            }
        }
        Collections.sort(lrN, new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                String st1 = r1.getString("fuel_icon");
                String st2 = r2.getString("fuel_icon");
                return st1.compareTo(st2);
            }
        });

        ListRecords lrResult = new ListRecords();
        if (lrN.size() > 0) {
            Record rec1 = lrN.get(0);
            String icon = rec1.getString("fuel_icon");
            int countTic = 0;
            Long countFuel = 0L;
            Float sum = 0f;
            for (Record rec : lrN) {
                String st = rec.getString("fuel_icon");
                if (icon.equals(st)) {
                    countTic++;
                    countFuel += (Long) rec.getValue("volume");
                    sum += rec.getFloat("amount");
                } else {
                    Field f = rec1.getField("amount");
                    f.value = sum;
                    f = rec1.getField("volume");
                    f.value = countFuel;
                    Field f_count = new Field("count", Field.TYPE_INTEGER, countTic);
                    rec1.add(f_count);
                    lrResult.add(rec1);
                    rec1 = rec;
                    icon = rec1.getString("fuel_icon");
                    countTic = 1;
                    countFuel = (Long) rec.getValue("volume");
                    sum = rec.getFloat("amount");
                }
            }
            Field f = rec1.getField("amount");
            f.value = sum;
            f = rec1.getField("volume");
            f.value = countFuel;
            Field f_count = new Field("count", Field.TYPE_INTEGER, countTic);
            rec1.add(f_count);
            lrResult.add(rec1);
            response.value = lrResult;
        } else {
            response.value = lrN;
        }
//        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
    }

}
