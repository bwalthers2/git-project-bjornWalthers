import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HashingBlobTester {
    public static void main(String[] args) throws IOException {
        Git tester = new Git();
        // Testing File lotta stuff but it works
        String data = "abarkawoeno";
        String data2 = "mynameis";
        String newline = "\n";
        String fileName3 = "TestingHash1.txt";
        String fileName4 = "TestingHash2.txt";
        String dirName = "TestingDirectory";
        String dirInside = "TestingDirectory/nestedDir";
        String fileInside = "TestingDirectory/nestedFile";
        String doubleNestDir = "TestingDirectory/nestedDir/anothaOne";
        String doubleNestFile = "TestingDirectory/nestedDir/anothaOne/finalOne";
        String doubleDirInDir = "TestingDirectory/nestedDir/twoDirsInside";
        File secondDirectoryInDir = new File(doubleDirInDir);
        File nestFileInDir = new File(fileInside);
        File doubleNestFilePointer = new File(doubleNestFile);
        File doubleDir = new File(doubleNestDir);
        File testDir = new File(dirName);
        File dirInsideFile = new File(dirInside);
        FileWriter myWriter = new FileWriter("TestingDirectory/nestedDir/anothaOne/finalOne");
        myWriter.write("Testing Object Contents");
        myWriter.close();
        testDir.mkdir();
        dirInsideFile.mkdir();
        nestFileInDir.createNewFile();
        doubleDir.mkdir();
        doubleNestFilePointer.createNewFile();
        secondDirectoryInDir.mkdir();

        /** 
        tester.MakeAndPlaceIndex(dirName);

        // filling testFile 1 with words
        try (FileOutputStream outputStream = new FileOutputStream(fileName3)) {
            outputStream.write(data.getBytes());
            outputStream.write(newline.getBytes());
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tester.MakeAndPlaceIndex(fileName3);

        // filling testFile 2 with words
        try (FileOutputStream outputStream = new FileOutputStream(fileName4)) {
            outputStream.write(data2.getBytes());
            outputStream.write(newline.getBytes());
            outputStream.write(data2.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tester.MakeAndPlaceIndex(fileName4);

        System.out.println();
        Path filePath = Paths.get(fileName3);
        try {
            String content = Files.readString(filePath);
            System.out.println("The contents of " + fileName3 + " are " + content);
            System.out.println("The hash of this file is " + tester.encryptThisString(content));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Path filePath2 = Paths.get(fileName4);
        try {
            String content = Files.readString(filePath2);
            System.out.println("The contents of " + fileName4 + " are " + content);
            System.out.println("The hash of this file is " + tester.encryptThisString(content));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File ind = new File("git/index");
        System.out.println(tester.CheckTheString("Test", ind));
        // Git.AddFileContents(fileName3);
        // Git.AddFileContents(fileName4);
        Git.printIndexForTesting();
        // Git.printObjectsContentsTesting();
        // tester.AddFileContents(fileName3);
        // tester.AddFileContents(fileName4);
        //tester.removeTestFiles();

        */

        tester.stage("TestingDirectory");
        
    }
}
