package com.hzxiaojietan.base.common.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by xiaojie.tan on 2017/10/26
 * ViewUtils
 */
public class ViewUtils {

    public static final int RIPPLE_DELAY = 100;

    public static void showInput(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideInput(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    public static void rippleClickDelay(final View view, @NonNull final View.OnClickListener onClickListener) {
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onClickListener.onClick(view);
                        }
                    }, RIPPLE_DELAY);
                }
            });
        }
    }

    //是否超出行数
    public static boolean overLine(TextView textView, int maxLine) {
        if (textView == null)
            return false;

        boolean isOverSize = true;
        try {
            Field field = textView.getClass().getSuperclass().getDeclaredField("mLayout");
            field.setAccessible(true);
            Layout mLayout = (Layout) field.get(textView);
            if (mLayout == null)
                return false;
            isOverSize = mLayout.getEllipsisCount(maxLine - 1) > 0;
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return isOverSize;
    }
}
