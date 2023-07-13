package com.ifeng.ad.mutacenter.common.enums;

import com.ifeng.ad.mutacenter.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum ImageMimeType {
    IMAGE_JPEG("image/jpeg"),
    IMAGE_JPG("image/jpg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif");


    private final String value;

    ImageMimeType (String value) {
        this.value = value;
    }

    public static List<String>  getAllImageMimes(){
        List<String> imageMimesList = new ArrayList<>();
        imageMimesList.add(StringUtils.afterSlashString(IMAGE_JPEG.value));
        imageMimesList.add(StringUtils.afterSlashString(IMAGE_JPG.value));
        imageMimesList.add(StringUtils.afterSlashString(IMAGE_PNG.value));
        imageMimesList.add(StringUtils.afterSlashString(IMAGE_GIF.value));
        return imageMimesList;
    }
}

