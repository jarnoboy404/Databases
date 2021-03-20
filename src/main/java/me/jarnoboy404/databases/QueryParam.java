package me.jarnoboy404.databases;

import java.util.HashMap;
import java.util.Map;

public class QueryParam {

    private Map<String, Object> keysAndValues;
    
    public QueryParam() {
        keysAndValues = new HashMap<>();
    }
    
    public void addQueryParam(String key, Object value) {
        keysAndValues.put(key, value);
    }

    public String getUpdateQuery(String table, QueryParam whereQuery) {
        String query = "";
        for(Map.Entry<String, Object> entry : keysAndValues.entrySet()) {
            query += (query.equals("") ? "`" : ",`") + entry.getKey() + "`='" + entry.getValue() + "'";
        }
        return "UPDATE `" + table + "` SET " + query + whereQuery.getWhereQuery();
    }

    public String getInsertQuery(String table) {
        String keys = "";
        String values = "";
        for(Map.Entry<String, Object> entry : keysAndValues.entrySet()) {
            keys += (keys.equals("") ? "`" : ",`") + entry.getKey() + "`";
            values += (values.equals("") ? "'" : ",'") + entry.getValue() + "'";
        }
        return "INSERT INTO `" + table + "` (" + keys + ") VALUES (" + values + ")";
    }

    public String getWhereQuery() {
        String query = "";
        for(Map.Entry<String, Object> entry : keysAndValues.entrySet()) {
            query += (query.equals("") ? "`" : " AND `") + entry.getKey() + "`='" + entry.getValue() + "'";
        }
        return " WHERE " + query;
    }
}
