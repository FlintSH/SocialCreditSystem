package com.earthpol.socialcredit.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    //Move This To A Config!
    private String host = "localhost";
    private String port = "3306";
    private String database = "ccp";
    private String username = "ccp";
    private String password = "ccp"; //Testing Password (Not actually used anywhere)

    private Connection connection;

    public boolean isConnected() {return(connection != null);}

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=true", username, password);
        }
    }

    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
