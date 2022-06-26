package com.laptrinhb2a.springboot.mailsender.service.impl;

import com.laptrinhb2a.springboot.mailsender.controller.UploadFileController;
import com.laptrinhb2a.springboot.mailsender.dto.ResDTO;
import com.laptrinhb2a.springboot.mailsender.utils.Const;
import com.laptrinhb2a.springboot.mailsender.dto.DataMailDTO;
import com.laptrinhb2a.springboot.mailsender.dto.ClientSdi;
import com.laptrinhb2a.springboot.mailsender.service.MailService;
import com.laptrinhb2a.springboot.mailsender.service.ClientService;
import com.laptrinhb2a.springboot.mailsender.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public ResDTO create(ClientSdi sdi) {

        ResDTO res = new ResDTO();

        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sdi.getEmailTo());
            dataMail.setSubject(sdi.getSubject());

            Map<String, Object> props = new HashMap<>();
            props.put("name", sdi.getName());
            props.put("username", sdi.getUsername());
            props.put("password", DataUtils.generateTempPwd(6));
            props.put("content", sdi.getContent());
            props.put("url", sdi.getUrl());
            dataMail.setProps(props);

            // Sinh ra mail template
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            Context context = new Context();
            context.setVariables(dataMail.getProps());
            String html = chooseTypeTemplate(sdi.getType(), context);
            helper.setTo(dataMail.getTo());
            helper.setSubject(dataMail.getSubject());
            helper.setText(html, true);

            List<String> listUrl = Arrays.asList(sdi.getUrl());

            listUrl.forEach(url -> {
                //Thêm file trong khi gửi mail
                String pathFile = UploadFileController.CURRENT_FOLDER.toString().concat("\\static\\").concat(url);
                String imageName = url.substring(7);
                FileSystemResource file = new FileSystemResource(new File(pathFile));
                try {
                    helper.addAttachment(imageName, file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            //Thực hiện gửi mail cho user
            mailService.sendHtmlMail(message);
            res.setRes(true);
            res.setHtml(html);
            return res;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        res.setRes(false);
        res.setHtml("Error");
        return res;
    }

    @Override
    public List<ResDTO> search(ClientSdi sdi)  {
        List<ResDTO> lst = new ArrayList<>();
        ResDTO item = new ResDTO();
        ResDTO item1 = new ResDTO();
        try{
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sdi.getEmailTo());
            dataMail.setSubject(sdi.getSubject());

            Map<String, Object> props = new HashMap<>();
            props.put("name", sdi.getName());
            props.put("username", sdi.getUsername());
            props.put("password", DataUtils.generateTempPwd(6));
            dataMail.setProps(props);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            Context context = new Context();
            context.setVariables(dataMail.getProps());
            item.setHtml(templateEngine.process(Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER, context));
            item.setType(1);
            item.setName("TEMP1");
            lst.add(item);
            item1.setHtml(templateEngine.process(Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER1, context));
            item1.setType(2);
            item1.setName("TEMP2");
            lst.add(item1);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return lst;
    }

    public String chooseTypeTemplate(Integer type, Context context) {
        String html = "";
        try {
            switch (type) {
                case Const.TP1:
                    html = templateEngine.process(Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER, context);
                    break;
                case Const.TP2:
                    html = templateEngine.process(Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER1, context);
                    break;
                default:
                    break;
            }
        }finally {

        }
        return html;
    }
}
