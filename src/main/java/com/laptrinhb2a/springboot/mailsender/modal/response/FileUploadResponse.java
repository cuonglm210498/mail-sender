package com.laptrinhb2a.springboot.mailsender.modal.response;

import lombok.Data;

@Data
public class FileUploadResponse {

    private String fileName;
    private String url;
    private long size;
}
