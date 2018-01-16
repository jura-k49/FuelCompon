package net.ukr.jura.compon.json_simple;


public class SimpleRecordToJson {
    private String quote = "\"";
    private String quoteColon = "\":";
    private StringBuffer sb;

    public String modelToJson(Field field) {
        sb = new StringBuffer( 1000);
        if (field.type == Field.TYPE_RECORD) {
            recordToJson((Record) field.value);
        } else {
            listToJson((ListRecords) field.value);
        }
        return sb.toString();
    }

    private void recordToJson(Record rec) {
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
                case Field.TYPE_LIST:
                    sb.append(quote + f.name + quoteColon);
                    listToJson((ListRecords) f.value);
                    break;
            }
        }
        sb.append("}");
    }

    private void listToJson(ListRecords listRecords) {
        sb.append("[");
        String separator = "";
        for (Record r : listRecords) {
            sb.append(separator);
            separator = ",";
            recordToJson(r);
        }
        sb.append("]");
    }
}
