package com.shijiucheng.konghua.main.Utils;

import android.content.Context;
import android.text.TextPaint;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isPhoneNumber(String input) {// 判断手机号码是否规则
//        String regex = "(1[0-9][0-9]|15[0-9]|18[0-9])\\d{8}";
        String regex = "[1][3456789]\\d{9}";
        Pattern p = Pattern.compile(regex);
        return p.matches(regex, input);//如果不是号码，则返回false，是号码则返回true
    }

    /**
     * 判断邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否包含字母和数字
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        return isDigit && isLetter && str.matches(regex);
    }

    public static boolean isQQ(String qq) {
        return qq.matches("[1-9][0-9]{4,14}");
    }

    public static boolean isIdentity(String number){
        return number.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
    }

    /**
     * 计算字符串的长度（px）
     *
     * @param context  context
     * @param text     text
     * @param textSize textSize
     * @return text所占的像素值
     */
    public static float getTextWidth(Context context, String text, float textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

    public static boolean listIsEmpty(List list) {
        return list == null || list.isEmpty();
    }
}
