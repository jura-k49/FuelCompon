package net.ukr.jura.compon.interfaces_classes;

import android.util.Log;

import net.ukr.jura.compon.json_simple.Record;

public class Filters {
    public FilterParam[] filterParams;

    public Filters(FilterParam... args) {
        filterParams = args;
    }

    public boolean isConditions(Record record) {
        for (FilterParam fp: filterParams) {
            Object value = record.getValue(fp.nameField);
            if (value != null) {
                if (fp.value instanceof String) {
                    if (! (value instanceof String && ((String) fp.value).equals((String) value))) return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
