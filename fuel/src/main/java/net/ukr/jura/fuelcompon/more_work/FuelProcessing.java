package net.ukr.jura.fuelcompon.more_work;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.fuelcompon.R;

public class FuelProcessing extends MoreWork {

    @Override
    public void clickView(View viewClick, View parentView,
                          BaseComponent baseComponent, Record rec, int position) {
        int id = viewClick.getId();
        Field ff = rec.getField("count");
        Long count = (Long) ff.value;
        if (id == R.id.plus) {
            count++;
        } else {
            if (count > 0) {
                count--;
            }
        }
        ff.value = count;
        TextView tv = (TextView) parentView.findViewById(R.id.count);
        ImageView min = (ImageView) parentView.findViewById(R.id.minus);
        tv.setText(String.valueOf(count));
        if (count == 0) {
            tv.setAlpha(0.35f);
            min.setAlpha(0.35f);
        } else {
            tv.setAlpha(1f);
            min.setAlpha(1f);
        }
        Field fAmount = rec.getField("amount");
        if (fAmount == null) {
            fAmount = new Field("amount", Field.TYPE_LONG, new Long(0));
            rec.add(fAmount);
        }
        Field fCost = rec.getField("cost");
        if (fCost == null) {
            fCost = new Field("cost", Field.TYPE_DOUBLE, new Double(0));
            rec.add(fCost);
        }
        Record arg = (Record) baseComponent.multiComponent.getComponent(R.id.panel).argument.value;
        Double price = arg.getDouble("price");
        Long am = count * (Long)rec.getValue("nominal");
        Double cost = price * am;
        fCost.value = cost;
        fAmount.value = am;
        iBase.sendEvent(baseComponent.paramMV.paramView.viewId);
    }
}
