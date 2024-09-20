import java.io.*;

public class Git {
    public static void main(String[] args) {

    }
    // setup command uses an integer variable countToRemove to check if everything exists. If this value is equal to three at the end, it will print out that everything exists.
    //setup first creates git, then git/objects, the git/objects/index
    public void setup() throws IOException
    {
        int countToRemove = 0;
        File git = new File("git");
        if (git.exists()){
            System.out.println("git exists");
            countToRemove = countToRemove + 1;
        }
        else
            git.mkdir();
        File obj = new File("git/objects");
        obj.mkdir();
        if (obj.exists()){
            System.out.println("object exists");
            countToRemove = countToRemove + 1;
        }
        else
            obj.mkdir();
        File ind = new File("git/objects/index");
        if (ind.createNewFile() == true)
        {
            System.out.println("file made");
        }
        else
        {
            countToRemove = countToRemove + 1;
        }
        if (countToRemove == 3)
        {
            System.out.println("Git repository exists");
            return;
        }
        return;
    }
    //removes the repository that we just made
    public void remove()
    {
        File ind = new File("git/objects/index");
        if (ind.delete())
        {
            System.out.println("deleted index");
        }
        File obj = new File("git/objects");
        if (obj.delete())
        {
            System.out.println("deleted object");
        }
        File git = new File("git");
        if (git.delete())
        {
            System.out.println("deleted git");
        }
    }
}