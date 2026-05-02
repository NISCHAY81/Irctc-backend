package com.substring.irctc.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TrainImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String filename;
    private String fileType;
    private long size;
    private LocalDateTime uploadTime = LocalDateTime.now();

    @OneToOne(mappedBy = "trainImage")
    private Train train;

    public TrainImage(int id, String filename, String fileType, long size, LocalDateTime uploadTime, Train train) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.size = size;
        this.uploadTime = uploadTime;
        this.train = train;
    }

    public TrainImage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
