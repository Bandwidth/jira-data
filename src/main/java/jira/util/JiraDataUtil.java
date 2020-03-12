package jira.util;

public class JiraDataUtil {

    private static final int ONE_DAY = 1;
    private static final int TWO_DAYS = 2;
    public static final String JIRA_LINK = "https://bandwidth-jira.atlassian.net/browse/";

    private JiraDataUtil(){}  //private constructor

    public static String getPointsEstimateQuality(float pointDelta){

        if(Math.abs(pointDelta) < ONE_DAY){
            return "green";
        }
        if(Math.abs(pointDelta) < TWO_DAYS){
            return "yellow";
        }
        else{
            return "red";
        }
    }


}
