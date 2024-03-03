package com.backend.ingresso.data.utilityExternal.Interface;

import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;

import java.util.List;

public interface ICloudinaryUti {
    ResultService<CloudinaryCreate> CreateImg(String url, Integer width, Integer height);
    ResultService<String> deleteImg(List<String> publicList);
}
