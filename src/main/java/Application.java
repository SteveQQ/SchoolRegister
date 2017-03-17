import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class Application {

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();

        TextTerminal terminal = textIO.getTextTerminal();
        terminal.printf("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _%n");
        terminal.printf(" _ _ _ _   _ _ _ _                _ _ _ _     _ _ _ _   _ _ _ _%n");
        terminal.printf("|         |       |  |           |        |  |         |        %n");
        terminal.printf("|_ _ _ _  |       |  |           |_ _ _ _ |  |_ _ _    |    _ _%n");
        terminal.printf("        | |       |  |           |   \\       |         |       |%n");
        terminal.printf(" _ _ _ _| |_ _ _ _|  |_ _ _      |    \\      |_ _ _ _  |_ _ _ _|     %n");
        terminal.printf("                  \\                 %n");
        terminal.printf("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _%n");
    }
}
