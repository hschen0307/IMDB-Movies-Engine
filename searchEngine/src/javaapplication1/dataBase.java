package javaapplication1;
import java.sql.*;
//package com.mkyong.io;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Jerry on 2016/11/3.
 */
public class dataBase {
        Connection con = null;
        ResultSet result = null;
    public void run() {
        try
        {
            con = openConnection();
            //System.out.println("Success");
            //result = searchAllTuples(con);
            
            //showMetaDataOfResultSet(result);
            //showResultSet(result);
        } catch (SQLException e)
        {
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
        } catch (
                ClassNotFoundException e)

        {
            System.err.println("Cannot find the database driver");
        } finally
        {
           closeConnection(con);
        }
    }

    public  Connection openConnection() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String host = "localhost";
        String port = "1521";
        String dbName = "xe";
        String userName = "HW2";
        String password = "1qazse4!QAZSE$";
        String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
        return DriverManager.getConnection(dbURL, userName, password);
    }

    public  void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    public  ResultSet searchAllTuples(Connection con, String Query) throws SQLException {
        Statement stmt = con.createStatement();
        /*
        String Query = "select distinct genre\n" +
                "from MOVIE_GENRES\n";*/
        return stmt.executeQuery(Query);//"SELECT * FROM Movie_Genres");
    }

    public  void showMetaDataOfResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        for (int col = 1; col <= meta.getColumnCount(); col++) {
            System.out.println("Column: " + meta.getColumnName(col) + "\t, Type: " + meta.getColumnTypeName(col));
        }
    }

    public  void showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        //int tupleCount = 1;
        while (result.next()) {
           // System.out.print("Tuple " + tupleCount++ + " : ");
           // System.out.print("Tuple " + tupleCount++ + " : ");
            for (int col = 1; col <= meta.getColumnCount(); col++) {

                System.out.print("\"" + result.getString(col) + "\",");
            }
            System.out.println();
         }
    }
 
    public  void insert_movie_genres() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\DataBase\\hetrec2011-movielens-2k-v2\\movie_genres.dat")))
        {
            String sCurrentLine;
            String colName;
            colName= br.readLine();
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM Movie_Genres");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                String [] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO Movie_Genres VALUES(?,?)");
                stmt2.setString(1, temp[0]);
                stmt2.setString(2, temp[1]);
                stmt2.executeUpdate();
                stmt2.close();
            }
            stmt.close();
            System.out.println("Inserting Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void insert_movies() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\DataBase\\hetrec2011-movielens-2k-v2\\movies.dat"))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            //System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM Movies");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO Movies VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                for(int i = 0; i<colName.length; i++) {
                    stmt2.setString(i+1, temp[i]);
                }
                stmt2.executeUpdate();
                stmt2.close();
            }
            stmt.close();
            System.out.println("Inserting Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    



}

