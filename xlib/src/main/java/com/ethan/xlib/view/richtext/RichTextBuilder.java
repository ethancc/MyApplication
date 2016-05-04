package com.ethan.xlib.view.richtext;


import android.text.TextUtils;

/**
 * 组装富文本的工具类
 */
public class RichTextBuilder {

	/**
	 * 组装成<uin:xxx,nickname:xxx>
	 */
	public static String buildNicknameString(long uin, String nickName) {
		nickName = encode(nickName);
        if (TextUtils.isEmpty(nickName)) {
            nickName = String.valueOf(uin);
        }
		return new StringBuilder().append("<uin:").append(uin).append(",nickname:").append(nickName).append(">").toString();
	}

	public static String encode(String content) {
        if(TextUtils.isEmpty(content)) return "";
		content = content.replace("%", "%25"); // encode时必须放在第一个
		content = content.replace(",", "%2C");
		content = content.replace("}", "%7D");
		content = content.replace("{", "%7B");
		content = content.replace(":", "%3A");
		return content;
	}

	public static String decode(String content) {
        if(TextUtils.isEmpty(content)) return "";
		content = content.replace("%3A", ":");
		content = content.replace("%7D", "}");
		content = content.replace("%7B", "{");
		content = content.replace("%2C", ",");
		content = content.replace("%25", "%"); // decode时必须放在最后一个
		return content;
	}
}
