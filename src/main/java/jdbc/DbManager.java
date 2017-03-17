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
    public static final String MOCK_STUDENTS_TABLE = "students_table2";
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

    public List<Subject> selectAllSubjects(String tableName, Connection c){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }
        List<Subject> result = new ArrayList<Subject>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM " + tableName);
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Subject(res.getInt("Id"), res.getString("SubjectName")));
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table]");
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

    public Student createStudent(Student student, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }
        Statement stat = null;
        ResultSet res = null;
        Integer lastId = -1;
        try {
            stat = conn.createStatement();
            stat.executeUpdate("insert into " + tableName +
                    " (FirstName, LastName, DateOfBirth, ParentEmail, Street, City, PostalCode, HouseNumber) values " +
                    "(" +
                    "\'" + student.getFirstName() + "\', " +
                    "\'" + student.getLastName() + "\', " +
                    "\'" + student.getDateOfBirth() + "\', " +
                    "\'" + student.getParentEmail() + "\', " +
                    "\'" + student.getStreet() + "\', " +
                    "\'" + student.getCity() + "\', " +
                    "\'" + student.getPostalCode() + "\', " +
                    student.getHouseNumber() +
                    ");"
            );
            res = stat.executeQuery("select last_insert_id() as last_id from " + tableName);
            while (res.next()) {
                lastId = Integer.valueOf(res.getString("last_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(close) {
                    conn.close();
                }
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
        student.setId(lastId);
        return student;
    }

    public List<Student> selectAllStudents(String tableName, Connection c){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }

        List<Student> result = new ArrayList<Student>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM " + tableName);
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Student(
                            res.getInt("Id"),
                            res.getString("FirstName"),
                            res.getString("LastName"),
                            res.getString("DateOfBirth"),
                            res.getString("ParentEmail"),
                            res.getString("Street"),
                            res.getString("City"),
                            res.getString("PostalCode"),
                            res.getInt("HouseNumber")
                    ));
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table]");
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

    public Student selectStudentById(Integer id, String tableName, Connection c){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }

        Student result = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM " + tableName + " WHERE Id=" + id );
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result = new Student(
                            res.getInt("Id"),
                            res.getString("FirstName"),
                            res.getString("LastName"),
                            res.getString("DateOfBirth"),
                            res.getString("ParentEmail"),
                            res.getString("Street"),
                            res.getString("City"),
                            res.getString("PostalCode"),
                            res.getInt("HouseNumber")
                    );
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table]");
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

    public List<Student> selectStudentsByFullName(String firstName, String lastName, String tableName , Connection c){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }

        List<Student> result = new ArrayList<Student>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM " + tableName + " WHERE FirstName=\'" + firstName + "\' AND LastName=\'" + lastName +"\'");
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Student(
                            res.getInt("Id"),
                            res.getString("FirstName"),
                            res.getString("LastName"),
                            res.getString("DateOfBirth"),
                            res.getString("ParentEmail"),
                            res.getString("Street"),
                            res.getString("City"),
                            res.getString("PostalCode"),
                            res.getInt("HouseNumber")
                    ));
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table]");
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

    public boolean updateStudentInfo(Student student, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }

        Statement stat = null;
        ResultSet res = null;
        int result = 0;
        try {
            stat = conn.createStatement();
            result = stat.executeUpdate("update " + tableName + " set " +
                    "FirstName = \'" + student.getFirstName() + "\', " +
                    "LastName = \'" + student.getLastName() + "\', " +
                    "DateOfBirth = \'" + student.getDateOfBirth() + "\', " +
                    "ParentEmail = \'" + student.getParentEmail() + "\', " +
                    "Street = \'" + student.getStreet() + "\', " +
                    "City = \'" + student.getCity() + "\', " +
                    "PostalCode = \'" + student.getPostalCode() + "\', " +
                    "HouseNumber = \'" + student.getHouseNumber() + "\' " +
                    "where id = " + student.getId() +
                    ";"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(close) {
                    conn.close();
                }
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
        return result == 1;
    }

    public boolean deleteStudent(Integer Id, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = getConnection();
        } else {
            conn = c;
        }

        Statement stat = null;
        ResultSet res = null;
        int result = 0;
        try {
            stat = conn.createStatement();
            result = stat.executeUpdate("delete from " + tableName + " where " +
                    "Id = " + Id + ";"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(close) {
                    conn.close();
                }
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
        return result == 1;
    }

    //---------------TEST PURPOSES METHODS----------------

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

    public Connection createMockStudentsTable(){
        Connection conn = getConnection();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.execute("CREATE TEMPORARY TABLE IF NOT EXISTS " + MOCK_STUDENTS_TABLE + " (" +
                    " Id integer not null auto_increment primary key, " +
                    " FirstName varchar(30) not null, " +
                    " LastName varchar(30) not null, " +
                    " DateOfBirth date not null, " +
                    " ParentEmail varchar(255), " +
                    " Street varchar(50) not null, " +
                    " City varchar(50) not null, " +
                    " PostalCode varchar(6) not null, " +
                    " HouseNumber integer not null " +
                    ");");
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
