package com.example.mobile_athleta.service;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class RotateTransformation implements Transformation {
    private final float rotate;

    public RotateTransformation(float rotate) {
        this.rotate = rotate;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        Bitmap rotatedBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        if (rotatedBitmap != source) {
            source.recycle();
        }
        return rotatedBitmap;
    }

    @Override
    public String key() {
        return "rotate" + rotate;
    }
}
