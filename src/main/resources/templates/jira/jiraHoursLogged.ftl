<tr>
    <td></td>
    <td colspan="3" class="bgcolorSubHeader"><h3>&nbsp;&nbsp;HOURS LOGGED :</h3></td>
</tr>

<tr>
    <td></td>
    <td colspan="3">&nbsp;</td>
</tr>


<#assign count = 0>
<#list jiraInfo.assigneeTimeLoggedMap?keys as key>


    <#assign count = count + 1>
    <#assign hBar = "">
    <#if count != 1>
        <#assign hBar>
            <tr>
                <td>
                </td>
                <td></td>
                <td  colspan="2" ><hr width=85%></td>
            </tr>
        </#assign>
    </#if>
    ${hBar}

    <tr>
    <td></td>
    <td></td>
    <td class="colTwo">Logger :</td>
    <td>${key}</td>
    </tr>



    <#assign hours = jiraInfo.assigneeTimeLoggedMap[key].secondsLogged / (60*60)>

    <#if hours == 0>
        <#assign qColor = "bgcolorRed">
    <#else>
        <#assign qColor = "">
    </#if>

    <tr>
        <td></td>
        <td></td>
        <td class="colTwo">Time :</td>
        <td class="${qColor}">${hours} Hours</td>
    </tr>

</#list>

<tr>
    <td></td>
    <td colspan="3">&nbsp;</td>
</tr>
