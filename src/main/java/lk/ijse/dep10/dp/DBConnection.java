package lk.ijse.dep10.dp;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;
    private DBConnection(){
        try {

            File file=new File("application.properties");
            Properties properties=new Properties();
            FileReader fr=new FileReader(file);
            properties.load(fr);
            fr.close();

            String host=properties.getProperty("mysql.host","127.0.0.1");
            String port=properties.getProperty("mysql.port","3306");
            String database=properties.getProperty("mysql.database","Student_attendance_database");
            String userName=properties.getProperty("mysql.username","root");
            String password=properties.getProperty("mysql.password","Amila@1993");

            String url="jdbc:mysql://"+host+":"+port+"/"+database+"?createDatabaseIfNotExist=true&allowMultiQueries=true";

            connection= DriverManager.getConnection(url,userName,password);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Configuration file does not exist").showAndWait();
            throw new RuntimeException(e);
        }

        catch (SQLException | IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to obtain the database Connection,Try again. If the problem persist please contact the technical team").showAndWait();
            throw new RuntimeException(e);
        }
    }
    public static DBConnection getInstance(){
        return (dbConnection==null)?dbConnection=new DBConnection():dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }

}

