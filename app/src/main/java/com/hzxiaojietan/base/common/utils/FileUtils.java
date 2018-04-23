package com.hzxiaojietan.base.common.utils;

import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by xiaojie.tan on 2017/10/26
 * FileUtils
 */
public class FileUtils {
    /**
     * Delete file(file or folder).
     *
     * @param file (file or folder).
     */
    public static void delete(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                delete(f);
            } else {
                f.delete();
            }
        }
        file.delete();
    }


    public static void delete(String  filePath) {
        if(TextUtils.isEmpty(filePath)){
            return;
        }
        File file = new File(filePath);
        delete(file);
    }

    /**
     * 删除文件夹，包括子文件
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            File file = new File(folderPath);
            if(!file.exists()){
                return;
            }
            delete(file); // 删除完里面所有内容
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测文件是否可用
     */
    public static boolean checkFile(File f) {
        if (f != null && f.exists() && f.canRead() && (f.isDirectory() || (f.isFile() && f.length() > 0))) {
            return true;
        }
        return false;
    }

    /**
     * sd卡是否可用
     * @return
     */
    public static boolean isSDCardAvailable()
    {
        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片的旋转角度置为0  ，此方法可以解决某些机型拍照后图像，出现了旋转情况
     *
     * @Title: setPictureDegreeZero
     * @param path
     * @return void
     * @date 2012-12-10 上午10:54:46
     */
    public static void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 文件是否存在
     */
    public static boolean isExist(String filePath) {
        return !(filePath == null || filePath.isEmpty()) && new File(filePath).exists();

    }

    /**
     * 文件重命名
     * @param oldPath 旧名称
     * @param newPath 新名称
     * @return
     */
    public static boolean changeFileName(String oldPath, String newPath) {
        if(StringUtils.isEmpty(oldPath) || StringUtils.isEmpty(newPath)){
            return false;
        }
        File file = new File(oldPath);
        return file.renameTo(new File(newPath));
    }
}
