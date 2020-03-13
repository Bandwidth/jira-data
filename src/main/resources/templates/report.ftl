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
            .whiteLink{color: white;}
            .bgcolorHeader1 {background-color: #212121; color: white; padding: 2px 9px 2px; }
            .bgcolorHeader2 {background-color:#515151; color: white;}
            .bgcolorSubHeader {background-color:white; color: #515151;}
            .bgcolorRed {background-color:#750000; color: white; font-weight: bold; padding: 9px;}
            .bgcolorYellow {background-color:#fff2cc; color: black;}
            .bgcolorGreen {background-color:#d9ead3; color: black;}
        </style>
    </head>
    <body>

        <#-- ################################################################################### -->
        <#-- SPRINT REPORT SECTION                                                                    -->
        <#-- ################################################################################### -->
        <#include "sprintSummary.ftl">

        <#-- ################################################################################### -->
        <#-- JIRA DATA SECTION                                                                          -->
        <#-- ################################################################################### -->
        <#include "jiraList.ftl">


    </body>
</html>