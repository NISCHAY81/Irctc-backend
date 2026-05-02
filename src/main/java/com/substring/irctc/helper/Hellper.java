package com.substring.irctc.helper;

import java.util.UUID;

public class Hellper {
    public static String getFileName(String folder,String originalFileName) {
        return folder + UUID.randomUUID()+ "-" + originalFileName;
    }
}
