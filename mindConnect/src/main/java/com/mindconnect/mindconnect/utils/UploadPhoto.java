package com.mindconnect.mindconnect.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.EagerTransformation;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class UploadPhoto {

    public static String upload(@NotNull String filePath) throws IOException {

  Cloudinary  cloudinary = new Cloudinary();
         Map uploadResponse = cloudinary.uploader().upload(
                 filePath,
                 ObjectUtils.asMap(
                         "resource_type", "auto",
                         "use_filename", true,
                         "unique_filename", false,
                         "overwrite", true
                )
         );
         return uploadResponse.get("url").toString();
    }
}
