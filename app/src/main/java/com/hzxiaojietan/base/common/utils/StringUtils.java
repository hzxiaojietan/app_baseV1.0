package com.hzxiaojietan.base.common.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaojie.tan on 2017/10/26
 */
public class StringUtils {
	/**
	 * 将字符串数组用分隔符分割
	 * 
	 * @param strArray
	 *            -数组
	 * @param separator
	 *            -分隔符
	 * @return
	 */
	public static String join(String[] strArray, String separator) {
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < strArray.length; i++) {
			strbuf.append(separator).append(strArray[i]);
		}
		return strbuf.deleteCharAt(0).toString();
	}

	/**
	 * 是否为空 null或者长度为0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	public static String leftPad(int num, int length, char pad) {
		String format = "%" + pad + length + "d";
		return String.format(Locale.CHINA, format, num);
	}

	public static String sqliteEscape(String keyWord) {
		keyWord = keyWord.replace("/", "//");
		keyWord = keyWord.replace("'", "''");
		keyWord = keyWord.replace("[", "/[");
		keyWord = keyWord.replace("]", "/]");
		keyWord = keyWord.replace("%", "/%");
		keyWord = keyWord.replace("&", "/&");
		keyWord = keyWord.replace("_", "/_");
		keyWord = keyWord.replace("(", "/(");
		keyWord = keyWord.replace(")", "/)");
		return keyWord;
	}
}
