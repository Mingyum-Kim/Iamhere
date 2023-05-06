package com.personal.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

public interface MailService {
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;
    public String createKey();
    public String sendSimpleMessage(String to) throws Exception;
}
