package ch.agility.training.jpa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

public class DatabaseHelper {

    public static enum DatabaseMode {
        MEMORY, EMBEDDED, SERVER;
    }

    private static DataSource dataSource;

    private static Connection keepRunningConn;

    public static void startDatabase(DatabaseMode databaseMode) throws Exception {
        Class.forName(org.h2.Driver.class.getName());

        JdbcDataSource h2dataSource = new JdbcDataSource();
        switch (databaseMode) {
            case MEMORY:
                h2dataSource.setUrl("jdbc:h2:mem:test");
                break;
            case EMBEDDED:
                h2dataSource.setUrl("jdbc:h2:/D:/Daten/H2/test");
                break;
            case SERVER:
                // org.h2.tools.Server server = new Server();
                // Server.createTcpServer(args)
                h2dataSource.setUrl("jdbc:h2:tcp://localhost:9092/test");
                break;
            default:
                break;
        }

        h2dataSource.setUser("sa");
        h2dataSource.setPassword("");
        dataSource = h2dataSource;

        keepRunningConn = getConnection();
    }

    public static void stopDatabase() throws SQLException {
        if (keepRunningConn != null) {
            keepRunningConn.close();
        }
    }

    private static Connection getConnection() throws SQLException {
         return dataSource.getConnection();
//        return DriverManager.getConnection("jdbc:h2:/D:/Daten/H2/test", "sa", "");
        // return DriverManager.getConnection("jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS JPA", "sa", "");
        // return DriverManager.getConnection("jdbc:h2:/D:/Daten/H2/test;INIT=CREATE SCHEMA IF NOT EXISTS JPA", "sa",
        // "");
    }

    public static List<Map<String, Object>> executeSqlQuery(String sql) throws SQLException {
        return executeSqlQuery(sql, getConnection());
    }

    public static List<Map<String, Object>> executeSqlQuery(String sql, Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(sql);
            final ResultSetMetaData metaData = rs.getMetaData();
            final int nbrOfColumns = metaData.getColumnCount();
            final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                final Map<String, Object> dataRow = new HashMap<String, Object>(nbrOfColumns);
                for (int col = 1; col <= nbrOfColumns; col++) {
                    dataRow.put(metaData.getColumnName(col), rs.getObject(col));
                }
                result.add(dataRow);
            }
            return result;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static int executeSql(String sql) throws SQLException {
        return executeSql(sql, getConnection());
    }

    public static int executeSql(String sql, Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            int updateCount = statement.getUpdateCount();
            connection.commit();
            return updateCount;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

}
