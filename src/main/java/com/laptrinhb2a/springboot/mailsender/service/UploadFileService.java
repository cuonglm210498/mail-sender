package com.laptrinhb2a.springboot.mailsender.service;

import com.laptrinhb2a.springboot.mailsender.modal.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadFileService {

    FileUploadResponse uploadImage(MultipartFile image) throws IOException;

    FileUploadResponse uploadVideo(MultipartFile video) throws IOException;

    List<FileUploadResponse> uploadMultiImage(MultipartFile[] file) throws Exception;
}
