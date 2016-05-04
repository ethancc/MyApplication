package com.ethan.xlib.view.richtext;

import java.util.regex.Pattern;

/**
 * 富文本正则的常量
 */
public class Patterns {

	public static final Pattern NICK_PATTERN = Pattern.compile("<uin:.*?,nickname:.*?>");

	public static final String NICK_NAME_SEPERATE = ",nickname:";
	public static final String UIN_SEPERATE = "uin:";

    public static final Pattern SMILEY_PATTERN = Pattern
            .compile("\\[{1}([\u4e00-\u9fa5]*)\\]{1}");
}
