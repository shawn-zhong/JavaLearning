package com.tseong.learning.others.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JdbcDemo {

    private static final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String databaseUrl = "jdbc:sqlserver://192.168.30.241:1433;DatabaseName=PCRF";
    private static final String userName = "PCRF";
    private static final String password = "PCRF";
    private static Connection dbConn;

    static {
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(databaseUrl, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

    public static void TestMetaData() throws Exception {
        String metaDataCatalogName = "PCRF";
        String metaDataSchemaName = "dbc";
        String procedureName = "PCRF_GET_SESSION_CHANGE_LIST_LOCAL";
        DatabaseMetaData metaData = dbConn.getMetaData();
        ResultSet procs = metaData.getProcedures(metaDataCatalogName, metaDataSchemaName, procedureName);

        List<String> found = new ArrayList<>();

        while (procs.next()) {
            found.add(procs.getString("PROCEDURE_CAT") + "." + procs.getString("PROCEDURE_SCHEM") +
                    "." + procs.getString("PROCEDURE_NAME"));
        }

        found.size();
        procs.close();

        procs = metaData.getProcedureColumns(metaDataCatalogName, metaDataSchemaName, procedureName, null);
        List<String> result = new LinkedList<>();

        while (procs.next()) {
            String columnName = procs.getString("COLUMN_NAME");
            int columnType = procs.getInt("COLUMN_TYPE");
            if (columnName == null && (
                    columnType == DatabaseMetaData.procedureColumnIn ||
                            columnType == DatabaseMetaData.procedureColumnOut ||
                            columnType == DatabaseMetaData.procedureColumnInOut)) {
            } else {
                String a = String.format("%d %s %s", procs.getInt("DATA_TYPE"), procs.getString("TYPE_NAME"), procs.getBoolean("NULLABLE"));
                result.add(a);
            }
        }


        result.size();
    }

    public static void queryTest() throws Exception {

        // base query test
        Statement stmt = dbConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT 1022 as MyCol");
        while (rs.next()) {
            System.out.println(rs.getInt("MyCol"));
        }
        stmt.close();

        // procedure call test
        CallableStatement proc = dbConn.prepareCall("{ call PCRF_TESTFUNC_REMOTE(?)}");
        proc.setInt(1, 124);    // para 1
        ResultSet rs2 = proc.executeQuery();

        while (rs2.next()) {
            System.out.println(rs2.getInt("VALCOL"));
        }
        proc.close();

        // procedure call test2 : synonym
        CallableStatement proc2 = dbConn.prepareCall("{ call PCRF_GET_SESSION_CHANGE_LIST(?,?,?)}");
        proc2.setString(1, "CRM_CUSTOMERS_PROMOTION");
        proc2.setInt(2, 1);
        proc2.setInt(3, 1);
        ResultSet rs3 = proc2.executeQuery();

        while (rs3.next()) {
            System.out.println(rs3.getString("SESSION_ID"));
            System.out.println(rs3.getString("PEER_ID"));
            System.out.println(rs3.getString("SUBSCRIPTION_E164"));
        }
    }

}
