/**  
 * Description: <类功能描述-必填> 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */
package com.jc.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 扩展的字符串工具类 〈功能详细描述〉
 * 
 * @author chenzhao
 * @version [版本号, 2012-12-18]
 */
public class StringExtUtils {

	/**
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * 禁止实例化
	 */
	private StringExtUtils() {

	}

	/**
	 * 加密, 转成 utf-8 字符串 〈功能详细描述〉
	 * 
	 * @param key
	 * @return
	 */
	public static String encode(String key) {
		if (null == key || "".equals(key.trim())) {
			return key;
		}

		try {
			key = URLEncoder.encode(key, "utf-8");
		} catch (UnsupportedEncodingException ex) {
		}

		return key;
	}

	/**
	 * 解密, 从 utf-8 字符串 还原 〈功能详细描述〉
	 * 
	 * @param key
	 * @return
	 */
	public static String decode(String key) {
		if (null == key || "".equals(key.trim())) {
			return key;
		}

		try {
			key = URLDecoder.decode(key, "utf-8");
		} catch (UnsupportedEncodingException ex) {
		}

		return key;
	}

	/**
	 * 生成36位的uuid. 〈功能详细描述〉
	 * 
	 * @return
	 */
	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}

	/**
	 * 生成36位的uuid。字符全为大写 〈功能详细描述〉
	 * 
	 * @return
	 */
	public static String generateUUIDWithUppercase() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();

	}

	/**
	 * 判别一组String值是否都是UUID. 〈功能详细描述〉
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUUID(String... str) {
		for (int i = 0; i < str.length; i++) {
			try {
				UUID.fromString(str[i]);
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 将InputStream转换为字符串
	 * 
	 * @param is
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public static String converts(InputStream is) {
		StringBuilder sb = new StringBuilder();
		String readline = "";
		try {
			/**
			 * 若乱码，请改为new InputStreamReader(is, "GBK").
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (br.ready()) {
				readline = br.readLine();
				sb.append(readline);
			}
			br.close();
		} catch (IOException ie) {

		}
		return sb.toString();
	}

	/**
	 * 功能：把一般字串转成base64字串
	 * 
	 * @param str
	 *            String
	 * @return String 成功把str转换成base64编码,失败返回null
	 */
	public static String string2Base64(String str) {
		if (str.length() == 0)
			return str;

		BASE64Encoder base64en = new BASE64Encoder();
		String rt = base64en.encodeBuffer(str.getBytes());
		return rt.replaceAll("\r", "").replaceAll("\n", "");

	}

	/**
	 * 将字符串以指定的字符集BASE64编码
	 * 
	 * @param str
	 * @param charSetName
	 * @return
	 */
	public static String string2Base64(String str, String charSetName) {
		if (str.length() == 0)
			return str;

		BASE64Encoder base64en = new BASE64Encoder();
		String rt = null;
		try {
			rt = base64en.encodeBuffer(str.getBytes(charSetName));
			rt = rt.replaceAll("\r", "").replaceAll("\n", "");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return rt;
	}

	/**
	 * 功能：把base64字串还原
	 * 
	 * @param base64str
	 *            String
	 * @return String 成功还原，失败返回null
	 */
	public static String base642String(String base64str) {
		BASE64Decoder base64de = new BASE64Decoder();
		if (base64str.length() == 0)
			return base64str;

		try {
			return new String(base64de.decodeBuffer(base64str));
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * 将base64编码字符串还原，并以指定字符集识别。
	 * 
	 * @param base64str
	 * @param charSetName
	 * @return
	 */
	public static String base642String(String base64str, String charSetName) {
		BASE64Decoder base64de = new BASE64Decoder();
		if (base64str.length() == 0)
			return base64str;

		try {
			return new String(base64de.decodeBuffer(base64str), charSetName);
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * 判断字符串中是否包含指定子串，在判断是将忽略大小写。<br>
	 * 例如，containsIgnoreCase("Hello world", "hello")将返回true
	 * 
	 * @param str
	 * @param subStr
	 * @return
	 */
	public static boolean containsIgnoreCase(String str, String subStr) {
		return str.toUpperCase().indexOf(subStr.toUpperCase()) > -1;
	}

	/**
	 * 使用传入的参数替换掉字符串中包含的形如{n}的字符串。例如对于“测试{0}和{1}”， 调用该方法format("测试{0}和{1}", "A",
	 * "B")将会返回“测试A和B”。
	 * 
	 * @param value
	 * @param parameters
	 *            为了保证结果的正确性，请注意参数值的顺序
	 * @return
	 */
	public static String format(String value, Object... parameters) {
		Pattern p = Pattern.compile("\\{\\d\\}");
		Matcher m = p.matcher(value);
		int i = 0;
		while (m.find())
			value = value.replace(m.group(), (String) parameters[i++]);

		return value;
	}

	/**
	 * 如果该参数字符串不为null，长度大于0，且至少包含一个non-whitespace字符，那么该方法返回true
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be <code>null</code>
	 * @return <code>true</code> if the String is not null, length > 0, and not
	 *         whitespace only
	 */
	public static boolean hasText(String str) {
		if (str == null)
			return false;

		str = str.trim();
		int strLen;
		if ((strLen = str.length()) == 0)
			return false;

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(int[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (int i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(Integer[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (Integer i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(double[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (double i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(long[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (long i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(float[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (float i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(short[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (short i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String join(char[] array, String delim) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer s = new StringBuffer();
		for (char i : array) {
			s.append(delim + i);
		}

		return s.substring(delim.length());
	}

	/**
	 * 将数组元素拼装成字符串，每个元素以指定的<code>delim</code>分隔。
	 * 
	 * @param array
	 * @param delim
	 * @param addSingleQuote
	 *            是否将每个数组元素以单引号包裹
	 * @return
	 */
	public static String join(String[] array, String delim,
			boolean addSingleQuote) {
		if (array.length == 0) {
			return EMPTY_STRING;
		}

		StringBuffer buf = new StringBuffer();
		for (String s : array) {
			if (addSingleQuote) {
				s = "'" + s + "'";
			}

			buf.append(delim + s);
		}

		return buf.substring(delim.length());
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param jsonString
	 * @return
	 */
	public static JSONObject parseJson(String jsonString) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}

		if (!JSONUtils.mayBeJSON(jsonString)) {
			return null;
		}
		try {
			return JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符"<",">"转义为"&lt;","&gt;"
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeIllegalChars(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}

		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * 去除给定内容中的换行、HTML标记、空格、script，style以及首尾空格转义符;
	 * 
	 * @param richText
	 * @return
	 */
	public static String removeHTML(String richText) {
		if (StringUtils.isBlank(richText))
			return richText;

		return richText
				.replaceAll("[\\r\\n\\s]+", "")
				.replaceAll(
						"(?i)<(script|style)[^>]*>([\\S\\s]*?)</(script|style)>",
						"")
				.replaceAll("(?i)<img[^<>]*/?>([\\S\\s]*?)(</img>)?", "")
				.replaceAll("</?[^<>]+/?>", "")
				.replaceAll("(^(&nbsp;)*)|((&nbsp;)*$)", "");
	}

	/**
	 * 去除给定内容中包含的script代码，以及CSS注入
	 * 
	 * @param richText
	 * @return
	 */
	public static String removeScripts(String richText) {
		if (StringUtils.isBlank(richText))
			return richText;

		return richText
				.replaceAll(
						"(?i)<(script|style|link|meta)[^>]*/?>([\\S\\s]*?)(</(script|style|link|meta)>)?",
						"");
	}

	/**
	 * 取得给定内容中包含的图片URL地址，并用逗号隔开
	 * 
	 * @param html
	 * @return
	 */
	public static String parseImageUrl(String html) {
		StringBuffer imgUrls = new StringBuffer();
		Pattern p = Pattern
				.compile("(?i)<img([^<>]+)src=['\"]?([^<>'\"]+[^/'\"]?)['\"]?([^<>]*)/?>([\\S\\s]*?)(</img>)?");
		Matcher m = p.matcher(html);
		while (m.find()) {
			imgUrls.append("," + m.group(2));
		}

		if (imgUrls.length() > 0) {
			return imgUrls.substring(1);
		}

		return StringUtils.EMPTY;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param value
	 * @param regexDelim
	 * @param a
	 * @return
	 */
	public static int[] toArray(String value, String regexDelim, int[] a) {
		if (StringUtils.isBlank(value)) {
			return new int[0];
		}

		String[] array = value.split(regexDelim);
		if (a == null || array.length > a.length) {
			a = new int[array.length];
		} else {
			a = ArrayUtils.subarray(a, 0, array.length);
		}

		for (int i = 0; i < array.length; i++) {
			a[i] = Integer.parseInt(array[i]);
		}

		return a;
	}

	/**
	 * 替换字符串中的换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeLineBreak(String str) {
		if (StringUtils.isNotBlank(str))
			str = str.replaceAll("\r", "").replaceAll("\n", "");
		return str;
	}

	/***
	 * 判断字符串是否数字
	 * 
	 * @param str
	 * @author huangshaokang
	 * @return
	 */
	public static boolean isNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/***
	 * 判断字符串是否包含中文
	 * 
	 * @param str
	 * @author chenzhao
	 * @return
	 */
	public static boolean isContainsChinese(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static void main(String[] args) {
		String s = "&nbsp;&nbsp;&nbsp;<p>sdssdfsdf</p>&nbsp;&nbsp;<p>说颠三倒四的</p><SCRIPT type='text/javascript'>alert(123)</SCRIPT>"
				+ "<img src='http://travel.woyo.com/guide/123.jpg'>&nbsp;&nbsp;&nbsp;等答复<IMG src='http://travel.woyo.com/guide/123.jpg'/>"
				+ "法国风格<IMG height=\"123\" src=\"http://travel.woyo.com/guide/123.jpg\" width=\"456\"></img>方法复&nbsp;&nbsp;&nbsp;23<script>alert(123)</script>是滴"
				+ "<style type=\"text/css\">body{color:red}</style>123"
				+ "&lt;script type='text/vb'&gt;思考即将开始打哈哈及&lt;/script&gt;&lt;script type='text/vb'/&gt;大风风火火<script type=\"text/cdf\"/>大家看看里";
		s = "<script type=\"text/cdf\"/>大家看看里<meta>1234</meta><meta http-equiv=\"pragma\" content=\"no-cache\">大点的<link rel=\"stylesheet\" href=\"css/common.css\" type=\"text/css\" media=\"screen\" />覆盖该覆盖";
		//

		String abc = "aaaa/bbbb/cccc";

	}

}
