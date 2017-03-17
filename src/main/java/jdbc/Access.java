package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by SteveQ on 2017-03-17.
 */
public class Access {

    private static Properties prop = new Properties();

    public Access() {}

    static{
        try {
            prop.load(Access.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_register", prop.getProperty("dbuser"), prop.getProperty("dbpassword"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
