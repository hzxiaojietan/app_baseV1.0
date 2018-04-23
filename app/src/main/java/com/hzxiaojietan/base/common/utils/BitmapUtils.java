package com.hzxiaojietan.base.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 图片处理工具类
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    /**
     * 最大分辨率800*1000 bitmap,假定bitmap的Config.ARGB_8888
     */
    private static final int MAX_BITMAP_PIXELS = 800 * 1000 / 4;

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
                AppLog.d(TAG, "computeSampleSize while roundedSize:"
                        + roundedSize);
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
            AppLog.d(TAG, "computeSampleSize roundedSize:" + roundedSize);
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        AppLog.d(TAG, "computeInitialSampleSize w:" + w);
        AppLog.d(TAG, "computeInitialSampleSize h:" + h);

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        AppLog.d(TAG, "computeInitialSampleSize lowerBound:" + lowerBound);
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        AppLog.d(TAG, "computeInitialSampleSize upperBound:" + upperBound);
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap getSmallBitmap(String filePath)
            throws OutOfMemoryError {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = computeSampleSize(options, -1, MAX_BITMAP_PIXELS);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static boolean compressToFile(String filePath, File tofile) {
        try {
            Bitmap bm = getSmallBitmap(filePath);
            if (bm == null) {
                return false;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(tofile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            bm.recycle();
            bm = null;
        } catch (FileNotFoundException e) {
            return false;
        } catch (OutOfMemoryError e) {
            return false;
        }
        return true;
    }

    /**
     * 压缩到指定大小
     *
     * @param filePath 原图路径
     * @param toFile   保存路径
     * @param size     指定大小
     * @return
     */
    public static boolean compressToSize(String filePath, File toFile, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
//			Bitmap bm = getSmallBitmap(filePath);
//			if (bm == null) {
//				return false;
//			}

            Bitmap bm = BitmapFactory.decodeFile(filePath);
            int quality = 100;

            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);

            Logger.d("compress size = " + baos.toByteArray().length);
            while (baos.toByteArray().length > size) {
                baos.reset();
                quality -= 10;
                bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                Logger.d("compress size = " + baos.toByteArray().length);
            }
            baos.writeTo(new FileOutputStream(toFile));
            bm.recycle();
            bm = null;
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 压缩到指定大小
     *
     * @param bm       原图
     * @param toFile   保存路径
     * @param size     指定大小
     * @return
     */
    public static boolean compressToSize(Bitmap bm, File toFile, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int quality = 100;

            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);

            Logger.d("compress size = " + baos.toByteArray().length);
            while (baos.toByteArray().length > size) {
                baos.reset();
                quality -= 10;
                bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                Logger.d("compress size = " + baos.toByteArray().length);
                if (quality == 0)//如果图片的质量已降到最低则，不再进行压缩
                    break;
            }
            baos.writeTo(new FileOutputStream(toFile));
            bm.recycle();
            bm = null;
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Bitmap旋转
     * @param bitmap
     * @param orienation
     * @return
     */
    public static Bitmap rotation(Bitmap bitmap, int orienation){
        Matrix m = new Matrix();
        m.setRotate(orienation,(float) bitmap.getWidth()/2, (float) bitmap.getHeight()/2);
        return  Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }

}
