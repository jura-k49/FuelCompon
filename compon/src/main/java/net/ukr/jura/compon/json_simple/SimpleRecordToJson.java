package net.ukr.jura.compon.json_simple;


import android.util.Log;

public class SimpleRecordToJson {
    private String quote = "\"";
    private String quoteColon = "\":";
    private StringBuffer sb;

    public String modelToJson(Field field) {
        sb = new StringBuffer( 1000);
        if (field.type == Field.TYPE_RECORD) {
            recordJson((Record) field.value);
        } else {
            listJson((ListRecords) field.value);
        }
        return sb.toString();
    }

    public String recordToJson(Record rec) {
        sb = new StringBuffer( 1000);
        recordJson(rec);
        return sb.toString();
    }

    private void recordJson(Record rec) {
        sb.append("{");
        String separator = "";
        for (Field f : rec) {
            sb.append(separator);
            separator = ",";
            switch (f.type) {
                case Field.TYPE_STRING :
                    sb.append(quote + f.name + quoteColon + quote + (String) f.value + quote);
                    break;
                case Field.TYPE_INTEGER :
                    sb.append(quote + f.name + quoteColon + (Integer) f.value);
                    break;
                case Field.TYPE_LONG :
                    sb.append(quote + f.name + quoteColon + (Long) f.value);
                    break;
                case Field.TYPE_DOUBLE :
                    sb.append(quote + f.name + quoteColon + (Double) f.value);
                    break;
                case Field.TYPE_LIST_RECORD:
                    sb.append(quote + f.name + quoteColon);
                    listJson((ListRecords) f.value);
                    break;
                case Field.TYPE_LIST_FIELD:
                    sb.append(quote + f.name + quoteColon);
                    listFieldsJson((ListFields) f.value);
                    break;
                case Field.TYPE_BOOLEAN:
                    sb.append(quote + f.name + quoteColon + (Boolean) f.value);
                    break;
            }
//            Log.d("QWERT","recordJson NN="+f.name+" TTTT="+f.type+"<< ST="+sb);
        }
        sb.append("}");
    }

    private void listJson(ListRecords listRecords) {
        sb.append("[");
        String separator = "";
        for (Record r : listRecords) {
            sb.append(separator);
            separator = ",";
            recordJson(r);
        }
        sb.append("]");
    }

    private void listFieldsJson(ListFields listFields) {
        sb.append("[");
        String separator = "";
        for (Field f : listFields) {
            sb.append(separator);
            switch (f.type) {
                case Field.TYPE_STRING :
                    sb.append(quote + (String) f.value + quote);
                    break;
                case Field.TYPE_INTEGER :
                    sb.append(String.valueOf((Integer) f.value));
                    break;
                case Field.TYPE_LONG :
                    sb.append(String.valueOf((Long) f.value));
                    break;
                case Field.TYPE_DOUBLE :
                    sb.append(String.valueOf((Double) f.value));
                    break;
            }
            separator = ",";
        }
        sb.append("]");
    }
}
