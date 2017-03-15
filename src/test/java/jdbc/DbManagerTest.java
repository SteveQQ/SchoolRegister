package jdbc;

import model.Student;
import model.Subject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class DbManagerTest {

    private DbManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new DbManager();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void selectingAllSubjectsFromSubjectsTable() throws Exception {
        List<Subject> subjects = manager.selectAllSubjects(DbManager.SUBJECTS_TABLE, null);

        assertTrue(subjects.size() > 0);
    }

    @Test
    public void selectingAllSubjectsFromSubjectsTableWhenEmpty() throws Exception{
        Connection conn = manager.createMockSubjectTable();
        if(conn != null){
            exception.expect(IllegalStateException.class);
            manager.selectAllSubjects(DbManager.MOCK_SUBJECTS_TABLE, conn);
        }
    }

    @Test
    public void createStudentCompleted() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null){
            Integer result = manager.createStudent(new Student(
                    "John",
                    "Doe",
                    "1993-01-01",
                    "asdf@asd.com",
                    "sunflower street",
                    "Warsaw",
                    "12-123",
                    12
            ),
                    DbManager.MOCK_STUDENTS_TABLE,
                    conn,
                    true);
            assertTrue(result > 0);
        }
    }

    @Test
    public void getStudentWhenFullNameNotDuplicates() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null){
            manager.createStudent(new Student(
                            "John",
                            "Doe",
                            "1993-01-01",
                            "asdf@asd.com",
                            "sunflower street",
                            "Warsaw",
                            "12-123",
                            12
                    ),
                    DbManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            manager.createStudent(new Student(
                            "Mike",
                            "Hike",
                            "1993-02-02",
                            "sdfg@asd.com",
                            "rasberry street",
                            "Warsaw",
                            "78-123",
                            15
                    ),
                    DbManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            List<Student> result = manager.selectStudentsByFullName("John", "Doe", DbManager.MOCK_STUDENTS_TABLE, conn);
            assertTrue(result.size() == 1);
        }
    }

    @Test
    public void getAllStudentsWhenFullNameDuplicates() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null){
            manager.createStudent(new Student(
                            "John",
                            "Doe",
                            "1993-01-01",
                            "asdf@asd.com",
                            "sunflower street",
                            "Warsaw",
                            "12-123",
                            12
                    ),
                    DbManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            manager.createStudent(new Student(
                            "John",
                            "Doe",
                            "1993-02-02",
                            "sdfg@asd.com",
                            "rasberry street",
                            "Warsaw",
                            "78-123",
                            15
                    ),
                    DbManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            List<Student> result = manager.selectStudentsByFullName("John", "Doe", DbManager.MOCK_STUDENTS_TABLE, conn);
            assertTrue(result.size() == 2);
        }
    }
}