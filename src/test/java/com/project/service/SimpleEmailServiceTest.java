package com.project.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.tasks.domain.Mail;
import com.project.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

  @InjectMocks
  private SimpleEmailService simpleEmailService;

  @Mock
  private JavaMailSender javaMailSender;

  @Test
  public void shouldSendMail() {
    //Given
    Mail mail = new Mail("test@test.com",
        "Test",
        "test");

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(mail.getMailTo());
    mailMessage.setSubject(mail.getSubject());
    mailMessage.setText(mail.getMessage());

    //When
    simpleEmailService.send(mail);

    //Then
    verify(javaMailSender, times(1)).send(mailMessage);
  }

}
