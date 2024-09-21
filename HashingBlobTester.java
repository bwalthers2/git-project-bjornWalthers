import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class HashingBlobTester {
    private static String printFileString(String fileName) {
        try{
        String total = "";
        File file = new File(fileName);
        Scanner scannerCounter = new Scanner(file);
        while(scannerCounter.hasNext())
        {
            String next = scannerCounter.next();
            total = total + next;
        }
        System.out.println(total + "");
        scannerCounter.close();
        return total;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void main(String[] args) {
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
        String total = printFileString(fileName3);
        System.out.println(tester.encryptThisString(total));
        tester.putInObjects(fileName3, tester.encryptThisString(total));
    }
}

