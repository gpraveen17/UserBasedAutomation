package automation.util;

import common.AbstractPage;
import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static common.AbstractPage.getUserDirectory;
import static common.AbstractPage.sp;

public class Util {

public static String getValueFromResource(String key,FileType fileType){
   String value ="";
   Properties properties = new Properties();
   try{
      //System.out.println("User_dir : "+System.getProperty("user.dir"));
      String filePath = getUserDirectory()+sp+"src"+sp+"test"+sp+"resources"+fileType.gtFileName();
      FileInputStream fileInputStream = new FileInputStream(filePath);
      properties.load(fileInputStream);
      value = properties.get(key).toString();
   }catch (Exception e){

   }
   return value;
}
public static boolean cleanUpFiles(){
boolean status = false;
String folderPath = getUserDirectory()+sp+"Screenshot";
         // Create File object for the directory
         File folder = new File(folderPath);
         if (folder.exists() && folder.isDirectory()) {
            // Get all files in the directory
            File[] files = folder.listFiles();

            if (files != null) {
               for (File file : files) {
                  // Delete only files (not directories)
                  if (file.isFile()) {
                     if (file.delete()) {
                        //System.out.println("Deleted: " + file.getName());
                     } else {
                        System.out.println("Failed to delete: " + file.getName());
                     }
                  }
               }
               status = true;
            } else {
               System.out.println("The folder is empty.");
            }
         } else {
            System.out.println("Folder does not exist or is not a directory.");
         }
   return status;
      }

}
