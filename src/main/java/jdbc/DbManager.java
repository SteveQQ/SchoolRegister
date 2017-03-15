package jdbc;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.Student;
import model.Subject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class DbManager {
    private Properties prop = new Properties();
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public static final String STUDENTS_TABLE = "students_table";
    public static final String SUBJECTS_TABLE = "subjects_table";
    public static final String MOCK_SUBJECTS_TABLE = "subjects_table2";
    public static final String SUBJECTS_SUBSCRIPTIONS_TABLE = "subjects_subscriptions_table";


    public DbManager(){
        try {
            prop.load(DbManager.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection(){
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

    public List<Subject> selectAllSubjects(){
        Connection conn = getConnection();
        List<Subject> result = new ArrayList<Subject>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM subjects_table;");
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Subject(res.getInt("Id"), res.getString("SubjectName")));
                }
            } else {
                throw new SQLException("No data in [subjects_table]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                if(stat != null) {
                    stat.close();
                }
                if(res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Integer createStudent(Student student){
        throw new NotImplementedException();
    }

    //---------------TEST PURPOSES METHODS----------------
    public List<Subject> selectAllSubjects(String tableName, Connection c){
        List<Subject> result = new ArrayList<Subject>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = c.createStatement();
            res = stat.executeQuery("SELECT * FROM " + tableName);
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Subject(res.getInt("Id"), res.getString("SubjectName")));
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table2]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
                if(stat != null) {
                    stat.close();
                }
                if(res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Connection createMockSubjectTable(){
        Connection conn = getConnection();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.execute("CREATE TEMPORARY TABLE IF NOT EXISTS " + MOCK_SUBJECTS_TABLE + " (Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, SubjectName VARCHAR(30) NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        return conn;
    }
}
