package com.houkew.bazzlebaby.utils;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;

import java.io.ByteArrayOutputStream;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/3 9:11
 * @Description: Bitmap相关工具
 */

public class BitmapTools {

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public AVFile Bitmap2AVFile(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return new AVFile(System.currentTimeMillis()+ AVUser.getCurrentUser().getUsername(),baos.toByteArray());
    }


}
