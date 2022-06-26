package com.laptrinhb2a.springboot.mailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMailDTO {

    private String to; //người nhận mail
    private String subject; //tiêu đề mail
    private String content; // Nội dung mail
    private Map<String, Object> props; //Truyền thông tin động. VD: password, token,.. vào template và gửi cho user
}
