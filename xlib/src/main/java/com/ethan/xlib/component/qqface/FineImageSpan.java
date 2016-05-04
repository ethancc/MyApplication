package com.ethan.xlib.component.qqface;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * 比普通ImageSpan更好的ImageSpan，特别是表情，对齐得相当好
 * 扩展了ImageSpan，用于表情imagespan排版对齐
 */
public class FineImageSpan extends ImageSpan {
    public FineImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
            int fontHeight = fmi.bottom - fmi.top;
            int drawHeight = rect.bottom - rect.top;

            int top = drawHeight / 2 - fontHeight / 4;
            int bottom = drawHeight / 2 + fontHeight / 4;

            fm.top = -bottom;
            fm.ascent = -bottom;
            fm.descent = top;
            fm.bottom = top;
        }

        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable d = getDrawable();
        canvas.save();
        int transY = 0;
        transY = ((bottom - top) - d.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        d.draw(canvas);
        canvas.restore();
    }
}
