package jira.poc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import jira.repository.JiraApiDao;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;


public class ParseData {

    private final static int SECONDS_IN_DAY = 60 * 60 * 8;
    private final static int SECONDS_IN_HOUR = 60 * 60;



    public void generateSprintReport(String sprintName){

        JiraApiDao jiraData = new JiraApiDao();
        JSONArray jiraJsonArray = jiraData.getSprintJiraJsonArray(sprintName);
        Map<String, Integer> sprintLoggedTimePerDevMap = new HashMap<>();


        //Loop Through each Jira
        for(Object jiraJSONObject : jiraJsonArray){

            Map jiraFieldDataMap = getJiraFieldDataMap((JSONObject) jiraJSONObject);


            JSONArray workLogJsonArray =jiraData.getIssueWorkLogs(jiraFieldDataMap.get("jiraKey").toString());
            Map<String, Integer> timeAuthorSecondsLoggedMap = getTimeAuthorSecondsLoggedMap(workLogJsonArray );

            printFields(jiraFieldDataMap);
//            printTimeLogged(timeAuthorSecondsLoggedMap);

            for(String key : timeAuthorSecondsLoggedMap.keySet()){

                if(sprintLoggedTimePerDevMap.containsKey(key)){
                    sprintLoggedTimePerDevMap.put(key, sprintLoggedTimePerDevMap.get(key) + timeAuthorSecondsLoggedMap.get(key));
                }else {
                    sprintLoggedTimePerDevMap.put(key, timeAuthorSecondsLoggedMap.get(key));
                }
            }

        }


        System.out.println(" ++++++++++++++++++++++++++++++++");
        System.out.println(" ++++++++++++++++++++++++++++++++");
        System.out.println(" ++++++++++++++++++++++++++++++++");

        printTimeLogged(sprintLoggedTimePerDevMap);


    }


    private Map<String, Integer> getTimeAuthorSecondsLoggedMap(JSONArray workLogJsonArray ){

        Map<String, Integer> timeAuthorSecondsLoggedMap = new HashMap<>();
        for(Object worklogObject : workLogJsonArray){

            String timeAuthor = (String)((JSONObject) worklogObject).getJSONObject("author").get("key");
            Integer timeSecondsLogged = (Integer) ((JSONObject) worklogObject).get("timeSpentSeconds");

            if(timeAuthorSecondsLoggedMap.containsKey(timeAuthor)){
                timeAuthorSecondsLoggedMap.put(timeAuthor,timeAuthorSecondsLoggedMap.get(timeAuthor) + timeSecondsLogged);
            }else {
                timeAuthorSecondsLoggedMap.put(timeAuthor,timeSecondsLogged);
            }
        }
        return timeAuthorSecondsLoggedMap;
    }

    private void printTimeLogged(Map<String, Integer> timeAuthorSecondsLoggedMap){

        for(String key : timeAuthorSecondsLoggedMap.keySet()){

            System.out.println("\t\t\t =======================");
            System.out.println("\t\t\t  Dev     : " + key);
//            System.out.println("\t\t\t  Seconds : " + timeAuthorSecondsLoggedMap.get(key));

            Double hours = timeAuthorSecondsLoggedMap.get(key).doubleValue() / SECONDS_IN_HOUR;
            System.out.println("\t\t\t  Hours   : " + BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP).toString());
        }
    }

    private void printFields(Map issueMap){

        System.out.println("_______________________________________________\n");
        System.out.println("Jira     : " + issueMap.get("jiraKey"));
        System.out.println("_______________________________________________\n");
        System.out.println("\t Jira     : " + issueMap.get("jiraKey"));
        System.out.println("\t Summary  : " + issueMap.get("summary"));
        System.out.println("\t Assignee : " + issueMap.get("assignee"));
        System.out.println("\t Tester 1 : " + issueMap.get("tester01"));
        System.out.println("\t Tester 2 : " + issueMap.get("tester02"));
        System.out.println("\t ----------------------");
        System.out.println("\t Estimate : " + issueMap.get("storyPoints"));
        System.out.println("\t Actual   : " + issueMap.get("actualPoints"));
        System.out.println("\t ----------------------");
        System.out.println("\t Delta    : " + issueMap.get("delta"));

        System.out.println("");
    }

    private Map getJiraFieldDataMap(JSONObject jsonObject){

        Map<String,String> fieldMap = new HashMap();
        JSONObject fields = ((JSONObject) jsonObject).getJSONObject("fields");

        fieldMap.put("jiraKey", (String) jsonObject.get("key"));
        fieldMap.put("summary", (String) fields.get("summary"));
        fieldMap.put("assignee", getFieldDataKey(fields, "assignee"));
        fieldMap.put("tester01", getFieldDataKey(fields, "customfield_11700"));
        fieldMap.put("tester02", getFieldDataKey(fields, "customfield_12837"));

        Double storyPoints = (Double) fields.get("customfield_10002");
        if (storyPoints == null){
            storyPoints = Double.valueOf(0);
        }
        fieldMap.put("storyPoints", storyPoints.toString());

        Integer seconds = (Integer) fields.get("timespent");
        Double actualPoints = (seconds != null) ?  seconds.doubleValue()/ SECONDS_IN_DAY : Double.valueOf(0);
        fieldMap.put("actualPoints", BigDecimal.valueOf(actualPoints).setScale(2, RoundingMode.HALF_UP).toString());

        Double delta = storyPoints - actualPoints;
        fieldMap.put("delta", BigDecimal.valueOf(delta).setScale(2, RoundingMode.HALF_UP).toString());

        return fieldMap;
    }

    private  Integer getFieldDataInteger(JSONObject fieldsObj, String fieldName){

        Integer integerValue = (Integer) fieldsObj.get(fieldName);
        if (integerValue == null){
            integerValue = Integer.valueOf(0);
        }
        return integerValue;
    }

    private  String getFieldDataKey(JSONObject fieldsObj, String fieldName){

        String fieldData = "EMPTY";
        if(fieldsObj.get(fieldName) != null){
            fieldData = (String)((JSONObject)fieldsObj.get(fieldName)).get("key");
        }
        return fieldData;
    }

}
