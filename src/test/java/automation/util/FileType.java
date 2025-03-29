package automation.util;

public enum FileType {
    ENV_FILE("/properties/env.properties"),
    CAPABILITIES("/properties/capabilities.properties"),
    PROD("/properties/prod.properties"),
    QA("/properties/qa.properties");
private final String fileName;
    public String gtFileName(){return fileName;}
    FileType(String fileName) {this.fileName=fileName;}
}
