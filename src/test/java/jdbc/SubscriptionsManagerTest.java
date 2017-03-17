package jdbc;

import model.Student;
import model.Subject;
import model.Subscription;
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
public class SubscriptionsManagerTest {


    private SubscriptionsManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new SubscriptionsManager();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void createSubscriptionSuccessful() throws Exception {
        Connection conn = manager.createMockSubscriptionsTable();
        Boolean result = false;
        if(conn != null){
            result = manager.createSubscription(new Student(
                            1,
                            "John",
                            "Doe",
                            "1993-01-01",
                            "asdf@asd.com",
                            "sunflower street",
                            "Warsaw",
                            "12-123",
                            12
                    ),
                    new Subject(
                            2,
                            "WF"
                    ),
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    true);
            assertTrue(result);
        }

    }

    @Test
    public void getBasicInfoAboutStudentsSubscription() throws Exception {
        Connection conn = manager.createMockSubscriptionsTable();
        List<Subject> result;
        if(conn != null){
            Student s = new Student(
                    1,
                    "John",
                    "Doe",
                    "1993-01-01",
                    "asdf@asd.com",
                    "sunflower street",
                    "Warsaw",
                    "12-123",
                    12
            );
            manager.createSubscription(s,
                    new Subject(
                            2,
                            "WF"
                    ),
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    false);
            manager.createSubscription(s,
                    new Subject(
                            4,
                            "WF2"
                    ),
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    false);
            result = manager.selectSubjectsForStudent(s,
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    true);
            assertTrue(result.size() == 2);
        }
    }

    @Test
    public void gettingCompleteInformationAboutStudentsSubscriptions() throws Exception {
        Connection conn = manager.createMockSubscriptionsTable();
        List<Subscription> result;
        if(conn != null){
            Student s = new Student(
                    1,
                    "John",
                    "Doe",
                    "1993-01-01",
                    "asdf@asd.com",
                    "sunflower street",
                    "Warsaw",
                    "12-123",
                    12
            );
            manager.createSubscription(s,
                    new Subject(
                            2,
                            "WF"
                    ),
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    false);
            manager.createSubscription(s,
                    new Subject(
                            4,
                            "WF2"
                    ),
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    false);
            result = manager.selectSubjectsGradesForStudent(s,
                    SubscriptionsManager.MOCK_SUBSCRIPTIONS_TABLE,
                    conn,
                    true);
            assertTrue(result.size() == 2);
        }
    }
}