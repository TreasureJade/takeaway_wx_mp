package com.cins.hobo.takeaway_wx_mp.util;

import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : hobo
 * @className : UploadFileUtil
 * @date: 2021/1/21
 * @description: 上传文件工具类
 */
@Slf4j
public class UploadFileUtil {
    public static String uploadPic(String fileName, MultipartFile file) {
        File dest = new File(fileName);
        if (!dest.getParentFile().exists()) {
            throw new GlobalException(ResultEnum.DIR_NOT_EXIST);
        }
        try {
            file.transferTo(dest);
            log.info("文件上传成功 目录为:{}", fileName);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件上传失败,失败原因:{}", e.getMessage());
        }
        return "";
    }

    public static String getFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.substring(fileName.lastIndexOf("."));
    }


}
