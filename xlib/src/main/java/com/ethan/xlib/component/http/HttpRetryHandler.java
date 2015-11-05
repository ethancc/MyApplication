package com.ethan.xlib.component.http;


import com.ethan.xlib.common.Callback;
import com.ethan.xlib.common.util.LogUtil;
import com.ethan.xlib.component.ex.HttpException;
import com.ethan.xlib.component.http.request.UriRequest;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
public final class HttpRetryHandler {

    private int maxRetryCount;

    private static HashSet<Class<?>> blackList = new HashSet<Class<?>>();

    static {
        blackList.add(HttpException.class);
        blackList.add(Callback.CancelledException.class);
        blackList.add(MalformedURLException.class);
        blackList.add(URISyntaxException.class);
        blackList.add(NoRouteToHostException.class);
        blackList.add(PortUnreachableException.class);
        blackList.add(ProtocolException.class);
        blackList.add(NullPointerException.class);
        blackList.add(FileNotFoundException.class);
        blackList.add(JSONException.class);
        blackList.add(SocketTimeoutException.class);
        blackList.add(UnknownHostException.class);
        blackList.add(IllegalArgumentException.class);
    }

    public HttpRetryHandler() {
        this(2);
    }

    public HttpRetryHandler(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    boolean retryRequest(Throwable ex, int count, UriRequest request) {

        if (count > maxRetryCount || request == null) {
            LogUtil.w("The Max Retry times has been reached!");
            LogUtil.w(ex.getMessage(), ex);
            return false;
        }

        if (!HttpMethod.permitsRetry(request.getParams().getMethod())) {
            LogUtil.w("The Request Method can not be retried.");
            LogUtil.w(ex.getMessage(), ex);
            return false;
        }

        if (blackList.contains(ex.getClass())) {
            LogUtil.w("The Exception can not be retried.");
            LogUtil.w(ex.getMessage(), ex);
            return false;
        }

        return true;
    }
}
