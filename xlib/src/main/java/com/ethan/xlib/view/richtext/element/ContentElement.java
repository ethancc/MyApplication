package com.ethan.xlib.view.richtext.element;

import android.os.Parcel;

/**
 * 内容元素（非富文本）
 */
public class ContentElement extends  RichTextElement {

	public String content;

	public ContentElement() {
		super(RICH_TEXT_ELEMENT_CONTENT);
	}

	@Override
	public String toString() {
		return "ContentElement [content=" + content + ", startPosition=" + startPosition + ", endPosition=" + endPosition + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

}
