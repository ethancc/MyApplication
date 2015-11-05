package com.ethan.xlib.component.http.app;


import com.ethan.xlib.component.http.request.UriRequest;

/**
 * Created by wyouflf on 15/8/4.
 */
public interface ResponseParser {

    void checkResponse(UriRequest request) throws Throwable;

    Object parse(Class<?> resultType, String result) throws Throwable;
}
