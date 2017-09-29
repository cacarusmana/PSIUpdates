package com.global.imd.psiupdates.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.global.imd.psiupdates.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Caca Rusmana on 25/09/2017.
 */

public class Utility {


    /*
    to check whether response result from server is valid or no
     */
    public static boolean checkValidResult(String result) {

        if (result != null) {
            if (result.contains(Constant.ERROR_MESSAGE_FIELD)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    /*
    to check whether response result from server is valid or no
    if yes will show toast to show the error message
     */
    public static boolean checkValidResult(Context context, String result) {

        if (result != null) {
            if (result.contains(Constant.ERROR_MESSAGE_FIELD)) {
                try {
                    JSONObject json = new JSONObject(result);

                    Toast.makeText(context, json.getString(Constant.ERROR_MESSAGE_FIELD), Toast.LENGTH_LONG).show();
                } catch (JSONException je) {
                    je.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.message_problem_communication), Toast.LENGTH_LONG).show();
                }

                return false;
            }

        } else {
            Toast.makeText(context, context.getString(R.string.message_problem_communication), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    // to get file name of status icon
    public static String getImageFileName(String status) {
        return status.substring(status.indexOf(Constant.COLON) + 1).trim().replace(Constant.SPACE, Constant.UNDERSCORE).toLowerCase();
    }

    public static Animation expandAnimation() {
        RotateAnimation anim = new RotateAnimation(180f, 0f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(Constant.ANIMATION_DURATION);
        return anim;
    }

    public static Animation collapseAnimation() {
        RotateAnimation anim = new RotateAnimation(-180f, 0f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(Constant.ANIMATION_DURATION);
        return anim;
    }
}
