package com.laptrinhb2a.springboot.mailsender.mapper;

import com.laptrinhb2a.springboot.mailsender.modal.response.FileUploadResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileMapper {

    public FileUploadResponse to(MultipartFile file, String url) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();
        fileUploadResponse.setFileName(fileName);
        fileUploadResponse.setSize(size);
        fileUploadResponse.setUrl(url);
        return fileUploadResponse;
    }
}
