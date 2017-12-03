package Util;

import java.sql.*;

/**
 * @Author: michael
 * @Date: 16-7-19 上午1:23
 * @Project: S.M.
 * @Package: Util
 */
public class SQLConnector {
    public static final String userName = "root";
    public static final String userPassword = "root";
    public static final String DriverName = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/UniPrinter?useUnicode=true&characterEncoding=UTF-8";
    public Connection con() {
        Connection con = null;
        try {
            Class.forName(DriverName);
            con = DriverManager.getConnection(URL, userName, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public ResultSet qurey (String sql) {
        ResultSet rs = null;
        try {
            PreparedStatement stmt = this.con().prepareStatement(sql);
            rs = stmt.executeQuery();
            System.out.println("已取得结果集");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public int update(String sql) throws SQLException {
        try {
            PreparedStatement stmt = this.con().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("SQL action failed.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public int update(String sql, boolean safemode) throws SQLException {
        try {
            PreparedStatement stmt = this.con().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0 && safemode) {
                throw new SQLException("SQL action failed.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    public static void closeResultSet(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
                rs = null;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
