package jdbc;

import model.Subject;
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
public class SubjectsOperationsTest {

    private SubjectsManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new SubjectsManager();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void selectingAllSubjectsFromSubjectsTable() throws Exception {
        List<Subject> subjects = manager.selectAllSubjects(SubjectsManager.SUBJECTS_TABLE, null);

        assertTrue(subjects.size() > 0);
    }

    @Test
    public void selectingAllSubjectsFromSubjectsTableWhenEmpty() throws Exception{
        Connection conn = manager.createMockSubjectTable();
        if(conn != null){
            exception.expect(IllegalStateException.class);
            manager.selectAllSubjects(SubjectsManager.MOCK_SUBJECTS_TABLE, conn);
        }
    }

    @Test
    public void creatingNewSubjectCorrect() throws Exception {
        Connection conn = manager.createMockSubjectTable();
        if(conn != null){
            Subject result = manager.createSubject(new Subject(
                            "WF"
                    ),
                    SubjectsManager.MOCK_SUBJECTS_TABLE,
                    conn, true);
            assertTrue(result.getId() > 0);
        }
    }

    @Test
    public void deleteSubjectCorrect() throws Exception {
        Connection conn = manager.createMockSubjectTable();
        if(conn != null) {
            Subject subject = manager.createSubject(new Subject(
                            "WF"
                    ),
                    SubjectsManager.MOCK_SUBJECTS_TABLE,
                    conn,
                    false);

            Boolean result = manager.deleteSubject(subject.getId(), SubjectsManager.MOCK_SUBJECTS_TABLE, conn, false);
            assertTrue(result);
            exception.expect(IllegalStateException.class);
            manager.selectAllSubjects(SubjectsManager.MOCK_SUBJECTS_TABLE, conn);
        }

    }
}