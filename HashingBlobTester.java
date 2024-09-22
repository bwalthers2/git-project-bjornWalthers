
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
        String data = "abarkawoenowe";
        String newline = "\n";
        String fileName3 = "TestingHash.txt";
        try (FileOutputStream outputStream = new FileOutputStream(fileName3)) {
            outputStream.write(data.getBytes());
            outputStream.write(newline.getBytes());
            outputStream.write(data.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
        tester.MakeAndPlaceBlob(fileName3);

        Path filePath = Paths.get(fileName3);
        try {
            String content = Files.readString(filePath);
            System.out.println("The contents of the file are " + content);
            System.out.println("The hash of this file is " + tester.encryptThisString(content));


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File ind = new File("git/objects/index");
        System.out.println(tester.CheckTheString("Test", ind));
    }
}

