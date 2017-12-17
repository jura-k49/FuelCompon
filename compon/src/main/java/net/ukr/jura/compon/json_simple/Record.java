package net.ukr.jura.compon.json_simple;
import java.util.ArrayList;

public class Record extends ArrayList<Field>{
    public Object getValue(String name) {
        Field f = getField(name);
        if (f == null) {
            return null;
        } else {
            return f.value;
        }
    }

    public Field getField(String name) {
        if (name.indexOf(".") < 0) {
            for (Field f : this) {
                if (f.name.equals(name)) {
                    return f;
                }
            }
        } else {
            String st;
//            String st = name.replaceAll("\\.", "-");
//            String[] nameList = st.split("-");
            String[] nameList = name.split("\\.");
            Record record = this;
            int ik = nameList.length - 1;
            boolean yes;
            for (int i = 0; i < ik; i++) {
                st = nameList[i];
                yes = true;
                for (Field f : record ) {
                    if (f.name.equals(st)) {
                        if (f.type == Field.TYPE_CLASS){
                            record = (Record) f.value;
                            yes = false;
                            break;
                        } else {
                            return null;
                        }
                    }
                }
                if (yes) {
                    return null;
                }
            }
            st = nameList[ik];
            for (Field f : record ) {
                if (f.name.equals(st)) {
                    return f;
                }
            }
        }
        return null;
    }

    public Double getDouble(String name) {
        Field f = getField(name);
        if (f != null) {
            switch (f.type) {
                case Field.TYPE_FLOAT:
                case Field.TYPE_DOUBLE:
                    return (double) f.value;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public Float getFloat(String name) {
        Field f = getField(name);
        if (f != null) {
            switch (f.type) {
                case Field.TYPE_DOUBLE:
                    double d = (double) f.value;
                    return (float) d;
                case Field.TYPE_FLOAT:
                case Field.TYPE_INTEGER:
                    return (float) f.value;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public String getString(String name) {
        Field f = getField(name);
        if (f != null) {
            return String.valueOf(f.value);
        } else {
            return null;
        }
    }
}
