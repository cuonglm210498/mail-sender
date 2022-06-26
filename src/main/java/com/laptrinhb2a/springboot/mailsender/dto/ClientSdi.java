package com.laptrinhb2a.springboot.mailsender.dto;

import lombok.Data;

@Data
public class ClientSdi {

    private String name;
    private String username;
    private String subject;
    private String content;
    private String[] url;
    private String emailTo;
    private Integer type;
}
