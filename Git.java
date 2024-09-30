import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Git {
    // setup command uses an integer variable countToRemove to check if everything
    // exists. If this value is equal to three at the end, it will print out that
    // everything exists.
    // setup first creates git, then git/objects, the git/objects/index
    public void setup() throws IOException {
        int countToRemove = 0;
        File git = new File("git");
        if (git.exists()) {
            System.out.println("git exists");
            countToRemove = countToRemove + 1;
        } else
            git.mkdir();
        File obj = new File("git/objects");
        obj.mkdir();
        if (obj.exists()) {
            System.out.println("object exists");
            countToRemove = countToRemove + 1;
        } else
            obj.mkdir();
        File ind = new File("git/index");
        if (ind.createNewFile() == true) {
            System.out.println("file made");
        } else {
            countToRemove = countToRemove + 1;
        }
        if (countToRemove == 3) {
            System.out.println("Git repository exists");
            return;
        }
        return;
    }

    // removes the repository that we just made
    public void remove() {
        File ind = new File("git/index");
        if (ind.delete()) {
            System.out.println("deleted index");
        }
        File obj = new File("git/objects");
        if (obj.delete()) {
            System.out.println("deleted object");
        }
        File git = new File("git");
        if (git.delete()) {
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

    // Adds the filename and the hash into the index file
    public static void putInIndex(String fileName, String hash) {
        File ind = new File("git/index");
        File addBlobOrTree = new File(fileName);
        String writeStringIndex;
        if (addBlobOrTree.isDirectory()) {
            writeStringIndex = "\n" + "tree " + hash + " " + addBlobOrTree.getPath();
        } else {
            writeStringIndex = "\n" + "blob " + hash + " " + addBlobOrTree.getPath();
        }
        if (ind.exists()) {
            try (FileOutputStream outputStream = new FileOutputStream(ind, true)) {
                outputStream.write(writeStringIndex.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // uses a file to create a hash and add it into the index file
    public static void MakeAndPlaceIndex(String fileName) {
        Path filePath = Paths.get(fileName);
        File checkIfDirFile = new File(fileName);
        try {
            if (checkIfDirFile.isDirectory()) {
                File[] containedFilesArray = checkIfDirFile.listFiles();
                for (int dirCheckInt = 0; dirCheckInt < containedFilesArray.length; dirCheckInt++) {
                    if (containedFilesArray[dirCheckInt].isDirectory()) {
                        String recursionDirName = "";
                        recursionDirName += containedFilesArray[dirCheckInt];
                        MakeAndPlaceIndex(recursionDirName);
                    } else {
                        String recursionFile = "";
                        recursionFile += containedFilesArray[dirCheckInt];
                        String hashedNestFile = encryptThisString(recursionFile);
                        putInIndex(recursionFile, hashedNestFile);
                        copyContentToObjects(containedFilesArray[dirCheckInt]);
                    }
                }
                String containedFilesString = "";
                for (int i = 0; i < containedFilesArray.length; i++) {
                    containedFilesString += containedFilesArray[i];
                }
                String dirHash = encryptThisString(containedFilesString);
                File ind = new File("git/index");
                if (!CheckTheString(dirHash, ind)) {
                    putInIndex(fileName, dirHash);
                } else {
                    System.out.println("this file already exists");
                }
            } else {
                String content = Files.readString(filePath);
                String hash = encryptThisString(content);
                File ind = new File("git/index");
                if (!CheckTheString(hash, ind)) {
                    putInIndex(fileName, hash);
                } else {
                    System.out.println("this file already exists");
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Checks if a specific string is in the indexFile, returns true if it is in,
    // returns false if not
    public static boolean CheckTheString(String str, File fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(str)) {
                br.close();
                return true;
            }
        }
        br.close();
        return false;

    }

    // Adds the file data to the objects folder
    public static void AddFileContents(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        String content = Files.readString(filePath);
        String codedDirectory = encryptThisString(fileName + content).substring(0, 2);
        File fileToAdd = new File("git/objects/" + codedDirectory);
        fileToAdd.mkdir();
        File Update = new File(
                "git/objects/" + codedDirectory + "/" + encryptThisString(fileName + content).substring(2));
        if (Update.createNewFile() == true) {
            System.out.println("blob made for the file" + fileName);
        }
        try (FileOutputStream outputStream = new FileOutputStream(Update)) {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Removes the files in objects so you can retest.
    public static void removeTestFiles() {
        File objdir = new File("git/objects");
        for (File subfile : objdir.listFiles()) {
            if (subfile.isDirectory()) {
                for (File hashfile : subfile.listFiles()) {
                    hashfile.delete();
                }
                subfile.delete();
            }
        }
        File ind = new File("git/index");
        String data = "";
        try (FileOutputStream outputStream = new FileOutputStream(ind)) {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printIndexForTesting() throws IOException {
        BufferedReader readIndexLine = new BufferedReader(new FileReader("git/index"));
        while (readIndexLine.ready()) {
            System.out.println(readIndexLine.readLine());
        }
        readIndexLine.close();
    }

    public static void recursionSaveFilesDirectorys(String recursionFileName) {

    }

    public static void copyContentToObjects(File inputCopy) throws IOException {
        FileInputStream in = new FileInputStream(inputCopy);
        File outputCopy = new File("git/objects/" + encryptThisString(inputCopy.getName()));
        FileOutputStream out = new FileOutputStream(outputCopy);

        try {
            int n;
            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        } finally {
            in.close();
            out.close();
        }
        outputCopy.createNewFile();
    }

}