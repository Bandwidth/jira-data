

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