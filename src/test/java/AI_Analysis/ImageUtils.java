package AI_Analysis;

import java.io.*;
import java.nio.file.*;
import java.util.Base64;

public class ImageUtils {
    public static String encodeImageToBase64(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}

