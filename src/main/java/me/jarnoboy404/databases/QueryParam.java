package me.jarnoboy404.databases;

import java.util.ArrayList;
import java.util.List;

public class QueryParam {

    private List<String> keys, values;
    
    public QueryParam() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }
    
    public void addQueryParam(String key, String value) {
        keys.add(key);
        values.add(value);
    }
    
    public String convertKeys() {
        String query = "(";
        for (String key : keys) {
            if (query.equals("(")) {
                query += "`" + key + "`";
            } else {
                query += ",`" + key + "`";
            }
        }
        return query + ")";
    }

    public String convertValues() {
        String query = "(";
        for (String value : values) {
            if (query.equals("(")) {
                query += "'" + value + "'";
            } else {
                query += ",'" + value + "'";
            }
        }
        return query + ")";
    }

    public String convertKeysAndValues() {
        String query = "";
        for (int i = 0; i < keys.size(); i++) {
            String value = "'" + values.get(i) + "'";
            if(values.get(i) == null) value = null;

            if(query.equals("")) {
                query += " `" + keys.get(i) + "` = " + value;
            }else {
                query += ", `" + keys.get(i) + "` = " + value;
            }
        }

        return query;
    }
}
