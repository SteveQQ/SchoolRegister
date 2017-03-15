package jdbc;

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
        List<Subject> subjects = manager.selectAllSubjects();

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
}