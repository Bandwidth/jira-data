<tr>
    <td></td>
    <td class="colOne"></td>
    <td class="colTwo">Jira :</td>
    <td>${JIRA_LINK}${jiraInfo.jiraKey}</td>
</tr>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Summary :</td>
    <td>${jiraInfo.summary}</td>
</tr>


<#if jiraInfo.epicLink == "EPIC MISSING/Please Link an Epic">
    <#assign qColor = "bgcolorRed">
<#else>
    <#assign qColor = "">
</#if>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Epic Link :</td>
    <td class="${qColor}"> ${jiraInfo.epicLink}</td>
</tr>

<tr>
    <td></td>
    <td></td>
    <td  colspan="2" ><hr width=85%></td>
</tr>