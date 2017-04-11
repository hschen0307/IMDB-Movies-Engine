package com.company;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
i

/**
 * Created by Jerry on 2016/11/3.
 */
class Main {

    public static void main(String[] args) {
        // write your code here
        populate aPopulate = new populate();
        String[] temp = args.clone();
        aPopulate.setFiles(temp);
        aPopulate.run();
    }
}

public class populate {

        private Connection con;
        private ResultSet result;
        private String directory;
        private String fileGenres ;
        private String fileContries;
        private String fileMovies;
        private String fileActors;
        private String fileRatings;
        private String fileDirectors;
        private String fileTags;
        private String fileMovieTags;


    populate(){
        con = null;
        directory = new String();
        fileGenres = new String();
        fileContries = new String();
        fileMovies = new String();
        fileActors = new String();
        fileRatings = new String();
        fileDirectors = new String();
        fileTags = new String();
        fileMovieTags = new String();
        setDirectory();

    }

    public void setDirectory() {

        try {
            directory = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(directory);
    }

    public void setFiles(String[] allFiles){
        fileGenres = allFiles[0];
        fileContries = allFiles[1];
        fileMovies = allFiles[2];
        fileActors = allFiles[3];
        fileRatings = allFiles[4];
        fileDirectors = allFiles[5];
        fileTags = allFiles[6];
        fileMovieTags = allFiles[7];

        System.out.println("Genres"+fileGenres);
        System.out.println("Countries"+fileContries);
        System.out.println("Movies"+fileMovies);
        System.out.println("Actors"+fileActors);
        System.out.println("Ratings"+fileRatings);
        System.out.println("Directors"+fileDirectors);
        System.out.println("Tags"+fileTags);
        System.out.println("MovieTags"+fileMovieTags);
    }
    public void run() {
        try
        {
            con = openConnection();
            System.out.println("Success");
            insert_movies();
            insert_countries();
            insert_movie_genres();
            insert_actors();
            insert_directors();
            insert_tags();
            insert_movie_tags();
            insert_user_ratings();

        } catch (
                SQLException e)

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

    private Connection openConnection() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String host = "localhost";
        String port = "1521";
        String dbName = "xe";
        String userName = "HW2";
        String password = "1qazse4!QAZSE$";
        String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
        return DriverManager.getConnection(dbURL, userName, password);
    }

    private void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private ResultSet searchAllTuples(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String Query = "select distinct genre\n" +
                "from MOVIE_GENRES MG\n"+"";
        return stmt.executeQuery(Query);//"SELECT * FROM Movie_Genres");
    }

    private void showMetaDataOfResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        for (int col = 1; col <= meta.getColumnCount(); col++) {
            System.out.println("Column: " + meta.getColumnName(col) + "\t, Type: " + meta.getColumnTypeName(col));
        }
    }

    private void showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        int tupleCount = 1;
        while (result.next()) {
           // System.out.print("Tuple " + tupleCount++ + " : ");
           // System.out.print("Tuple " + tupleCount++ + " : ");
            for (int col = 1; col <= meta.getColumnCount(); col++) {

                System.out.print("\"" + result.getString(col) + "\",");
            }
            System.out.println();
        }
    }
    private void insert_movie_genres() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileGenres)))
        {
            String sCurrentLine;
            String colName;
            colName= br.readLine();
            Statement stmt = con.createStatement();
            System.out.println("Genres");
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM Genres");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String [] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO Genres VALUES(?,?)");
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

    private void insert_movies() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileMovies))) {
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
                    if(temp[i].contains("\\N")){
                        stmt2.setString(i+1, null);
                    }
                    else{
                        stmt2.setString(i+1, temp[i]);
                    }
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

    private void insert_countries() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileContries))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM countries");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO countries VALUES(?,?)");
                for(int i = 0; i< temp.length; i++) {
                    stmt2.setString(i+1, temp[i]);
                }

                if(temp.length<colName.length)
                    stmt2.setString(2, null);
                stmt2.executeUpdate();
                stmt2.close();
            }
            stmt.close();
            System.out.println("Inserting Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insert_actors() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileActors))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM actors");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO actors VALUES(?,?,?)");
                for(int i = 0; i< colName.length-1; i++) {
                    stmt2.setString(i + 1, temp[i]);
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

    private void insert_directors() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileDirectors))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM directors");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO directors VALUES(?,?,?)");
                for(int i = 0; i< colName.length; i++) {
                    stmt2.setString(i + 1, temp[i]);
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

    private void insert_tags() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileTags))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM tags");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO tags VALUES(?,?)");
                for(int i = 0; i< colName.length; i++) {
                    stmt2.setString(i + 1, temp[i]);
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

    private void insert_movie_tags() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileMovieTags))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM movie_tags");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO movie_tags VALUES(?,?)");
                for(int i = 0; i< colName.length-1; i++) {
                    stmt2.setString(i + 1, temp[i]);
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

    private void insert_user_ratings() throws SQLException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory+"\\"+fileRatings))) {
            String sCurrentLine;
            String [] colName;
            colName = br.readLine().split("\t");
            System.out.println(colName.length);
            Statement stmt = con.createStatement();
            System.out.println("Deleting previous tuples ...");
            stmt.executeUpdate("DELETE FROM user_ratings");
            System.out.println("Inserting Data ...");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String[] temp = sCurrentLine.split("\t");
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO user_ratings VALUES(?,?,?,?)");
                for(int i = 0; i< colName.length-5; i++) {
                    stmt2.setString(i + 1, temp[i]);
                }

                String date = temp[5]+'-'+temp[4]+'-'+temp[3];
                stmt2.setDate(4, java.sql.Date.valueOf(date));
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

