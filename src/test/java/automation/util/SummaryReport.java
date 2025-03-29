package automation.util;

import common.AbstractPage;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import static automation.Constants.Constants.STATUS;
import static common.AbstractPage.*;
import static common.AbstractPage.executionTime;

public class SummaryReport {
    public static String generateSummaryReport(){
        String summaryReportString="";
        String header= "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Customized Summary Report</title>\n" +
                "    <style>\n" +
                "        table, th, td {\n" +
                "            border: 1px solid black;\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            padding: 8px;\n" +
                "            border-bottom: 1px solid #ddd;\n" +
                "            word-wrap: break-word;\n" +
                "            max-width: 200px;\n" +
                "        }\n" +
                "        .expandable-content {\n" +
                "            display: none;\n" +
                "        }\n" +
                ".summary-container {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(4, auto);\n" +
                "            gap: 110px;\n" +
                "            justify-content: center;\n" +
                "            text-align: center;\n" +
                "            font-size: 18px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .summary-box {\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            color: white;\n" +
                "            width: 200px;\n" +
                "            height: 0px;\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: center;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "        .total {\n" +
                "            background-color: #2196F3;\n" +
                "        }\n" +
                "        .pass {\n" +
                "            background-color: #4CAF50;\n" +
                "        }\n" +
                "        .fail {\n" +
                "            background-color: #F44336;\n" +
                "        }\n" +
                "        .skipped {\n" +
                "            background-color: #FFC107;\n" +
                "        }"+
                "    </style>\n" +
                "</head>\n" +
                "<body>\n";
        Set<String> testCaseNameList = resultMap.keySet();
        long totalSec =0L;
        if(executionTime!=null){
            for(String testCaseName : testCaseNameList){
                if(executionTime.get(testCaseName)!=null) {
                    totalSec = totalSec + executionTime.get(testCaseName);
                }
            }

        }
        int totalPagesCount = 0;
        int passPagesCount =0;
        int failedPagesCount=0;
        int skippedPagesCount=0;

        for(String testCaseName:resultMap.keySet()){
            HashMap<String, HashMap<String,String>> userMap_tc = resultMap.get(testCaseName);
            Set<String> userPageList_tc = userMap_tc.keySet();
            int failedCountTC= 0;
            for(String pageName : userPageList_tc){
                HashMap<String,String> pageMap= userMap_tc.get(pageName);
                if(pageMap.get(STATUS).equalsIgnoreCase("failed")){
                    failedPagesCount = failedPagesCount+1;
                }
                if(pageMap.get(STATUS).equalsIgnoreCase("Passed")){
                    passPagesCount = passPagesCount+1;
                }
                if(pageMap.get(STATUS).equalsIgnoreCase("skipped")){
                    skippedPagesCount = skippedPagesCount+1;
                }
            }
        }
        totalPagesCount = passPagesCount + failedPagesCount + skippedPagesCount;
        String totalPageString = "<div class=\"summary-container\">\n" +
                "        <div class=\"summary-box pass\">✅ Passed: "+passPagesCount+"</div>\n" +
                "        <div class=\"summary-box fail\">❌ Failed: "+failedPagesCount+"</div>\n" +
                "        <div class=\"summary-box skipped\">⏭\uFE0F Skipped: "+skippedPagesCount+"</div>\n" +
                "\t\t<div class=\"summary-box total\">\uD83D\uDCCA Total: "+totalPagesCount+"</div>\n" +
                "    </div>\n" +
                "<br>";
        String executionTime =  formatDuration(totalSec);
        String heading = "<center><h1>Regression Test Summary Report</h1></center>\n" +
                "<center><h1>User Page Validation</h1></center>\n<br>\n";
        summaryReportString = summaryReportString + header;
        summaryReportString = summaryReportString +heading;
        String module = "<b>Module: </b> User Based Validation\n";
        String duration = "<b>Duration: </b>"+executionTime+"\n";
        String report_generate_date= "<b>Report Generate Date : </b>"+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss z").format(new Date()).toString();
        String br="<br>\n";
        summaryReportString = summaryReportString + module+br+duration+br+report_generate_date+br+br+totalPageString+br+br+br;

        String tableHeader = "<table>\n"+
                "<tr>\n" +
                "        <th bgcolor=\"#ffcc80\">TestCaseNumber</th>\n" +
                "        <th bgcolor=\"#ffcc80\">UserName</th>\n" +
                "        <th bgcolor=\"#ffcc80\">PageName</th>\n" +
                "        <th bgcolor=\"#ffcc80\">Status</th>\n" +
                "        <th bgcolor=\"#ffcc80\">ErrorMessage</th>\n" +
                "    </tr>\n";
        summaryReportString = summaryReportString + tableHeader;

        int i =0;
        for(String testCaseName : testCaseNameList){
            long executionTime_tc = 0L;
            if(AbstractPage.executionTime.get(testCaseName)!=null) {
                executionTime_tc =  AbstractPage.executionTime.get(testCaseName);
            }
            String finalStatus_tc = "Passed";
            HashMap<String, HashMap<String,String>> userMap_tc = resultMap.get(testCaseName);
            Set<String> userPageList_tc = userMap_tc.keySet();
            int failedCountTC= 0;
            for(String pageName : userPageList_tc){
                HashMap<String,String> pageMap= userMap_tc.get(pageName);
                if(pageMap.get(STATUS).equalsIgnoreCase("failed")){
                    finalStatus_tc = "failed";
                    failedCountTC= failedCountTC+1;
                }
            }
            String finalMessage_tc = failedCountTC+" Pages Failed Out of "+userPageList_tc.size();
            String row = "";
            if (i % 2 == 0) {
                row = "<tr bgcolor=\"#e6f2ff\" onclick=\"toggleRows('group-"+testCaseName+"')\">\n" +
                        "        <td>" + testCaseName + "</td>\n" +
                        "        <td title=\"Execution Time :"+formatDuration(executionTime_tc) +"\">" + userTestCaseMap.get(testCaseName) + "</td>\n" +
                        "        <td>pageName</td>\n" ;
                if(finalStatus_tc.equalsIgnoreCase("Passed")) {
                    row = row + "<td bgcolor=\"green\">" + finalStatus_tc + "</td>\n";
                } else if(finalStatus_tc.equalsIgnoreCase("failed")){
                    row = row + "<td bgcolor=\"red\">" + finalStatus_tc + "</td>\n";
                }
                row= row+ "<td>"+finalMessage_tc+"</td>\n" +
                        "</tr>";

                summaryReportString = summaryReportString + row;
//-----------------

                int j =0;
                for(String pageName : userPageList_tc) {
                    String pageRow = "";
                    if (j%2==0) {
                        HashMap<String, String> pageMap = userMap_tc.get(pageName);
                        String error_message = "";
                        String base64 = "";
                        if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                            base64 = ImageBase64Encoder.encodeImageToBase64(pageMap.get("fileName"));
                        }
                        if(pageMap.get("errorMessage").equalsIgnoreCase("N/A")&&base64.length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")){
                            error_message = "<a href=\"#\" class=\"imageLink\" data-image=\""+base64+"\">Screen shot</a>";
                        } else {
                            if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                                error_message = "<a href=\"#\" class=\"imageLink\" data-image=\"" + base64 + "\">Screen shot</a> " + pageMap.get("errorMessage");
                            } else {
                                error_message = pageMap.get("errorMessage");
                            }
                        }
                        pageRow = "<tr bgcolor=\"#e6e6e6\" class=\"group-"+testCaseName+" expandable-content\">\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>"+pageName+"</td>\n";
                        if (pageMap.get("status").equalsIgnoreCase("Passed")) {
                            pageRow = pageRow + "<td bgcolor=\"green\">" + pageMap.get("status") + "</td>\n";
                        } else if (pageMap.get("status").equalsIgnoreCase("failed")) {
                            pageRow = pageRow + "<td bgcolor=\"red\">" + pageMap.get("status") + "</td>\n";
                        }
                        pageRow = pageRow + "<td>" + error_message + "</td>\n" +
                                "</tr> \n";
                    } else {
                        HashMap<String, String> pageMap = userMap_tc.get(pageName);
                        String error_message = "";
                        String base64 = "";
                        if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                            base64 = ImageBase64Encoder.encodeImageToBase64(pageMap.get("fileName"));
                        }
                        if(pageMap.get("errorMessage").equalsIgnoreCase("N/A")&&base64.length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")){
                            error_message = "<a href=\"#\" class=\"imageLink\" data-image=\""+base64+"\">Screen shot</a>";
                        } else {
                            if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                                error_message = "<a href=\"#\" class=\"imageLink\" data-image=\"" + base64 + "\">Screen shot</a> " + pageMap.get("errorMessage");
                            } else {
                                error_message = pageMap.get("errorMessage");
                            }
                        }
                        pageRow = "<tr class=\"group-"+testCaseName+" expandable-content\">\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>"+pageName+"</td>\n";
                        if (pageMap.get("status").equalsIgnoreCase("Passed")) {
                            pageRow = pageRow + "<td bgcolor=\"green\">" + pageMap.get("status") + "</td>\n";
                        } else if (pageMap.get("status").equalsIgnoreCase("failed")) {
                            pageRow = pageRow + "<td bgcolor=\"red\">" + pageMap.get("status") + "</td>\n";
                        }
                        pageRow = pageRow + "<td>" + error_message+ "</td>\n" +
                                "</tr> \n";
                    }
                    summaryReportString = summaryReportString + pageRow;
                    j = j+1;
                }

            } else {
                row = "<tr onclick=\"toggleRows('group-"+testCaseName+"')\">\n" +
                        "        <td>" + testCaseName + "</td>\n" +
                        "        <td title=\"Execution Time :"+formatDuration(executionTime_tc) +"\">" + userTestCaseMap.get(testCaseName) + "</td>\n" +
                        "        <td>pageName</td>\n" ;
                if(finalStatus_tc.equalsIgnoreCase("Passed")) {
                    row = row + "<td bgcolor=\"green\">" + finalStatus_tc + "</td>\n";
                } else if(finalStatus_tc.equalsIgnoreCase("failed")){
                    row = row + "<td bgcolor=\"red\">" + finalStatus_tc + "</td>\n";
                }
                row= row+ "<td>"+finalMessage_tc+"</td>\n" +
                        "</tr>";
                summaryReportString = summaryReportString + row;

                //-----------------

                int j =0;
                for(String pageName : userPageList_tc) {
                    String pageRow = "";
                    if (j%2==0) {
                        HashMap<String, String> pageMap = userMap_tc.get(pageName);
                        String error_message = "";
                        String base64 = "";
                        if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                            base64 = ImageBase64Encoder.encodeImageToBase64(pageMap.get("fileName"));
                        }
                        if(pageMap.get("errorMessage").equalsIgnoreCase("N/A")&&base64.length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")){
                            error_message = "<a href=\"#\" class=\"imageLink\" data-image=\""+base64+"\">Screen shot</a>";
                        } else {
                            if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                                error_message = "<a href=\"#\" class=\"imageLink\" data-image=\"" + base64 + "\">Screen shot</a> " + " "+pageMap.get("errorMessage");
                            } else {
                                error_message = pageMap.get("errorMessage");
                            }
                        }
                        pageRow = "<tr bgcolor=\"#e6e6e6\" class=\"group-"+testCaseName+" expandable-content\">\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>"+pageName+"</td>\n";
                        if (pageMap.get("status").equalsIgnoreCase("Passed")) {
                            pageRow = pageRow + "<td bgcolor=\"green\">" + pageMap.get("status") + "</td>\n";
                        } else if (pageMap.get("status").equalsIgnoreCase("failed")) {
                            pageRow = pageRow + "<td bgcolor=\"red\">" + pageMap.get("status") + "</td>\n";
                        }
                        pageRow = pageRow + "<td>" + error_message + "</td>\n" +
                                "</tr> \n";
                    } else {
                        HashMap<String, String> pageMap = userMap_tc.get(pageName);
                        String error_message = "";
                        String base64 = "";
                        if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                            base64 = ImageBase64Encoder.encodeImageToBase64(pageMap.get("fileName"));
                        }
                        if(pageMap.get("errorMessage").equalsIgnoreCase("N/A")&&base64.length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")){
                            error_message = "<a href=\"#\" class=\"imageLink\" data-image=\""+base64+"\">Screen shot</a>";
                        } else {
                            if(pageMap.get("fileName")!=null&& pageMap.get("fileName").length()>0&&!pageMap.get("fileName").equalsIgnoreCase("N/A")) {
                                error_message = "<a href=\"#\" class=\"imageLink\" data-image=\"" + base64 + "\">Screen shot</a> " + pageMap.get("errorMessage");
                            } else {
                                error_message = pageMap.get("errorMessage");
                            }
                        }
                        pageRow = "<tr class=\"group-"+testCaseName+" expandable-content\">\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>" + "</td>\n" +
                                "        <td>"+pageName+"</td>\n";
                        if (pageMap.get("status").equalsIgnoreCase("Passed")) {
                            pageRow = pageRow + "<td bgcolor=\"green\">" + pageMap.get("status") + "</td>\n";
                        } else if (pageMap.get("status").equalsIgnoreCase("failed")) {
                            pageRow = pageRow + "<td bgcolor=\"red\">" + pageMap.get("status") + "</td>\n";
                        }
                        pageRow = pageRow + "<td>" + error_message + "</td>\n" +
                                "</tr> \n";
                    }
                    summaryReportString = summaryReportString + pageRow;
                    j = j+1;
                }
            }
            i = i +1;

        }
        String footer = "</table>\n<script>\n" +
                "    function toggleRows(groupName) {\n" +
                "        var rows = document.getElementsByClassName(groupName);\n" +
                "        for (var i = 0; i < rows.length; i++) {\n" +
                "            rows[i].style.display = rows[i].style.display === \"table-row\" ? \"none\" : \"table-row\";\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    var imageLinks = document.querySelectorAll('.imageLink');\n" +
                "    imageLinks.forEach(function(link) {\n" +
                "        link.addEventListener('click', function(event) {\n" +
                "            event.preventDefault();\n" +
                "            var base64ImageData = this.getAttribute('data-image');\n" +
                "            var imageWindow = window.open();\n" +
                "            imageWindow.document.write('<img src=\"data:image/png;base64,' + base64ImageData + '\">');\n" +
                "        });\n" +
                "    });\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        summaryReportString = summaryReportString + footer;
        return summaryReportString;
    }
    public static String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        StringBuilder result = new StringBuilder();
        if (hours > 0) {
            result.append(hours).append(" Hour ");
        }
//    if (minutes > 0) {
        result.append(minutes).append(" Min ");
//    }
        if (remainingSeconds > 0 || result.length() == 0) {
            result.append(remainingSeconds).append(" Sec");
        }

        return result.toString().trim();
    }
    public static void writeFiles(String summaryContent,String fileName){
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(summaryContent);
            System.out.println("HTML file '" + fileName + "' has been created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the HTML file.");
            e.printStackTrace();
        }
    }
}
