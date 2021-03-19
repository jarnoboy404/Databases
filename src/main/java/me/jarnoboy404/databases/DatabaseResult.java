package me.jarnoboy404.databases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseResult {

    private ResultSet resultSet;

    public DatabaseResult(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public boolean getResult() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void endResult() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getInt(String arg) {
        try {
            return resultSet.getInt(arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getDouble(String arg0) {
        try {
            return resultSet.getDouble(arg0);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getLong(String arg) {
        try {
            return resultSet.getLong(arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String arg) {
        try {
            return resultSet.getString(arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getBoolean(String arg) {
        try {
            return resultSet.getBoolean(arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getDate(String arg) {
        try {
            return resultSet.getDate(arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
