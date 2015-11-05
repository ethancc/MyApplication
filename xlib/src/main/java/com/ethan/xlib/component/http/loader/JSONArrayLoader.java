package com.ethan.xlib.component.http.loader;

import android.text.TextUtils;

import com.ethan.xlib.common.util.IOUtil;
import com.ethan.xlib.component.cache.DiskCacheEntity;
import com.ethan.xlib.component.cache.LruDiskCache;
import com.ethan.xlib.component.http.RequestParams;
import com.ethan.xlib.component.http.request.UriRequest;

import org.json.JSONArray;
import java.io.InputStream;
import java.util.Date;

/**
 * Author: wyouflf
 * Time: 2014/06/16
 */
/*package*/ class JSONArrayLoader extends Loader<JSONArray> {

    private String contentStr;
    private String charset = "UTF-8";

    @Override
    public Loader<JSONArray> newInstance() {
        return new JSONArrayLoader();
    }

    @Override
    public void setParams(final RequestParams params) {
        if (params != null) {
            String charset = params.getCharset();
            if (charset != null) {
                this.charset = charset;
            }
        }
    }

    @Override
    public JSONArray load(final InputStream in) throws Throwable {
        contentStr = IOUtil.readStr(in, charset);
        return new JSONArray(contentStr);
    }

    @Override
    public JSONArray load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return this.load(request.getInputStream());
    }

    @Override
    public JSONArray loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        if (cacheEntity != null) {
            String text = cacheEntity.getTextContent();
            if (text != null) {
                return new JSONArray(contentStr);
            }
        }

        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {
        if (!TextUtils.isEmpty(contentStr)) {
            DiskCacheEntity entity = new DiskCacheEntity();
            entity.setKey(request.getCacheKey());
            entity.setLastAccess(System.currentTimeMillis());
            entity.setEtag(request.getETag());
            entity.setExpires(request.getExpiration());
            entity.setLastModify(new Date(request.getLastModified()));
            entity.setTextContent(contentStr);
            LruDiskCache.getDiskCache(request.getParams().getCacheDirName()).put(entity);
        }
    }
}
