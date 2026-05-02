package com.substring.irctc.dto;

import com.substring.irctc.entity.TrainImage;

import java.time.LocalDateTime;

public record TrainImageResponse(
        long id,
        String fileName,
        String fileType,
        String url,
        long size,
        LocalDateTime uploadTime
) {

    public  static TrainImageResponse from(TrainImage trainImage, String baseUrl, String trainNo) {
        return new TrainImageResponse(
                trainImage.getId(),
                trainImage.getFilename(),
                trainImage.getFileType(),
                 baseUrl+"/trains"+trainNo+"/image",
                trainImage.getSize(),
                trainImage.getUploadTime()
        );

    }
}
