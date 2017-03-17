package jdbc;

import model.Student;
import model.Subject;
import model.Subscription;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SteveQ on 2017-03-17.
 */
public class SubscriptionsManager {

    public static final String SUBSCRIPTIONS_TABLE = "subjects_subscriptions_table";
    public static final String MOCK_SUBSCRIPTIONS_TABLE = "subjects_subscriptions_table2";

    public Boolean createSubscription(Student student, Subject subject, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = Access.getConnection();
        } else {
            conn = c;
        }
        Statement stat = null;
        ResultSet res = null;
        Integer result = 0;
        try {
            stat = conn.createStatement();
            result = stat.executeUpdate("insert into " + tableName +
                    " (StudentId, SubjectId) values " +
                    "(" + student.getId() + ", " +
                    subject.getId() +
                    ");"
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

    public List<Subject> selectSubjectsForStudent(Student student, String tableName, Connection c, Boolean close){
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
            res = stat.executeQuery("SELECT StudentId, SubjectId, st.SubjectName, Grade FROM " + tableName +
                    " join subjects_table as st on SubjectId = st.Id" +
                    " where StudentId = " + student.getId() + " group by SubjectId order by SubjectId asc");
            List<Subject> subjs = new ArrayList<Subject>();
            if(res.last()) {
                res.beforeFirst();
                while (res.next()) {
                    result.add(new Subject(res.getInt("SubjectId"),
                                res.getString("SubjectName")));
                }
            } else {
                throw new IllegalStateException("No data in [subjects_table]");
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
        return result;
    }

    public List<Subscription> selectSubjectsGradesForStudent(Student student, String tableName, Connection c, Boolean close){
        Connection conn = null;
        if(c == null){
            conn = Access.getConnection();
        } else {
            conn = c;
        }

        List<Subscription> result = new ArrayList<Subscription>();
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = conn.createStatement();
            List<Subject> subjects = selectSubjectsForStudent(student, tableName, c, false);

            res = stat.executeQuery("SELECT StudentId, SubjectId, st.SubjectName, Grade FROM " + tableName +
                    " join subjects_table as st on SubjectId = st.Id" +
                    " where StudentId = " + student.getId() + " order by SubjectId asc");
            Map<Integer,List<Integer>> gradesMap = new HashMap<Integer, List<Integer>>();
            while(res.next()){
                List<Integer> gradesList = gradesMap.get(res.getInt("SubjectId"));
                if(gradesList == null){
                    gradesList = new ArrayList<Integer>();
                    gradesList.add(res.getInt("Grade"));
                    gradesMap.put(res.getInt("SubjectId"), gradesList);
                } else {
                    gradesList.add(res.getInt("Grade"));
                    gradesMap.put(res.getInt("SubjectId"), gradesList);
                }
            }
            List<Subscription> subs = new ArrayList<Subscription>();
            for(Subject sub : subjects){
                subs.add(new Subscription(student,
                        sub,
                        gradesMap.get(sub.getId())));
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
        return result;
    }

    //---------------TEST PURPOSES METHODS----------------
    public Connection createMockSubscriptionsTable(){
        Connection conn = Access.getConnection();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.execute("CREATE TEMPORARY TABLE IF NOT EXISTS " + MOCK_SUBSCRIPTIONS_TABLE + " (" +
                    " Id integer, " +
                    " StudentId integer, " +
                    " SubjectId integer, " +
                    " Grade integer" +
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
