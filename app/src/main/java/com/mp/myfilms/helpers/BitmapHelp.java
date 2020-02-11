package com.mp.myfilms.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BitmapHelp {

    public static byte[] BitmapToArrayBytes(Bitmap bp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imgBytes[] = stream.toByteArray();
        return imgBytes;
    }

    public static Bitmap BytesToBitmap(byte[] bytes) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        Bitmap bp = BitmapFactory.decodeStream(stream);
        return bp;
    }

}
