package me.jarnoboy404.databases;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class DatabaseConnection {

    private HikariDataSource ds;

    public DatabaseConnection(String host, Integer port, String database, String username, String password, int maxPools) throws HikariPool.PoolInitializationException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://<host>:<port>/<database>"
                .replaceAll("<host>", host)
                .replaceAll("<port>", port.toString())
                .replaceAll("<database>", database));
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        config.setMaximumPoolSize(maxPools);

        ds = new HikariDataSource(config);
    }

    protected DatabaseConnection(DatabaseConnection databaseConnection) {
        ds = databaseConnection.getDataSource();
    }

    protected void executeUpdate(String query) {
        try(Connection con = getDataSource().getConnection()) {
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected DatabaseResult executeQuery(String query) {
        try(Connection con = getDataSource().getConnection()) {
            PreparedStatement statement = con.prepareStatement(query);

            return new DatabaseResult(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected CompletableFuture<Void> executeUpdateAsync(String query) {
        return CompletableFuture.runAsync(() -> {
            try(Connection con = getDataSource().getConnection()) {
                PreparedStatement statement = con.prepareStatement(query);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    protected CompletableFuture<DatabaseResult> executeQueryAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try(Connection con = getDataSource().getConnection()) {
                PreparedStatement statement = con.prepareStatement(query);

                return new DatabaseResult(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public void closeDatabase() {
        ds.close();
    }

    public HikariDataSource getDataSource() {
        return ds;
    }
}
