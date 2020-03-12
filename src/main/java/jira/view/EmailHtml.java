package jira.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jira.dto.Assignee;
import jira.dto.JiraInfo;
import jira.dto.SprintInfo;
import jira.dto.StatusHistory;
import jira.service.BandwidthGmail;
import jira.util.JiraDataUtil;

public class EmailHtml {


    public void emailSprintInfo(SprintInfo sprintInfo) throws GeneralSecurityException, IOException, MessagingException {

        BandwidthGmail gmail = new BandwidthGmail();
        String emailBody = buildEmailBody(sprintInfo);
        gmail.sendEmail(emailBody);

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EmailHtml.class,"../../../resources/templates");

        Template template = cfg.getTemplate("report.ftl");
        Writer writer = new StringWriter();
        try {
            template.process(sprintInfo,writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        gmail.sendEmail(writer.toString());

    }

    public String buildEmailBody(SprintInfo sprintInfo){

        String text = "";

        text += "<!doctype html>";
        text += "<html lang=\"en\">";
        text += "<head>";
        text += "<meta charset=\"utf-8\">";
        text += "<title>Sprint Health Analytics</title>";
        text += "<meta name=\"description\" content=\"The HTML5 Herald\">";
        text += "<meta name=\"author\" content=\"Gonzo\">";

        text += "<style>";
        text += "body {font-family: Sans-Serif;} ";
        text += "td {vertical-align:top;} ";
        text += ".colTwo {text-align:right;} ";
        text += ".colOne {width:30px;} ";
        text += ".colOne2 {width:60px;} ";
        text += ".bottomBorder {border-bottom: 1px solid #999;} ";
        text += ".bgcolorHeader {background-color:#515151; color: white;} ";
        text += ".bgcolorSubHeader {background-color:white; color: #515151;} ";
        text += ".bgcolorRed {background-color:#750000; color: white; font-weight: bold; padding: 9px;} ";
        text += ".bgcolorYellow {background-color:#fff2cc; color: black;} ";
        text += ".bgcolorGreen {background-color:#d9ead3; color: black;} ";

        text += "</style>";

        text += "</head>";
        text += "<body>";

        text += buildSprintInfo(sprintInfo);

        text += "</body>";
        text += "</html>";

        return text;
    }


    private String buildSprintInfo(SprintInfo sprintInfo){

        String text = "";

        text += "<h1>Sprint Report</h1>";
        text += "<hr>";

        text += "<table border=\"0\">";

        text += "<tr>";
        text += "<td colspan=\"4\"  class=\"bgcolorSubHeader\"><h3>Sprint Info :</h3></td>";
        text += "</tr>";

        text += "<tr>";
        text += "<td class=\"colOne\"></td>";
        text += "<td class=\"colTwo\">Sprint Name :</td>";
        text += "<td colspan=\"2\" >" + sprintInfo.getSprintName() + "</td>";
        text += "</tr>";

        text += buildSprintAssigneeSecondsLoggedMap(sprintInfo.getAssigneeSecondsLoggedMap());

        text += "</table>";




        text += "<br><hr>";
        text += "<h1>Jira Data</h1>";
        text += buildJiraInfoList(sprintInfo.getJiraInfoList());



        return text;
    }



    private String buildSprintAssigneeSecondsLoggedMap(Map<String, Integer> assigneeSecondsLoggedMap){

        String text = "";

        text += "<tr>";
        text += "<td colspan=\"4\"  class=\"bgcolorSubHeader\"><h3>Dev Hours :</h3></td>";

        text += "</tr>";

        for (Map.Entry<String,Integer> entry :assigneeSecondsLoggedMap.entrySet()){
            BigDecimal hours = new BigDecimal(entry.getValue().floatValue() / (60*60));
            BigDecimal percentLogged = hours.divide(new BigDecimal(80)).multiply(new BigDecimal(100));

            text += "<tr>";
            text += "<td></td>";
            text += "<td class=\"colTwo\">" + entry.getKey() + "&nbsp;:" + "</td>";
            text += "<td>" + hours.setScale(2, RoundingMode.HALF_EVEN) + "</td>";

            text += "<td>= "+ percentLogged.setScale(1, RoundingMode.HALF_EVEN) + "% time logged</td>";
            text += "</tr>";



        }
        return text;
    }


    private String buildJiraInfoList(List<JiraInfo> jiraInfoList){

        String text = "";
        for(JiraInfo jiraInfo : jiraInfoList){
            text += "<table border=\"0\">";
            text += buildJiraInfo(jiraInfo);
            text += buildAssigneeTimeLoggedMap(jiraInfo.getAssigneeTimeLoggedMap());
            text += buildStatusHistoryList(jiraInfo.getStatusHistoryList());
            text += "</table>";

            text += "<br><br><hr><br><br>";

        }
        return text;
    }

    private String buildJiraInfo(JiraInfo jiraInfo){

        String text = "";

        text += "<tr><td colspan=\"3\" class=\"bgcolorHeader\"><h3>&nbsp;&nbsp;JIRA : "+ jiraInfo.getJiraKey()+"</h3></td></tr>";
        text += "<tr><td colspan=\"3\" class=\"bgcolorSubHeader\"><h3>&nbsp;&nbsp;JIRA DATA :</h3></td></tr>";
        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";

        // -----------------------------------------------------------------------------------------
        // Jira - General Info
        // -----------------------------------------------------------------------------------------


        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Jira :" + "</td>";
        text += "<td>" + JiraDataUtil.JIRA_LINK + jiraInfo.getJiraKey() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Summary :" + "</td>";
        text += "<td>" + jiraInfo.getSummary() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Epic Link :" + "</td>";

        if(jiraInfo.getEpicLink().equals("EPIC MISSING/Please Link an Epic") ){
            text += "<td class=\"bgcolorRed\">";
        }else {
            text += "<td>";
        }


        text += jiraInfo.getEpicLink() + "</td></tr>";

        text += "<tr><td></td><td  colspan=\"2\" ><hr width=85%></td></tr>";


        // -----------------------------------------------------------------------------------------
        // Jira - Assignee List
        // -----------------------------------------------------------------------------------------

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Implementer :" + "</td>";
        text += "<td>" + jiraInfo.getCoder().getPerson().getAssigneeName() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Tester 1 :" + "</td>";
        text += "<td>" + jiraInfo.getTester01().getPerson().getAssigneeName() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Tester 2 :" + "</td>";
        text += "<td>" + jiraInfo.getTester02().getPerson().getAssigneeName() + "</td></tr>";

        text += "<tr><td></td><td  colspan=\"2\" ><hr width=85%></td></tr>";

        // -----------------------------------------------------------------------------------------
        // Jira - Pointing
        // -----------------------------------------------------------------------------------------





        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Points Estimate :" + "</td>";


        if(jiraInfo.getPointsEstimate() == 0){
            text += "<td class=\"bgcolorRed\">";
        }
        else {
            text += "<td>";
        }



        text += jiraInfo.getPointsEstimate() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Points Actual :" + "</td>";

        if(jiraInfo.getPointsActual() == 0){
            text += "<td class=\"bgcolorRed\">";
        }
        else {
            text += "<td>";
        }

        text += jiraInfo.getPointsActual() + "</td></tr>";

        text += "<tr><td></td><td  colspan=\"2\" ><hr width=85%></td></tr>";

        // -----------------------------------------------------------------------------------------
        // Jira - Point Quality
        // -----------------------------------------------------------------------------------------

        String qColor = "bgcolorGreen";
        switch (jiraInfo.getPointsEstimateQuality()){
            case "red":
                qColor = "\"bgcolorRed\"";
                break;
            case "yellow":
                qColor = "\"bgcolorYellow\"";
                break;
            case "green":
                qColor = "\"bgcolorGreen\"";
                break;
        }



        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Points Delta :" + "</td>";
        text += "<td class=" + qColor + ">" + jiraInfo.getPointsEstimateDelta() + "</td></tr>";

        text += "<tr><td class=\"colOne\"></td><td class=\"colTwo\">" + "Point Quality :" + "</td>";
        text += "<td class=" + qColor +">" + jiraInfo.getPointsEstimateQuality() + "</td></tr>";

        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";
        return text;
    }



    private String buildAssigneeTimeLoggedMap(Map<String, Assignee> assigneeTimeLoggedMap){

        String text = "";
        text += "<tr><td colspan=\"3\" class=\"bgcolorSubHeader\"><h3>&nbsp;&nbsp;HOURS LOGGED :</h3></td></tr>";
        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";

        int i = 0;
        for(String assigneeKey : assigneeTimeLoggedMap.keySet()){

            if(i++ != 0){
                text += "<tr><td></td><td  colspan=\"2\" ><hr width=85%></td></tr>";
            }

            float timeLogged = ((float)assigneeTimeLoggedMap.get(assigneeKey).getSecondsLogged()) / (60*60);

            text += "<tr><td class=\"colOne2\"></td><td class=\"colTwo\">" + "Logger :" + "</td>";

            if(timeLogged == 0){
                text += "<td class=\"bgcolorRed\">";
            }
            else {
                text += "<td>";
            }
            text += assigneeKey + "</td></tr>";

            text += "<tr><td class=\"colOne2\"></td><td class=\"colTwo\">" + "Time :" + "</td>";

            if(timeLogged == 0){
                text += "<td class=\"bgcolorRed\">";
            }
            else {
                text += "<td>";
            }
            text += timeLogged + " Hours" + "</td></tr>";
        }

        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";
        return text;
    }



    private String buildStatusHistoryList(List<StatusHistory> statusHistoryList){

        String text = "";
        text += "<tr><td colspan=\"3\" class=\"bgcolorSubHeader\"><h3>&nbsp;&nbsp;STATUS HISTORY :</h3></td></tr>";
        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";


        int count = 1;
        for(StatusHistory statusHistory : statusHistoryList){

            String countString = Integer.toString(count++);
            if(statusHistory.getToStatus().equals("In Progress")){
                countString = "***  " + countString;
            }

            text += "<tr><td class=\"colOne2\"></td>";
            text += "<td class=\"colTwo\">" + countString + " :</td>";
            text += "<td>" + statusHistory.getFromStatus() + " to " + statusHistory.getToStatus() + "</td></tr>";

            text += "<tr><td class=\"colOne2\"></td>";
            text += "<td class=\"colTwo\"></td>";
            text += "<td>" + statusHistory.getCreated() + "</td></tr>";
        }

        text += "<tr><td colspan=\"3\">&nbsp;</td></tr>";
        return text;
    }




}
