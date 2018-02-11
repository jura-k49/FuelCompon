package net.ukr.jura.fuelcompon.flawsBackEnd;

import android.util.Log;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.Filters;
import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.SimpleRecordToJson;

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
        }

     }

    private void active_tickets(Field response) {
        ListRecords lr = (ListRecords) response.value;
        int payment = 0, pending = 0;
        ListRecords lrN = new ListRecords();
        for (Record rec : lr) {
            String status = rec.getString("status");
            switch (status) {
                case "pending" :
                    pending++;
                    break;
                case "awaits_payment" :
                    payment++;
                    break;
                case "available" :
                    lrN.add(rec);
                    break;
            }
        }
        Record record;
        record = new Record();
        record.add(new Field("type", Field.TYPE_INTEGER, 1));
        record.add(new Field("awaits_payment", Field.TYPE_INTEGER, payment));
        record.add(new Field("pending", Field.TYPE_INTEGER, pending));
        lrN.add(0, record);
        response.value = lrN;
        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
    }

    private void awaits_payment(Field response) {
        ListRecords lr = (ListRecords) response.value;
        int payment = 0, pending = 0;
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
        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
    }

}
