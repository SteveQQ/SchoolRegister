import jdbc.DbManager;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class Application {
    private static DbManager database;

    private Application() {
        database = new DbManager();
    }

    public static void main(String[] args) {
        Application app = new Application();
        Application.database.selectAllSubjects();
    }
}
