package com.ifeng.ad.mutacenter.common.enums;


import com.ifeng.ad.mutacenter.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum VideoMimeType {
    VIDEO_FLV("video/x-flv"),
    VIDEO_MP4("video/mp4");


    private final String value;

    VideoMimeType (String value) {
        this.value = value;
    }

    public static List<String> getAllVideoMimes(){
        List<String> videoMimesList = new ArrayList<>();
        videoMimesList.add(StringUtils.afterSlashString(VIDEO_FLV.value));
        videoMimesList.add(StringUtils.afterSlashString(VIDEO_MP4.value));
        return videoMimesList;
    }
}
