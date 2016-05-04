package com.ethan.xlib.view.richtext.element;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 昵称元素
 */
public class NickNameElement extends RichTextElement {

	public long uin;
	public String nickName;

	public NickNameElement() {
		this(RICH_TEXT_ELEMENT_NICK_NAME);
	}

	public NickNameElement(int type) {
		super(type);
	}

	@Override
	public String toString() {
		return "NickNameElement [uin=" + uin + ", nickName=" + nickName + ", startPosition=" + startPosition + ", endPosition=" + endPosition + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
		dest.writeInt(startPosition);
		dest.writeInt(endPosition);
		dest.writeLong(uin);
		dest.writeString(nickName);
	}

	public static final Parcelable.Creator<NickNameElement> CREATOR = new Parcelable.Creator<NickNameElement>() {

		@Override
		public NickNameElement createFromParcel(Parcel source) {
			NickNameElement element = new NickNameElement();
			element.type = source.readInt();
			element.startPosition = source.readInt();
			element.endPosition = source.readInt();
			element.uin = source.readLong();
			element.nickName = source.readString();
			return element;
		}

		@Override
		public NickNameElement[] newArray(int size) {
			return null;
		}

	};

}
