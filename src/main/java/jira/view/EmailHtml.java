package jira.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jira.dto.Assignee;
import jira.dto.JiraInfo;
import jira.dto.SprintInfo;
import jira.dto.StatusHistory;
import jira.service.BandwidthGmail;
import jira.util.JiraDataUtil;

public class EmailHtml {


    public void emailSprintInfo(SprintInfo sprintInfo) throws GeneralSecurityException, IOException, MessagingException {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EmailHtml.class,"../../../resources/templates");
        Template template = cfg.getTemplate("report.ftl");

        Writer writer = new StringWriter();
        try {
            template.process(sprintInfo,writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        BandwidthGmail gmail = new BandwidthGmail();
        gmail.sendEmail(writer.toString());
    }

}
