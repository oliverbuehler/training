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
import org.junit.Assert;
import org.junit.BeforeClass;

public class DatabaseTest {

	private static DataSource DATA_SOURCE;

	@BeforeClass
	public static void setUpClass() {
		// H2
		try {
			Class.forName(org.h2.Driver.class.getName());
		} catch (ClassNotFoundException e) {
			Assert.fail("Couldn't load H2 DB driver");
		}
		JdbcDataSource h2dataSource = new JdbcDataSource();
		// Server
		// h2dataSource.setUrl("jdbc:h2:tcp://localhost:9092/test");
		// h2dataSource.setUser("sa");
		// h2dataSource.setPassword(password);

		// Memory
		h2dataSource.setUrl("jdbc:h2:mem:test");
		DATA_SOURCE = h2dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return DATA_SOURCE.getConnection();
	}

	public static List<Map<String, Object>> executeSqlQuery(String sql)
			throws SQLException {
		return executeSqlQuery(sql, getConnection(), true);
	}

	public static List<Map<String, Object>> executeSqlQuery(String sql,
			Connection conn) throws SQLException {
		return executeSqlQuery(sql, getConnection(), false);
	}

	private static List<Map<String, Object>> executeSqlQuery(String sql,
			Connection conn, boolean closeConn) throws SQLException {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			final ResultSet rs = statement.executeQuery(sql);
			final ResultSetMetaData metaData = rs.getMetaData();
			final int nbrOfColumns = metaData.getColumnCount();
			final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				final Map<String, Object> dataRow = new HashMap<String, Object>(
						nbrOfColumns);
				for (int col = 1; col <= nbrOfColumns; col++) {
					dataRow.put(metaData.getColumnName(col), rs.getObject(col));
				}
				result.add(dataRow);
			}
			return result;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (conn != null && closeConn) {
				conn.close();
			}
		}
	}

	public static int executeSql(String sql) throws SQLException {
		return executeSql(sql, getConnection(), true);
	}

	public static int executeSql(String sql, Connection conn)
			throws SQLException {
		return executeSql(sql, conn, false);
	}

	private static int executeSql(String sql, Connection conn,
			boolean closeConn) throws SQLException {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.execute(sql);
			int updateCount = statement.getUpdateCount();
			conn.commit();
			return updateCount;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (conn != null && closeConn) {
				conn.close();
			}
		}
	}

}
