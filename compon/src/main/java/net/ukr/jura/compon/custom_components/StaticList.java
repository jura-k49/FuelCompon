package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.interfaces_classes.OnChangeStatusListener;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.json_simple.WorkWithRecordsAndViews;

public class StaticList extends BaseStaticList implements IComponent {
    protected ListRecords items;
    public StaticList(Context context) {
        super(context);
    }

    public StaticList(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    public void setData(Object data) {
        items = (ListRecords) data;
        setAdapter(adapter, false);
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {

    }

    @Override
    public String getString() {
        return null;
    }

    BaseStaticListAdapter adapter = new BaseStaticListAdapter() {
        WorkWithRecordsAndViews modelToView = new WorkWithRecordsAndViews();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position) {
            View v;
            Record record = items.get(position);
            v = ((LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(ITEM_LAYOUT_ID, null);
            modelToView.RecordToView(record, v);
            return v;
        }

        @Override
        public void onClickView(View view, View viewElrment, int position) {

        }
    };
}
