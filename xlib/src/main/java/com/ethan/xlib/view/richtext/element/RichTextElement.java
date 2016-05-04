package com.ethan.xlib.view.richtext.element;

import android.os.Parcelable;

/**
 * 富文本元素基类
 */
public abstract class RichTextElement implements Comparable<RichTextElement>, Parcelable {

	public static final int RICH_TEXT_ELEMENT_NICK_NAME = 1;
	public static final int RICH_TEXT_ELEMENT_LOCAL_SMILEY = 2;
//	public static final int RICH_TEXT_ELEMENT_NET_SMILEY = 3;
	public static final int RICH_TEXT_ELEMENT_CONTENT = 4;
//	public static final int RICH_TEXT_ELEMENT_AT = 5;
//	public static final int RICH_TEXT_ELEMENT_CUSTOM_URL = 6;
//	public static final int RICH_TEXT_ELEMENT_COMMENT = 7;

	protected int type;

	/**
	 * the index of the first character that is included
	 */
	public int startPosition;
	/**
	 * exclusive
	 */
	public int endPosition;
	public int offset;

	public RichTextElement(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	@Override
	public int compareTo(RichTextElement o) {
		if (o == null) {
			return 1;
		} else {
			return (startPosition - o.startPosition);
		}
	}

}
