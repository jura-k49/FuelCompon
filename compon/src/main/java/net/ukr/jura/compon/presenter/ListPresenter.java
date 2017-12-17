package net.ukr.jura.compon.presenter;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;

public class ListPresenter {

    private ListRecords listData;
    private int selectOld;
    private BaseComponent baseComponent;
    public enum Command {SELECT};
    private String nameSelectField;

    public ListPresenter(BaseComponent baseComponent) {
        this.baseComponent = baseComponent;
        nameSelectField = "select";
    }

    public void setNameSelectField(String name) {
        nameSelectField = name;
    }

    public void changeData(ListRecords listData, int selectStart) {
        this.listData = listData;
        this.selectOld = selectStart;
        if (selectStart > -1) {
            Record record = listData.get(selectStart);
            Field ff = record.getField(nameSelectField);
            ff.value = new Integer(2);
            baseComponent.changeDataPosition(selectStart, true);
        }
    }

    public void ranCommand(Command com, int position, Field f) {
        switch (com) {
            case SELECT:
                Record record = listData.get(position);
                Field ff = record.getField(nameSelectField);
                if ((Integer) ff.value != 0) {
                    if (selectOld > -1) {
                        record = listData.get(selectOld);
                        ff = record.getField(nameSelectField);
                        ff.value = new Integer(1);
                        baseComponent.changeDataPosition(selectOld, false);
                    }
                    selectOld = position;
                    record = listData.get(selectOld);
                    ff = record.getField(nameSelectField);
                    ff.value = new Integer(2);
                    baseComponent.changeDataPosition(selectOld, true);
                }
                break;
        }
    }

}
