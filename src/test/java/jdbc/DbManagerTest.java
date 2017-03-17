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
            Student result = manager.createStudent(new Student(
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
            assertTrue(result.getId() != null);
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

    @Test
    public void selectingStudentById() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null){
            Student student1 = manager.createStudent(new Student(
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
            Student student2 = manager.createStudent(new Student(
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
            Student result = manager.selectStudentById(student1.getId(), DbManager.MOCK_STUDENTS_TABLE, conn);
            assertTrue(result.equals(student1));
        }
    }

    @Test
    public void studentUpdatedCorrectly() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null) {
            Student newStudent = manager.createStudent(new Student(
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

            newStudent.setFirstName("Jack");
            newStudent.setCity("Lodz");
            Boolean result = manager.updateStudentInfo(newStudent, DbManager.MOCK_STUDENTS_TABLE, conn, false);
            Student updatedStudent = manager.selectStudentById(newStudent.getId(), DbManager.MOCK_STUDENTS_TABLE, conn);
            assertTrue(updatedStudent.equals(newStudent));
        }

    }

    @Test
    public void selectAllStudentsFromTable() throws Exception {
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
            List<Student> result = manager.selectAllStudents(DbManager.MOCK_STUDENTS_TABLE, conn);
            assertTrue(result.size() == 2);
        }

    }

    @Test
    public void studentDeleteCorrect() throws Exception {
        Connection conn = manager.createMockStudentsTable();
        if(conn != null) {
            Student newStudent = manager.createStudent(new Student(
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

            Boolean result = manager.deleteStudent(newStudent.getId(), DbManager.MOCK_STUDENTS_TABLE, conn, false);
            assertTrue(result);
            exception.expect(IllegalStateException.class);
            manager.selectAllStudents(DbManager.MOCK_STUDENTS_TABLE, conn);
        }
    }
}