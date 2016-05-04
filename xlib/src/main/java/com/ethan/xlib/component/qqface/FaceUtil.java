package com.ethan.xlib.component.qqface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.SparseArray;

import com.ethan.xlib.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FaceUtil
 * 工具类，用于将包含表情的String转成SpannableString
 */
public class FaceUtil {
    private static Pattern facePattern = Pattern.compile("\\[{1}([\u4e00-\u9fa5]*)\\]{1}"); // 匹配“[中文字符]”，比如“[你好啊]”，捕获的是[]之间的部分

    private static Pattern emojiPattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    final static private Pattern urlPattern = Pattern.compile("<img\\s*src=\"(.+?)\"\\s*/>");//url标签解码正则表达式匹配规则

    private static List<String> faceNames;
    public static List<String> getFaceNames(Context context) {
        if (faceNames == null) {
            faceNames = Arrays.asList(context.getResources().getStringArray(R.array.qqface_smiley_values_ch));
        }
        return faceNames;
    }

    public static int getFaceNameCount(Context context) {
        List<String> names = getFaceNames(context);
//        if (names != null) {
            return names.size();
//        }
//        return 0;
    }

    private static SparseArray<String> faceNameCache = new SparseArray<String>();
    public static String getFaceName(Context context, int faceIdx) {
        // 先到cache里面找
        String faceName = faceNameCache.get(faceIdx);
        if (faceName != null) {
            return faceName;
        }

        if (getFaceNames(context) == null) return null;
        if (faceIdx < 0 || faceIdx >= getFaceNames(context).size()) return null;
        faceName = getFaceNames(context).get(faceIdx);

        if (faceName != null) {
            faceNameCache.put(faceIdx, faceName); // 装进cache里面的肯定是非空的
        }
        return faceName;
    }

    private static int faceSize = -1;
    public static int getFaceSize(Context context) {
        if (faceSize == -1) {
            faceSize = (int) context.getResources().getDimension(R.dimen.qqface_msg_chat_emoji_size);
        }
        return faceSize;
    }

    /*
    采用动态获取资源id的方式可能有性能问题：
        int resourceId = context.getResources().getIdentifier("smiley_" + faceIdx
                , "drawable", context.getPackageName());
     */
    private static int[] faceDrawableResIdArray = new int[] {
            R.drawable.smiley_0, R.drawable.smiley_1, R.drawable.smiley_2, R.drawable.smiley_3, R.drawable.smiley_4, R.drawable.smiley_5,
            R.drawable.smiley_6, R.drawable.smiley_7, R.drawable.smiley_8, R.drawable.smiley_9, R.drawable.smiley_10, R.drawable.smiley_11,
            R.drawable.smiley_12, R.drawable.smiley_13, R.drawable.smiley_14, R.drawable.smiley_15, R.drawable.smiley_16, R.drawable.smiley_17,
            R.drawable.smiley_18, R.drawable.smiley_19, R.drawable.smiley_20, R.drawable.smiley_21, R.drawable.smiley_22, R.drawable.smiley_23,
            R.drawable.smiley_24, R.drawable.smiley_25, R.drawable.smiley_26, R.drawable.smiley_27, R.drawable.smiley_28, R.drawable.smiley_29,
            R.drawable.smiley_30, R.drawable.smiley_31, R.drawable.smiley_32, R.drawable.smiley_33, R.drawable.smiley_34, R.drawable.smiley_35,
            R.drawable.smiley_36, R.drawable.smiley_37, R.drawable.smiley_38, R.drawable.smiley_39, R.drawable.smiley_40, R.drawable.smiley_41,
            R.drawable.smiley_42, R.drawable.smiley_43, R.drawable.smiley_44, R.drawable.smiley_45, R.drawable.smiley_46, R.drawable.smiley_47,
            R.drawable.smiley_48, R.drawable.smiley_49, R.drawable.smiley_50, R.drawable.smiley_51, R.drawable.smiley_52, R.drawable.smiley_53,
            R.drawable.smiley_54, R.drawable.smiley_55, R.drawable.smiley_56, R.drawable.smiley_57, R.drawable.smiley_58, R.drawable.smiley_59,
            R.drawable.smiley_60, R.drawable.smiley_61, R.drawable.smiley_62, R.drawable.smiley_63, R.drawable.smiley_64, R.drawable.smiley_65,
            R.drawable.smiley_66,
            R.drawable.smiley_67, R.drawable.smiley_68, R.drawable.smiley_69, R.drawable.smiley_70, R.drawable.smiley_71, R.drawable.smiley_72,
            R.drawable.smiley_73, R.drawable.smiley_74, R.drawable.smiley_75, R.drawable.smiley_76, R.drawable.smiley_77, R.drawable.smiley_78,
            R.drawable.smiley_79, R.drawable.smiley_80, R.drawable.smiley_81, R.drawable.smiley_82, R.drawable.smiley_83, R.drawable.smiley_84,
            R.drawable.smiley_85, R.drawable.smiley_86, R.drawable.smiley_87, R.drawable.smiley_88, R.drawable.smiley_89, R.drawable.smiley_90,
            R.drawable.smiley_91, R.drawable.smiley_92, R.drawable.smiley_93, R.drawable.smiley_94, R.drawable.smiley_95, R.drawable.smiley_96,
            R.drawable.smiley_97, R.drawable.smiley_98, R.drawable.smiley_99, R.drawable.smiley_100, R.drawable.smiley_101, R.drawable.smiley_102,
            R.drawable.smiley_103, R.drawable.smiley_104
    };

