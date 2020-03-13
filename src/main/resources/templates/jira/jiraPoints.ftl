<#-- ########################################################################### -->
<#-- JIRA - POINTs                                                            -->
<#-- ########################################################################### -->

<#if jiraInfo.pointsEstimate == 0>
    <#assign qColor = "bgcolorRed">
<#else>
    <#assign qColor = "">
</#if>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Point Estimate :</td>
    <td class="${qColor}">${jiraInfo.pointsEstimate}</td>
</tr>

<#if jiraInfo.pointsActual == 0>
    <#assign qColor = "bgcolorRed">
<#else>
    <#assign qColor = "">
</#if>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Points Actual :</td>
    <td class="${qColor}">${jiraInfo.pointsActual}</td>
</tr>

<tr>
    <td></td>
    <td></td>
    <td  colspan="2" ><hr width=85%></td>
</tr>

<#-- ########################################################################### -->
<#-- JIRA - POINT QUALITY                                                             -->
<#-- ########################################################################### -->


<#switch jiraInfo.pointsEstimateQuality>
    <#case "red">
        <#assign qColor = "bgcolorRed">
        <#break>
    <#case "yellow">
        <#assign qColor = "bgcolorYellow">
        <#break>
    <#default>
        <#assign qColor = "bgcolorGreen">
</#switch>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Points Delta :</td>
    <td class="${qColor}">${jiraInfo.pointsEstimateDelta}</td>
</tr>

<tr>
    <td></td>
    <td></td>
    <td class="colTwo">Point Quality :</td>
    <td class="${qColor}">${jiraInfo.pointsEstimateQuality}</td>
</tr>

<tr>
    <td></td>
    <td colspan="3">&nbsp;</td>
</tr>