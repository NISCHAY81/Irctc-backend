package com.substring.irctc.services.impl;

import com.substring.irctc.entity.ImageMetaData;
import com.substring.irctc.services.FileUploadService;
import jakarta.validation.constraints.Size;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
public class CloudinaryFileUpload implements FileUploadService {
    @Override
    public ImageMetaData upload(MultipartFile file) throws IOException {


        return null;
    }
}
