package jdbc;

import model.Student;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SteveQ on 2017-03-17.
 */
public class StudentsOperationsTest {
    private StudentsManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new StudentsManager();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    true);
            assertTrue(result.getId() > 0 );
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            List<Student> result = manager.selectStudentsByFullName("John", "Doe", StudentsManager.MOCK_STUDENTS_TABLE, conn);
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            List<Student> result = manager.selectStudentsByFullName("John", "Doe", StudentsManager.MOCK_STUDENTS_TABLE, conn);
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            Student result = manager.selectStudentById(student1.getId(), StudentsManager.MOCK_STUDENTS_TABLE, conn);
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);

            newStudent.setFirstName("Jack");
            newStudent.setCity("Lodz");
            Boolean result = manager.updateStudentInfo(newStudent, StudentsManager.MOCK_STUDENTS_TABLE, conn, false);
            Student updatedStudent = manager.selectStudentById(newStudent.getId(), StudentsManager.MOCK_STUDENTS_TABLE, conn);
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);
            List<Student> result = manager.selectAllStudents(StudentsManager.MOCK_STUDENTS_TABLE, conn);
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
                    StudentsManager.MOCK_STUDENTS_TABLE,
                    conn,
                    false);

            Boolean result = manager.deleteStudent(newStudent.getId(), StudentsManager.MOCK_STUDENTS_TABLE, conn, false);
            assertTrue(result);
            exception.expect(IllegalStateException.class);
            manager.selectAllStudents(StudentsManager.MOCK_STUDENTS_TABLE, conn);
        }
    }
}