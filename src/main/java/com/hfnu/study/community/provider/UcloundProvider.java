package com.hfnu.study.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.hfnu.study.community.exception.CustomizeErrorCode;
import com.hfnu.study.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UcloundProvider {
    @Value("${ucloud.ufile.public-key}")
    private String public_key;

    @Value("${ucloud.ufile.private-key}")
    private String private_key;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.suffix}")
    private String suffix;

    @Value("${ucloud.ufile.time}")
    private Integer expire;


    public String upload(InputStream fileStream, String mineType, String fileName) {

        String generatorName;
        String[] fileSpilt = fileName.split("\\.");
        if (fileSpilt.length > 1) {
            //UUID生成唯一标识码
            generatorName = UUID.randomUUID().toString() + "." + fileSpilt[fileSpilt.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(public_key, private_key);
            ObjectConfig config = new ObjectConfig(region, suffix);

            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mineType)
                    .nameAs(generatorName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generatorName, bucketName, expire)
                        .createUrl();
                return url;
            } else {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
