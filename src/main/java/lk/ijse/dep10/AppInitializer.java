package lk.ijse.dep10;

import javafx.application.Application;
import javafx.stage.Stage;
import lk.ijse.dep10.dp.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                if(DBConnection.getInstance().getConnection()!=null && !DBConnection.getInstance().getConnection().isClosed()){
                    System.out.println("Database connection is about to close");
                    DBConnection.getInstance().getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }));

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        generateSchemaIfNotExist();

    }

    private void generateSchemaIfNotExist() {
        try {
            Connection connection =DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SHOW TABLES");

            HashSet<String>tableNameSet=new HashSet<>();
            while(rst.next()){
                tableNameSet.add(rst.getString(1));
            }
            boolean tableExists=tableNameSet.containsAll(Set.of("Attendance","Picture","Student","User"));
            System.out.println(tableExists);


            if (!tableExists){
                System.out.println("Schema is about to auto generate");
                stm.execute(readDBScript());

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String readDBScript(){
        InputStream is = getClass().getResourceAsStream("/schema.sql");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder dbScript = new StringBuilder();
        while (true){
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dbScript.append(line).append("\n");
        }
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dbScript.toString();
    }
}
