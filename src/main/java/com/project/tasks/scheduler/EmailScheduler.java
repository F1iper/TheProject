package com.project.tasks.scheduler;

import com.project.tasks.config.AdminConfig;
import com.project.tasks.domain.Mail;
import com.project.tasks.repository.TaskRepository;
import com.project.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

  private static final String SUBJECT = "Tasks: Once a day email";
  private static final String MESSAGE = "Currently in database you got: ";

  @Autowired
  private SimpleEmailService simpleEmailService;
  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private AdminConfig adminConfig;

  @Scheduled(cron = "0 0 10 * * *")
  public void sendInformationEmail() {
    simpleEmailService.send(new Mail(adminConfig.getAdminMail(),
        SUBJECT,
        chooseMessageForm()
    ));
  }

  private String chooseMessageForm() {
    long size = taskRepository.count();
    String info = MESSAGE + size;
    return info + (size > 1 ? " tasks" : " task");

  }
}
