package com.ethan.xlib.view.richtext;

import android.text.TextUtils;

import com.ethan.xlib.app.TAppBase;
import com.ethan.xlib.component.qqface.FaceUtil;
import com.ethan.xlib.view.richtext.element.ContentElement;
import com.ethan.xlib.view.richtext.element.NickNameElement;
import com.ethan.xlib.view.richtext.element.RichTextElement;
import com.ethan.xlib.view.richtext.element.SmileyElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;


/**
 * 富文本解析工具类
 */
public class RichTextParser {

    private static NickNameElement getNickNameElement(String matchResult) {
        int uinIndex = matchResult.indexOf(Patterns.UIN_SEPERATE);
        uinIndex += Patterns.UIN_SEPERATE.length();
        int prefixLen = matchResult.indexOf(Patterns.NICK_NAME_SEPERATE);

        if (uinIndex == -1 || prefixLen == -1) {
            return null;
        }
        String uin = matchResult.substring(uinIndex, prefixLen);
        String nickname = matchResult.substring(prefixLen + Patterns.NICK_NAME_SEPERATE.length(), matchResult.length() - 1);

        NickNameElement nickNameElement = new NickNameElement();
        nickNameElement.nickName = RichTextBuilder.decode(nickname);// 本地组nickname的时候escape过
        try {
            nickNameElement.uin = Long.parseLong(uin);
        } catch (Exception e) {
        }
        return nickNameElement;
    }

    public static ArrayList<NickNameElement> parseNickName(StringBuilder sb) {
        ArrayList<NickNameElement> elements = new ArrayList<>();
        Matcher m = Patterns.NICK_PATTERN.matcher(sb);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String s = m.group();
            NickNameElement nickNameElement = getNickNameElement(s);
            if (nickNameElement == null)
                continue;
            nickNameElement.startPosition = start;
            nickNameElement.endPosition = end;
            nickNameElement.offset = s.length() - nickNameElement.nickName.length();
            elements.add(nickNameElement);
        }
        return elements;
    }

    public static ArrayList<SmileyElement> parseSmiley(StringBuilder sb) {
        ArrayList<SmileyElement> localSmileyelements = new ArrayList<SmileyElement>();
        Matcher m = Patterns.SMILEY_PATTERN.matcher(sb);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String emotionCode = m.group();

            int index = FaceUtil.getFaceNames(TAppBase.getContext()).indexOf("[" + m.group(1) + "]");
            if (index != -1) {
                SmileyElement element = new SmileyElement(RichTextElement.RICH_TEXT_ELEMENT_LOCAL_SMILEY);
                element.startPosition = start;
                element.endPosition = end;
                element.smileyCode = emotionCode;
                element.smileyIndex = index;

                localSmileyelements.add(element);
            }
        }
        return localSmileyelements;
    }

    private static ArrayList<RichTextElement> getSortedRichTextElements(StringBuilder content) {
        ArrayList<RichTextElement> elements = new ArrayList<RichTextElement>();

        ArrayList<SmileyElement> smileyElements = parseSmiley(content);
        ArrayList<NickNameElement> nickNameElements = parseNickName(content);
        elements.addAll(smileyElements);
        elements.addAll(nickNameElements);

        Collections.sort(elements);// 按起始位置排序
        for (int i = 0; i < elements.size(); ++i) {//消除嵌套解析
            while (i < elements.size() - 1 && elements.get(i).endPosition > elements.get(i + 1).startPosition) {
                elements.remove(i + 1);
            }
        }

        ArrayList<RichTextElement> finalElements = new ArrayList<RichTextElement>();
        if (elements.size() == 0) {
            ContentElement contentElement = new ContentElement();
            contentElement.startPosition = 0;
            contentElement.endPosition = content.length();
            contentElement.content = content.toString();
            finalElements.add(contentElement);
            return finalElements;
        }

        for (int i = 0, j = elements.size(), contentLength = content.length(); i < j; i++) {
            RichTextElement currentElement = elements.get(i);

            // 第一个RichTextElement之前的文字组装为ContentElement
            if (i == 0 && currentElement.startPosition != 0) {
                ContentElement contentElement = new ContentElement();
                contentElement.startPosition = 0;
                contentElement.endPosition = currentElement.startPosition;
                contentElement.content = content.substring(contentElement.startPosition, contentElement.endPosition);

                finalElements.add(contentElement);
            }
            finalElements.add(currentElement);

            if (i < (j - 1)) {// 倒数第2个
                // 当前的element跟下一个element之间的内容是ContentElement
                RichTextElement nextElement = elements.get(i + 1);
                if (nextElement.startPosition > currentElement.endPosition) {
                    ContentElement contentElement = new ContentElement();
                    contentElement.startPosition = (currentElement.endPosition);
                    contentElement.endPosition = nextElement.startPosition;
                    contentElement.content = content.substring(contentElement.startPosition, contentElement.endPosition);

                    finalElements.add(contentElement);
                }
            } else {// 最后一个需要特殊处理
                // 当前的element之后的全是ContentElement
                if (currentElement.endPosition < contentLength) {
                    ContentElement contentElement = new ContentElement();
                    contentElement.startPosition = (currentElement.endPosition);
                    contentElement.endPosition = contentLength;
                    contentElement.content = content.substring(contentElement.startPosition, contentElement.endPosition);

                    finalElements.add(contentElement);
                }
            }
        }

        return finalElements;
    }

    public static ArrayList<RichTextElement> getRichTextElements(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        StringBuilder content = new StringBuilder(text);
        ArrayList<RichTextElement> allElements = getSortedRichTextElements(content);
        int offset = 0;
        for (RichTextElement richTextElement : allElements) {
            switch (richTextElement.getType()) {
                case RichTextElement.RICH_TEXT_ELEMENT_NICK_NAME:
                    richTextElement.startPosition -= offset;
                    richTextElement.endPosition -= offset;
                    NickNameElement nickNameElement = (NickNameElement) richTextElement;
                    content.replace(nickNameElement.startPosition, nickNameElement.endPosition, nickNameElement.nickName);
                    nickNameElement.endPosition = nickNameElement.startPosition + nickNameElement.nickName.length();
                    break;
                default:
                    break;
            }
            offset += richTextElement.offset;
        }
        return allElements;
    }

}
