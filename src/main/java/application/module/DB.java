package application.module;

import java.sql.*;

public class DB {
    private static Connection con;

    public static void init(String driver,String config) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        con = DriverManager.getConnection(config);
    }

    public static <T> void store(T entry) throws Exception{
        PreparedStatement s = buildTStatement(entry);
        executeStatement(s);
    }

    private static <T> PreparedStatement buildTStatement(T entry) throws SQLException {
        String sql = "";
        PreparedStatement s = con.prepareStatement(sql);
        return s;
    }

    private static void executeStatement(PreparedStatement s) throws SQLException {
        s.execute();
        s.close();
    }

    public static void close() throws SQLException {
        con.close();
    }
}
