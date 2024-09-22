
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HashingBlobTester {
    public static void main(String[] args) throws IOException {
        Git tester = new Git();
        //Testing File
        String data = "abarkawoeno";
        String data2 = "mynameis";
        String newline = "\n";
        String fileName3 = "TestingHash1.txt";
        String fileName4 = "TestingHash2.txt";
        try (FileOutputStream outputStream = new FileOutputStream(fileName3)) {
            outputStream.write(data.getBytes());
            outputStream.write(newline.getBytes());
            outputStream.write(data.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
        tester.MakeAndPlaceIndex(fileName3);

        try (FileOutputStream outputStream = new FileOutputStream(fileName4)) {
            outputStream.write(data2.getBytes());
            outputStream.write(newline.getBytes());
            outputStream.write(data2.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
        tester.MakeAndPlaceIndex(fileName4);

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
        tester.AddFileContents(fileName3);
        tester.AddFileContents(fileName4);

    }
}

