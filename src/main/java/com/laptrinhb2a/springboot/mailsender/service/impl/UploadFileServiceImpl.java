package com.laptrinhb2a.springboot.mailsender.service.impl;

import com.laptrinhb2a.springboot.mailsender.mapper.FileMapper;
import com.laptrinhb2a.springboot.mailsender.modal.response.FileUploadResponse;
import com.laptrinhb2a.springboot.mailsender.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources");

    @Autowired
    private FileMapper fileMapper;

    @Override
    public FileUploadResponse uploadImage(MultipartFile image) throws IOException {
        //Set up where to save photos
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");

        //If the user does not select an image, return null
        if (image.isEmpty()) {
            return null;
        }

        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }

        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }

        //get url
        String urlImage = imagePath.resolve(image.getOriginalFilename()).toString();

        FileUploadResponse fileUploadResponse = fileMapper.to(image, urlImage);

        return fileUploadResponse;
    }

    @Override
    public FileUploadResponse uploadVideo(MultipartFile video) throws IOException {
        //Set up where to save photos
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");

        //If the user does not select an image, return null
        if (video.isEmpty()) {
            return null;
        }

        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }

        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(video.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(video.getBytes());
        }

        //get url
        String urlImage = imagePath.resolve(video.getOriginalFilename()).toString();

        FileUploadResponse fileUploadResponse = fileMapper.to(video, urlImage);

        return fileUploadResponse;
    }

    @Override
    public List<FileUploadResponse> uploadMultiImage(MultipartFile[] files) throws Exception {
        //Set up where to save photos
        Path staticPath = Paths.get("static");
        Path filePath = Paths.get("files");

        List<MultipartFile> listFile = Arrays.asList(files);

        //If the user does not select an image, return null
        if (listFile.isEmpty()) {
            return null;
        }

        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(filePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(filePath));
        }

        List<FileUploadResponse> fileUploadResponses = new ArrayList<>();

        listFile.forEach(element -> {
            Path file = CURRENT_FOLDER.resolve(staticPath).resolve(filePath).resolve(element.getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(element.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileUploadResponse fileUploadResponse = new FileUploadResponse();
            fileUploadResponse.setFileName(StringUtils.cleanPath(element.getOriginalFilename()));
            fileUploadResponse.setUrl(filePath.resolve(element.getOriginalFilename()).toString());
            fileUploadResponse.setSize(element.getSize());

            fileUploadResponses.add(fileUploadResponse);

        });

        return fileUploadResponses;
    }
}
