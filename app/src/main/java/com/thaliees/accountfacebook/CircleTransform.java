package com.thaliees.accountfacebook;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        // Define width and height of our source
        final int width = source.getWidth(), height = source.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // Define the radius (shaped rounded)
        // By dividing by 2, we make the image completely circular
        // NOTE: You can take the width or the height
        final int radius = width / 2;
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);

        // But if you want the corners to be rounded without reaching the circle, define a radius less than the width (or height) / 2, for example, 10
        //final int radius = 10;
        //canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        // NOTE: You can create a constructor where you define the radius when instantiating the class

        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return "rounded()";
    }
}
