package com.substring.irctc.services.impl;

import com.substring.irctc.entity.ImageMetaData;
import com.substring.irctc.helper.Hellper;
import com.substring.irctc.services.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${file.upload.folder}")
private  String folder;
    @Override
    public ImageMetaData upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);

        InputStream inputStream = file.getInputStream();


        if(!Files.exists(Paths.get(folder ))) {
            Files.createDirectories(Paths.get(folder));
        }
String fileNameWithPath = Hellper.getFileName(folder,originalFilename);
        Files.copy(file.getInputStream(), Paths.get(folder + originalFilename), StandardCopyOption.REPLACE_EXISTING);

        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setFileId(UUID.randomUUID().toString());
        imageMetaData.setFileName(fileNameWithPath);
        imageMetaData.setFileSize(file.getSize());
        imageMetaData.setContentType(file.getContentType());
        imageMetaData.setUploadTime(LocalDateTime.now( ));

        return  imageMetaData;
    }
}
