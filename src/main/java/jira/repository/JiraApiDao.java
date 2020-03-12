package jira.repository;

import jira.util.PropertyStore;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;

public class JiraApiDao {

    private static final String USER = PropertyStore.getInstance().getProperty("jira.user");
    private static final String APIKEY = PropertyStore.getInstance().getProperty("jira.apiKey");

    private static final String URL_ROOT = "https://bandwidth-jira.atlassian.net";
    private static final String ACCEPT = "Accept";
    private static final String APP_JSON = "application/json";



    public JSONArray getSprintJiraJsonArray(String jql){

        HttpResponse<JsonNode> response = Unirest.get(URL_ROOT + "/rest/api/2/search")
                                                 .basicAuth(USER, APIKEY)
                                                 .header(ACCEPT, APP_JSON)
                                                 .queryString("jql", jql)
                                                 .asJson();

        return response.getBody().getObject().getJSONArray("issues");
    }

    public String getSprintJiraJsonString(String jql){

        HttpResponse<JsonNode> response = Unirest.get(URL_ROOT + "/rest/api/2/search")
                                                 .basicAuth(USER, APIKEY)
                                                 .header(ACCEPT, APP_JSON)
                                                 .queryString("expand", "names,changelog")
                                                 .queryString("fields", "*all")
                                                 .queryString("jql", jql)
                                                 .asJson();

        return response.getBody().getObject().getJSONArray("issues").toString(3);
    }

    public JSONArray getIssueWorkLogs(String jiraKey){

        HttpResponse<JsonNode> response = Unirest.get(URL_ROOT + "/rest/api/2/issue/" + jiraKey +"/worklog")
                                                 .basicAuth(USER, APIKEY)
                                                 .header(ACCEPT, APP_JSON)
                                                 .asJson();

        return response.getBody().getObject().getJSONArray("worklogs");
    }




}
