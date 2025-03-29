package automation.util;

import java.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageBase64Encoder {
    public static String encodeImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream imageStream = new FileInputStream(file);
            byte[] imageBytes = new byte[(int) file.length()];
            imageStream.read(imageBytes);
            imageStream.close();

            // Encode to Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String fileName = "C:\\Software\\Projects\\BDD_Demo\\Screenshot\\20250307_165404.png";
        String base64String = encodeImageToBase64(fileName);
        System.out.println("base64String : "+base64String);
    }
}
