import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Git {
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
    public static String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 40 digits long
            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        
    }
    //Adds the filename and the hash into the index file
    public static void putInObjects(String fileName,String hash)
    {
        File ind = new File("git/objects/index");
        String toHash = "\n" + hash;
        String theFile = " " + fileName;
        if (ind.exists())
        {
            try (FileOutputStream outputStream = new FileOutputStream(ind)) {
                outputStream.write(toHash.getBytes());
                outputStream.write(theFile.getBytes());
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    //uses a file to create a hash and add it into the index file
    public static void MakeAndPlaceBlob(String fileName)
    {
        Path filePath = Paths.get(fileName);
        try {
            String content = Files.readString(filePath);
            String hash = encryptThisString(content);
            putInObjects(fileName, hash);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}