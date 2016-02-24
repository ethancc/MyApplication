package com.ethan.xlib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.ethan.xlib.R;

public class Chatplug_EditText extends EditText {

	private InputConnection inputConnection;


	public Chatplug_EditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Chatplug_EditText(Context context) {
		super(context);
		init();
	}

	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		inputConnection = super.onCreateInputConnection(outAttrs);
		return inputConnection;
	}

	@Override
	public boolean onCheckIsTextEditor() {
		return true;
	}
	public InputConnection getInputConnection() {
		return inputConnection;
	}

	public Chatplug_EditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void insetText(int pos, int emoji_Width) {
		String text = "[大笑]";//QQSmileyManager.getTexts(getContext()).get(pos);
		// 插入的表情
		SpannableString ss = new SpannableString(text);
		Drawable d = getContext().getResources().getDrawable(R.drawable.smiley1); //QQSmileyManager.getSmileyDrawable(getContext(), pos);
//		Drawable d = getResources().getDrawable(
//				(int) mGVFaceAdapter.getItemId(position));
		
		d.setBounds(0, 0, emoji_Width, emoji_Width);// 设置表情图片的显示大小
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, text.length(), 
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 在光标所在处插入表情
		getText().insert(getSelectionStart(),
				ss);
	}

	private void init() {
		// addTextChangedListener(textWatcher);
	}


	public static  interface InextBtnEnabled{
		void setNextBtnEnable();
	}

}