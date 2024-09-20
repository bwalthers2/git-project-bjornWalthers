import java.io.IOException;
import java.io.*;

public class RepositoryTester {
    public static void main(String[]args) throws IOException
    {
        //Testing setup
        Git tester = new Git();
        tester.setup();
        tester.remove();
    }
}
