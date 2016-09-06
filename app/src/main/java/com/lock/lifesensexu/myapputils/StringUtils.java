package com.lock.lifesensexu.myapputils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lifesensexu on 16/9/6.
 */
public class StringUtils {
    private static final String TAG = "StringUtil";

    private StringUtils() {

    }

    /**
     * @author xyc
     * @ClassName isEmptyOrNull
     * @Description 判断一个字符串是否为空
     */
    public static boolean isEmptyOrNull(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author xyc
     * @ClassName isAllZero
     * @Description true 都是0 false 至少有一个不为0
     */
    public static boolean isAllZero(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * @author xyc
     * @ClassName isEmail
     * @Description 判断是否为Email
     */
    public static boolean isEmail(String str) {
        if (StringUtils.isEmptyOrNull(str)) {
            return false;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(str);
        return m.find();
    }
    /**
     * 剪切中间的字符串，替换为……
     *
     * @param sourceStirng
     * @param maxLength
     *            最大长度
     * @param tailLength
     *            尾部长度
     * @return
     */
    public static String subCenterString(String sourceStirng, int maxLength,
                                         int tailLength)
    {
        StringBuilder result = null;
        int length = sourceStirng.length();
        if (length > maxLength && maxLength > 10)
        {
            if (tailLength <= 0)
            {
                tailLength = maxLength/2;
            }

            int headLength = maxLength - tailLength;
            result = new StringBuilder();
            result.append(sourceStirng.substring(0, headLength));
            result.append("*******");
            result.append(sourceStirng.substring(length - tailLength));
        }
        else {
            result = new StringBuilder(sourceStirng);
        }

        return result.toString();
    }
    /**
     * UTF-8 URL编码
     * @param str
     * @return
     */
    public static String urlEncodeUtf8(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return str;
        }
    }

    /**
     * 字符串数组是否包含字符串
     * @param strArray 要查找的字符串数组
     * @param str 要查找的字符串
     * @return
     */
    public static boolean isArrayContains(String[] strArray, String str)
    {
        boolean result = false;

        if(strArray == null || str==null)
        {
            return result;
        }

        for(int i=0; i<strArray.length; i++)
        {
            String tempString = strArray[i];
            if(tempString.equals(str))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 检查字符串是否为url
     * @param str
     * @return
     */
    public static boolean isUrl(String str)
    {
        boolean result = false;
        try
        {
            new URL(str);
            result = true;
        }
        catch (MalformedURLException e) {

        }
        return result;
    }
}
