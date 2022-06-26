package com.laptrinhb2a.springboot.mailsender.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendHtmlMail(MimeMessage message) throws MessagingException;
}