    public static int[] getImageIds(){
        return faceDrawableResIdArray;
    }

    /**
     * 根据表情索引，返回表情图片对象
     * @param faceIdx 表情索引，目前资源中带的表情索引范围是[0,99]
     * @return 对应的表情Drawable对象
     */
    public static Drawable getFaceDrawable(Context context, int faceIdx) {
        if (faceIdx < 0 || faceIdx >= faceDrawableResIdArray.length) return null;
        int resId = faceDrawableResIdArray[faceIdx];
        if (resId == 0) return null;

        Drawable d = null;
        try {
            d = context.getResources().getDrawable(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获取DEL图标
     * @return DEL图标
     */
    public static Drawable getDelDrawable(Context context) {
        Drawable d = null;
        try {
            d = context.getResources().getDrawable(R.drawable.qqface_delete);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获取content转换成的带表情的字符串,textview可以直接使用
     * @return
     */
    public static SpannableStringBuilder parseFaceByText(Context context, String content) {
        return parseFaceByText(context, content, true);
    }

    private static SpannableStringBuilder parseFaceByText(Context context, String content, boolean useFineImageSpan) {
        if (TextUtils.isEmpty(content)) { // 无意义的输入
            return new SpannableStringBuilder("");
        }

        if (!content.contains("[")) { // 粗糙判断下有没有[
            return new SpannableStringBuilder(content);
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        Matcher matcher = facePattern.matcher(content);

        boolean hasException = false;
        while (!hasException && matcher.find()) {
            int faceIdx = getFaceNames(context).indexOf("[" + matcher.group(1) + "]");
            if (faceIdx < 0) { // [xxx]不是个表情，只是个普通字符串，跳到下一个匹配的部分
                continue;
            }

            Drawable d = getFaceDrawable(context, faceIdx);
            if (d == null) { // 没有找到这个表情对应的icon，只能把它当作普通字符串了，跳到下一个匹配的部分
                continue;
            }

            try {
                d.setBounds(0, 0, getFaceSize(context), getFaceSize(context));// 设置表情图片的显示大小
                ImageSpan span = (useFineImageSpan ? new FaceImageSpan(faceIdx, d, ImageSpan.ALIGN_BASELINE) : new ImageSpan(d, ImageSpan.ALIGN_BASELINE));
                builder.setSpan(span, matcher.start(), matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) { // 发生了异常，不能再下一轮了
                hasException = true;
            }
        }

        if (hasException) {
            return new SpannableStringBuilder(content);
        }

        return builder;
    }


    /**
     * 只能粗略的判断是否是qqface
     * @return
     */
    public static boolean isQQFace(CharSequence sequence) {
        if (sequence == null) {
            return false;
        }

        Pattern p = Pattern.compile("\\[(.|..|...)\\]");
        Matcher m = p.matcher(sequence);
        return m.find();
    }

    public static boolean isEmoji(CharSequence sequence) {
        if (sequence == null) {
            return false;
        }

        if (sequence.length() != 2) {
            return false;
        }

        Pattern pEmoji = getEmojiPattern();
        Matcher m = pEmoji.matcher(sequence);
        return m.find();
    }

    public static boolean isPic(CharSequence sequence){
        if (sequence == null) {
            return false;
        }

        if (sequence.length() <= 20) {
            return false;
        }
        Pattern pimgji = getImgPattern();
        Matcher m = pimgji.matcher(sequence);
        return m.find();
    }

    /**
     * 获取带表情文本的长度，一个表情的长度为1 表情可以是QQ表情或者Emoji表情
     */
    public static int getDestLength(CharSequence sequence) {
        if (sequence == null) {
            return 0;
        }

        // 把QQ表情替换成单个字符
        Pattern pQQFace = Pattern.compile("\\[(.|..|...)\\]");
        Matcher m = pQQFace.matcher(sequence);
        String result = m.replaceAll("-");

        // 把emoji表情替换成单个字符
        Pattern pEmoji = getEmojiPattern();
        m = pEmoji.matcher(result);
        result = m.replaceAll("-");

//            log.d("getDestLength:" + result);


        return result.length();
    }

    public static Pattern getEmojiPattern(){
        return emojiPattern;
    }

    public static Pattern getImgPattern(){
        return urlPattern;
    }
}
