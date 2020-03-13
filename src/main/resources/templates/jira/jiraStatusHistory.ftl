<tr>
    <td></td>
    <td colspan="3" class="bgcolorSubHeader"><h3>&nbsp;&nbsp;STATUS HISTORY :</h3></td>
</tr>

<tr>
    <td></td>
    <td colspan="3">&nbsp;</td>
</tr>

<#assign count = 0>
<#list jiraInfo.statusHistoryList as statusHistory>

    <#assign count = count + 1>
    <#assign countString = "">

    <#if statusHistory.toStatus == "In Progress">
        <#assign countString = "*** ">
    </#if>

    <tr>
        <td></td>
        <td></td>
        <td class="colTwo"> ${countString}${count} :</td>
        <td>${statusHistory.fromStatus} to ${statusHistory.toStatus}</td>
    </tr>

    <tr>
        <td></td>
        <td></td>
        <td class="colTwo"></td>
        <td>${statusHistory.created}</td>
    </tr>
</#list>

<tr>
    <td></td>
    <td colspan="3"><br><br><br></td>
</tr>