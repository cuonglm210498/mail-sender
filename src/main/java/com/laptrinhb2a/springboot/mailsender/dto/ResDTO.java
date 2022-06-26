package com.laptrinhb2a.springboot.mailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResDTO {

    private String html;
    private Boolean res;
    private Integer type;
    private String name;
    private String content;
}
