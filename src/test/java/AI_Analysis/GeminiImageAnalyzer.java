package AI_Analysis;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class GeminiImageAnalyzer {

    // Replace this with your API key from https://aistudio.google.com/app/apikey
    private static final String API_KEY = "AIzaSyDZJ1xWz4UvLLfUG_A57qIEP2_Y1r6SZGE";

    // Using Gemini 2.0 Flash Experimental
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent?key=" + API_KEY;

    public static void main(String[] args) throws IOException {
        String imagePath = "C:\\GIT\\temp.png"; // Your screenshot file
        String base64Image = encodeImageToBase64(imagePath);

        OkHttpClient client = new OkHttpClient();

        // Construct request body
        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();

        JSONArray parts = new JSONArray();

        // Part 1: Text prompt
        parts.put(new JSONObject().put("text", "Analyze this webpage screenshot. Summarize the key information shown."));

        // Part 2: Image
        JSONObject imageData = new JSONObject()
                .put("mimeType", "image/png")
                .put("data", base64Image);
        parts.put(new JSONObject().put("inlineData", imageData));

        JSONObject content = new JSONObject().put("parts", parts);
        contents.put(content);

        requestBody.put("contents", contents);

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(GEMINI_URL)
                .post(body)
                .build();

        // Send request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response.code());
                System.err.println("Response: " + response.body().string());
                return;
            }

            // Get response
            String responseBody = response.body().string();
            System.out.println("Gemini Response:\n" + extractTextFromResponse(responseBody));
        }
    }

    // Encode image to base64
    private static String encodeImageToBase64(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    // Extract and return AI's text response
    private static String extractTextFromResponse(String json) {
        JSONObject obj = new JSONObject(json);
        JSONArray candidates = obj.optJSONArray("candidates");
        if (candidates != null && !candidates.isEmpty()) {
            JSONObject content = candidates.getJSONObject(0).optJSONObject("content");
            if (content != null) {
                JSONArray parts = content.optJSONArray("parts");
                if (parts != null && !parts.isEmpty()) {
                    return parts.getJSONObject(0).optString("text", "No text found");
                }
            }
        }
        return "No valid response found.";
    }
}
