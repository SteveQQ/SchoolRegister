package jdbc;

import model.Subject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SteveQ on 2017-03-17.
 */
public class SubjectsManager {

    public static final String SUBJECTS_TABLE = "subjects_table";
    public static final String MOCK_SUBJECTS_TABLE = "subjects_table2";

    public List<Subject> selectAllSubjects(String tableName, Connection c){
        Connection conn = null;
        if(c == null){
            conn = Access.getConnection();
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

    public Subject createSubject(Subject subject, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = Access.getConnection();
        } else {
            conn = c;
        }
        Statement stat = null;
        ResultSet res = null;
        Integer lastId = -1;
        try {
            stat = conn.createStatement();
            stat.executeUpdate("insert into " + tableName + " (SubjectName) " + "value (\'" + subject.getSubjectName() + "\');");
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
        subject.setId(lastId);
        return subject;
    }

    public Boolean deleteSubject(Integer id, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = Access.getConnection();
        } else {
            conn = c;
        }

        Statement stat = null;
        ResultSet res = null;
        int result = 0;
        try {
            stat = conn.createStatement();
            result = stat.executeUpdate("delete from " + tableName + " where " +
                    "Id = " + id + ";"
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
        Connection conn = Access.getConnection();
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
