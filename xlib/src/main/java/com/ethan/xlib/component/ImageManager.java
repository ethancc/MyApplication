package com.ethan.xlib.component;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import com.ethan.xlib.common.Callback;
import com.ethan.xlib.component.image.ImageOptions;

import java.io.File;

/**
 * Created by wyouflf on 15/6/17.
 * 图片绑定接口
 */
public interface ImageManager {

    void bind(ImageView view, String url);

    void bind(ImageView view, String url, ImageOptions options);

    void bind(ImageView view, String url, Callback.CommonCallback<Drawable> callback);

    void bind(ImageView view, String url, ImageOptions options, Callback.CommonCallback<Drawable> callback);

    Callback.Cancelable loadDrawable(String url, ImageOptions options, Callback.CommonCallback<Drawable> callback);

    Callback.Cancelable loadFile(String url, ImageOptions options, Callback.CommonCallback<File> callback);

    void clearCacheFiles();
}
