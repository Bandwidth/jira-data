
<div class="bgcolorHeader1">
    <h1>JIRA DATA :</h1>
</div>
<br><br>

<table border="0">

    <#list jiraInfoList as jiraInfo>
        <#include "jira/jiraHeader.ftl">
        <#include "jira/jiraSummary.ftl">
        <#include "jira/jiraAssignees.ftl">
        <#include "jira/jiraPoints.ftl">
        <#include "jira/jiraHoursLogged.ftl">
        <#include "jira/jiraStatusHistory.ftl">
    </#list>

    <tr>
        <td></td>
        <td colspan="3">
            <br><br><hr><br><br>
        </td>
    </tr>

</table>
