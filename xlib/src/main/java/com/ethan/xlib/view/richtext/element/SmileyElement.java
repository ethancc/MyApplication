package com.ethan.xlib.view.richtext.element;

import android.os.Parcel;
import android.os.Parcelable;

public class SmileyElement extends RichTextElement {

    public int smileyIndex;
    public String smileyCode;
    public String smileyUrl;
    public int width = -1;
    public int height = -1;
    
    public SmileyElement(int type) {
        super(type);
    }

    public SmileyElement() {
        this(RICH_TEXT_ELEMENT_LOCAL_SMILEY);
    }

    @Override
    public String toString() {
        return "SmileyElement [smileyCode=" + smileyCode + ", smileyIndex=" + smileyIndex + ", startPosition="
                + startPosition + ", endPosition=" + endPosition + "]";
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
		dest.writeInt(smileyIndex);
		dest.writeString(smileyCode);
		dest.writeString(smileyUrl);
		dest.writeInt(width);
		dest.writeInt(height);
	}

	
	public static final Parcelable.Creator<SmileyElement> CREATOR = new Parcelable.Creator<SmileyElement>() {

		@Override
		public SmileyElement createFromParcel(Parcel source) {
			int type = source.readInt();
			
			SmileyElement element = new SmileyElement(type);
			
			element.startPosition = source.readInt();
			element.endPosition = source.readInt();
			element.smileyIndex = source.readInt();
			element.smileyCode = source.readString();
			element.smileyUrl = source.readString();
			element.width = source.readInt();
			element.height = source.readInt();
			return element;
		}

		@Override
		public SmileyElement[] newArray(int size) {
			return null;
		}

	};

}
