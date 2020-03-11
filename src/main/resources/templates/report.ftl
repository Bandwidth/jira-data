<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Sprint Analytics</title>
        <meta name="description" content="Sprint Report">
        <meta name="author" content="Gonzo">

        <style>
            /*table {width: 100%}*/
            body {font-family: sans-serif;}
            td {vertical-align:top;}
            .colZero {width:18px;}
            .colOne {width:45px;}
            .colTwo {text-align:right; white-space:nowrap; width:1px;}
            .bottomBorder {border-bottom: 1px solid #999;}
            .bgcolorHeader1 {background-color: #212121; color: white; padding: 2px 9px 2px; }
            .bgcolorHeader2 {background-color:#515151; color: white;}
            .bgcolorSubHeader {background-color:white; color: #515151;}
            .bgcolorRed {background-color:#750000; color: white; font-weight: bold; padding: 9px;}
            .bgcolorYellow {background-color:#fff2cc; color: black;}
            .bgcolorGreen {background-color:#d9ead3; color: black;}
        </style>
    </head>
    <body>
        *********

        <#-- ################################################################################### -->
        <#-- SPRINT SUMMARY                                                                      -->
        <#-- ################################################################################### -->
        <div class="bgcolorHeader1">
            <h1>SPRINT REPORT :</h1>
        </div>

        <table border="0">
            <tr>
                <td class="colZero"></td>
                <td colspan="4"  class="bgcolorSubHeader"><h3>Sprint Info :</h3></td>
            </tr>
            <tr>
                <td></td>
                <td class="colOne"></td>
                <td class="colTwo">Sprint Name :</td>
                <td colspan="2" > ${sprintName} </td>
            </tr>
            <tr>
                <td></td>
                <td colspan="4"  class="bgcolorSubHeader"><h3>Dev Hours :</h3></td>
            </tr>

            <#list assigneeSecondsLoggedMap?keys as key>
                <#assign hours = assigneeSecondsLoggedMap[key] / (60*60)>
                <#assign percentLogged = (hours / 80) * 100 >
                <tr>
                    <td  colspan="2"></td>
                    <td class="colTwo">${key}&nbsp;:</td>
                    <td class="colTwo">${hours}</td>
                    <td>= ${percentLogged}% time logged</td>
                </tr>
            </#list>

        </table>
        <br><br>



        <#-- ################################################################################### -->
        <#-- JIRA DATA                                                                           -->
        <#-- ################################################################################### -->

        <div class="bgcolorHeader1">
            <h1>JIRA DATA :</h1>
        </div>
        <br><br>

        <table border="0">

        <#list jiraInfoList as jiraInfo>




            <tr>
                <td class="colZero"></td>
                <td colspan="3" class="bgcolorHeader2"><h3>&nbsp;&nbsp;JIRA : ${jiraInfo.jiraKey}</h3></td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3" class="bgcolorSubHeader"><h3>&nbsp;&nbsp;JIRA DATA :</h3></td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3">&nbsp;</td>
            </tr>


        <#-- ########################################################################### -->
        <#-- JIRA - GENERAL INFO                                                         -->
        <#-- ########################################################################### -->

            <tr>
                <td></td>
                <td class="colOne"></td>
                <td class="colTwo">Jira :</td>
                <td>${jiraInfo.jiraKey}</td>
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

        <#-- ########################################################################### -->
        <#-- JIRA - ASSIGNEE LIST                                                        -->
        <#-- ########################################################################### -->

            <tr>
                <td></td>
                <td></td>
                <td class="colTwo">Implementer :</td>
                <td>${jiraInfo.coder.person.assigneeName}</td>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td class="colTwo">Tester 1 :</td>
                <td>${jiraInfo.tester01.person.assigneeName}</td>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td class="colTwo">Tester 2 :</td>
                <td>${jiraInfo.tester02.person.assigneeName}</td>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td  colspan="2" ><hr width=85%></td>
            </tr>

        <#-- ########################################################################### -->
        <#-- JIRA - POINTING                                                             -->
        <#-- ########################################################################### -->

            <tr>
                <td></td>
                <td></td>
                <td class="colTwo">Points Estimate :</td>
                <td>${jiraInfo.pointsEstimate}</td>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td class="colTwo">Points Actual :</td>
                <td>${jiraInfo.pointsActual}</td>
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

        <#-- ################################################################################### -->
        <#-- HOURS LOGGED                                                                        -->
        <#-- ################################################################################### -->

            <tr>
                <td></td>
                <td colspan="3" class="bgcolorSubHeader"><h3>&nbsp;&nbsp;HOURS LOGGED :</h3></td>
            </tr>

            <tr>
                <td></td>
                <td colspan="3">&nbsp;</td>
            </tr>

            <#list jiraInfo.assigneeTimeLoggedMap?keys as key>
                <tr>
                    <td></td>
                    <td></td>
                    <td  colspan="2" ><hr width=85%></td>
                </tr>

                <tr>
                    <td></td>
                    <td></td>
                    <td class="colTwo">Logger :</td>
                    <td>${key}</td>
                </tr>

                <tr>
                    <td></td>
                    <td></td>
                    <td class="colTwo">Time :</td>
                    <#assign hours = jiraInfo.assigneeTimeLoggedMap[key].secondsLogged / (60*60)>
                    <td>${hours} Hours</td>
                </tr>

            </#list>

            <tr>
                <td></td>
                <td colspan="3">&nbsp;</td>
            </tr>


        <#-- ################################################################################### -->
        <#-- STATUS HISTORY                                                                      -->
        <#-- ################################################################################### -->

            <tr>
                <td></td>
                <td colspan="3" class="bgcolorSubHeader"><h3>&nbsp;&nbsp;STATUS HISTORY :</h3></td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3">&nbsp;</td>
            </tr>

            <#list jiraInfo.statusHistoryList as statusHistory>
                <tr>
                    <td></td>
                    <td></td>
                    <td class="colTwo"> countString :</td>
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
                <td colspan="3"><br><br><br><br></td>
            </tr>

        </#list>

            <tr>
                <td></td>
                <td colspan="3">
                    <br><br><hr><br><br>
                </td>
            </tr>

        </table>


    </body>
</html>