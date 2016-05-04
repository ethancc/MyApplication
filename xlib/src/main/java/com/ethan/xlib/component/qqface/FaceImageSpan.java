package com.ethan.xlib.component.qqface;

import android.graphics.drawable.Drawable;

/**
 * FaceImageSpan
 */
public class FaceImageSpan extends FineImageSpan {
    private int faceIdx;
    public FaceImageSpan(int faceIdx, Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
        this.faceIdx = faceIdx;
    }

    public int getFaceIdx() {
        return faceIdx;
    }
}
