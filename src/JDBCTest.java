import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class JDBCTest {
	static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	static String DB_URL = "";

	static String USER = "";
	static String PASS = "";
	static String SQL = "select * from DUAL";
	static long t0 = new Date().getTime();
	static long t1 = new Date().getTime();

	private static void delta(String text) {
		long now = new Date().getTime();
		System.out.println((now - t0) + " " + text + " " + (now - t1));
		t1 = now;
	}

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("JAVA_TOOL_OPTIONS=-Duser.name=<USER> ./jdbctest jdbc:oracle:thin:@<HOST>:<PORT>/<SID> <USER> <PASS> '[QUERY]'");
		DB_URL = args[0];
		USER = args[1];
		PASS = args[2];
		if (args.length>3) {
			SQL=args[3];
		}
		
		
		System.out.println(DB_URL+" "+USER+"/"+PASS);
		Connection conn = null;
		Statement stmt = null;
		Date t1 = new Date(), t2, t3;
		try {
			Class.forName(JDBC_DRIVER);
			delta("started");

			
				conn = getConnection();
				stmt = getStatement(conn);
				query(stmt);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end main

	private static void query(Statement stmt) throws SQLException {

		ResultSet query = stmt.executeQuery(SQL);
		delta("query...");
		query.next();
		
		delta("Database queried successfully..." + query.getObject(1) + " ");
	}

	private static Statement getStatement(Connection conn) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		delta("Creating statement...");
		return stmt;
	}

	private static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		delta("Connecting to database...");
		return conn;
	}
}