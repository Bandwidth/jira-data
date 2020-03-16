package jira.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jira.dto.SprintInfo;
import jira.service.BandwidthGmail;

public class EmailHtml {

    private static Logger log = LogManager.getRootLogger();

    public void emailSprintInfo(SprintInfo sprintInfo) {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EmailHtml.class,"../../../resources/templates");

        Writer writer = new StringWriter();
        try {
            Template template = cfg.getTemplate("report.ftl");
            template.process(sprintInfo,writer);

        } catch (TemplateException | IOException e) {
            log.error("Email Template Composition Error: ",e);
        }

        BandwidthGmail gmail = new BandwidthGmail();
        try {
            gmail.sendEmail(writer.toString());
        } catch (IOException | GeneralSecurityException | MessagingException e) {
            log.error("GMail Sending Error: ",e);
        }
    }

}
