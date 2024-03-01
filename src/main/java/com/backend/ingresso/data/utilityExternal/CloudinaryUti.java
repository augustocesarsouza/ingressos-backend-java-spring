package com.backend.ingresso.data.utilityExternal;

import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.data.cloudinaryUtil.CloudinaryCreate;
import com.backend.ingresso.data.utilityExternal.Interface.ICloudinaryUti;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryUti implements ICloudinaryUti {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryUti(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ResultService<CloudinaryCreate> CreateImg(String url, Integer width, Integer height) {
        try {
            Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.asMap(
                    "transformation", new Transformation<>().width(width).height(height).crop("fill").quality(100)
            ));

            String publicId = (String) uploadResult.get("public_id");
            String imageUrl = (String) uploadResult.get("url");

            return ResultService.Ok(new CloudinaryCreate(publicId, imageUrl));
        }catch (Exception e){
           return ResultService.Fail(e.getMessage());
        }
    }
    @Override
    public ResultService<String> deleteImg(List<String> publicList) {
        try {
            ApiResponse delete = cloudinary.api().deleteResources(publicList, null);

            var result = delete.get("deleted");

            if(result == null)
                return ResultService.Fail("Unable to delete image in cloudinary");

            return ResultService.Ok("all right when deleting image");
        }catch (Exception e){
            return ResultService.Fail(e.getMessage());
        }
    }

}
