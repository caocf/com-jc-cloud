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
package com.jc.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;

/**
 * 防止网站脚本注入攻击 〈功能详细描述〉
 * 
 * @author chenzhao
 * @version [版本号, 2012-12-17]
 */
public class CommonsXSSUtil {

	/**
	 * 對腳本注入判斷的正則表達式
	 */
	private static final Pattern SCRIPT_TAG_PATTERN = Pattern.compile(
			"<script[^>]*>.*</script[^>]*>", Pattern.CASE_INSENSITIVE);

	/**
	 * 脚本注入 另外情况考虑
	 */
	private static final String[] ILLEGAL_STRING_ARRAY = { "javascript",
			"JAVASCRIPT",
			"j\r\na\r\nv\r\n\r\na\r\ns\r\nc\r\nr\r\ni\r\np\r\nt\r\n" };

	/**
	 * html脚本注入大全
	 */
	private static final String[] ILLEGAL_HTML_STRING_ARRAY = { "<!-->",
			"<!DOCTYPE>", "<a>", "<abbr>", "<acronym>", "<address>",
			"<applet>", "<area>", "<b>", "<base>", "<basefont>", "<bdo>",
			"<big>", "<blockquote>", "<body>", "<br>", "<button>", "<caption>",
			"<center>", "<cite>", "<code>", "<col>", "<colgroup>", "<dd>",
			"<del>", "<dfn>", "<dir>", "<div>", "<dl>", "<dt>", "<em>",
			"<fieldset>", "<font>", "<form>", "<frame>", "<frameset>",
			"<head>", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<hr>",
			"<html>", "<i>", "<iframe>", "<img>", "<input>", "<ins>", "<kbd>",
			"<label>", "<legend>", "<li>", "<link>", "<map>", "<menu>",
			"<meta>", "<noframes>", "<noscript>", "<object>", "<ol>",
			"<optgroup>", "<option>", "<p>", "<param>", "<pre>", "<q>", "<s>",
			"<samp>", "<script>", "<select>", "<small>", "<span>", "<strike>",
			"<strong>", "<style>", "<sub>", "<sup>", "<table>", "<tbody>",
			"<td>", "<textarea>", "<tfoot>", "<th>", "<thead>", "<title>",
			"<tr>", "<tt>", "<u>", "<ul>", "<var>", "<blink>" };

	/**
	 * sql脚本注入大全
	 */
	private static final String[] ILLEGAL_SQL_STRING_ARRAY = { "%", "%_",
			"%_%", "_%", "||", "$", "&", "|", "\'", "\"", "_" };// "_", 暂时不用这个

	/**
	 * 链接 還有其他的正則表達式 ① <a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>])\\s* ② <a
	 * href='(.+?)'
	 */
	private static final Pattern HREF_TAG_PATTERN = Pattern.compile(
			"<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>])\\s*",
			Pattern.CASE_INSENSITIVE);

	/**
     */
	private static final PatternCompiler pc = new Perl5Compiler();

	/**
     */
	private static final PatternMatcher matcher = new Perl5Matcher();

	/**
	 * 过滤非法脚本 例如，<script>fdafa</script> 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String filterXSS(String content) throws Exception {
		String old = content;
		String ret = _filterXSS(content);
		while (!ret.equals(old)) {
			old = ret;
			ret = filterXSS(ret);
		}
		return ret;
	}

	/**
	 * 判断非法sql脚本字符 〈功能详细描述〉
	 * 
	 * @param content
	 * @return true : 是非法注入字符; false : 是正常的传输字符
	 * @throws Exception
	 */
	public static boolean isIllegalSqlInject(String content) throws Exception {

		boolean returnBool = false;

		if (content != null && !"".equals(content)) {

			for (String s : ILLEGAL_SQL_STRING_ARRAY) {

				if (content.contains(s)) {

					returnBool = content.contains(s);
					return returnBool;
				}
			}
		}

		return returnBool;
	}

	/**
	 * 过滤链接符和其他非法注入的html标签
	 * 
	 * @param content
	 * @param isNeedFilterHtml
	 *            - true : 需要对html代码脚本进行过滤; false : 不需要对html代码脚本进行过滤
	 * @return
	 * @throws Exception
	 */
	public static String filterXSSHrefAndOtherIllegal(String content,
			boolean isNeedFilterHtml) throws Exception {

		Matcher matcher = null;
		String temp = "";

		try {
			matcher = HREF_TAG_PATTERN.matcher(content);
			temp = content;

			/**
			 * 链接注入进行过滤
			 */
			while (matcher.find()) {
				String replaceString = replaceString(temp);

				temp = temp.replaceAll(replaceString, "");
			}

			/**
			 * 对html代码注入进行过滤
			 */
			if (isNeedFilterHtml) {
				temp = replaceHtmlString(temp);
			}

		} catch (PatternSyntaxException e) {
			throw new Exception(e.toString());
		}

		return temp;

	}

	/**
	 * 处理html代码注入问题 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceHtmlString(String content) {

		String replaceString = "";

		if (content != null && !"".equals(content)) {

			replaceString = content;

			for (String s : ILLEGAL_HTML_STRING_ARRAY) {

				replaceString = replaceString.replaceAll(s, "");
			}

		}

		return replaceString;
	}

	/********************************************************************** 私有方法处理 集结 *************************************************************************************/

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 */
	private static String _filterXSS(String content) {
		try {
			return stripAllowScriptAccess(stripProtocol(stripCssExpression(stripAsciiAndHex(stripEvent(stripScriptTag(content))))));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 */
	private static String stripScriptTag(String content) {
		Matcher m = SCRIPT_TAG_PATTERN.matcher(content);
		content = m.replaceAll("");
		return content;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String stripEvent(String content) throws Exception {
		String[] events = { "onmouseover", "onmouseout", "onmousedown",
				"onmouseup", "onmousemove", "onclick", "ondblclick",
				"onkeypress", "onkeydown", "onkeyup", "ondragstart",
				"onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
				"onrowexit", "onselectstart", "onload", "onunload",
				"onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
				"onscroll", "oncontextmenu" };
		for (String event : events) {
			org.apache.oro.text.regex.Pattern p = pc.compile("(<[^>]*)("
					+ event + ")([^>]*>)", Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (null != p)
				content = Util.substitute(matcher, p, new Perl5Substitution(
						"$1" + event.substring(2) + "$3"), content,
						Util.SUBSTITUTE_ALL);

		}
		return content;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String stripAsciiAndHex(String content) throws Exception {
		// filter &# \00xx
		org.apache.oro.text.regex.Pattern p = pc.compile(
				"(<[^>]*)(&#|\\\\00)([^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
			content = Util
					.substitute(matcher, p, new Perl5Substitution("$1$3"),
							content, Util.SUBSTITUTE_ALL);
		return content;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String stripCssExpression(String content) throws Exception {
		org.apache.oro.text.regex.Pattern p = pc.compile(
				"(<[^>]*style=.*)/\\*.*\\*/([^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
			content = Util
					.substitute(matcher, p, new Perl5Substitution("$1$2"),
							content, Util.SUBSTITUTE_ALL);

		p = pc.compile(
				"(<[^>]*style=[^>]+)(expression|javascript|vbscript|-moz-binding)([^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p) {
			content = Util
					.substitute(matcher, p, new Perl5Substitution("$1$3"),
							content, Util.SUBSTITUTE_ALL);
		}

		p = pc.compile("(<style[^>]*>.*)/\\*.*\\*/(.*</style[^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
			content = Util
					.substitute(matcher, p, new Perl5Substitution("$1$2"),
							content, Util.SUBSTITUTE_ALL);

		p = pc.compile(
				"(<style[^>]*>[^>]+)(expression|javascript|vbscript|-moz-binding)(.*</style[^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
			content = Util
					.substitute(matcher, p, new Perl5Substitution("$1$3"),
							content, Util.SUBSTITUTE_ALL);
		return content;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String stripProtocol(String content) throws Exception {
		String[] protocols = { "javascript", "vbscript", "livescript",
				"ms-its", "mhtml", "data", "firefoxurl", "mocha" };
		for (String protocol : protocols) {
			org.apache.oro.text.regex.Pattern p = pc.compile("(<[^>]*)"
					+ protocol + ":([^>]*>)",
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (null != p)
				content = Util.substitute(matcher, p, new Perl5Substitution(
						"$1/$2"), content, Util.SUBSTITUTE_ALL);
		}
		return content;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String stripAllowScriptAccess(String content)
			throws Exception {
		org.apache.oro.text.regex.Pattern p = pc.compile(
				"(<[^>]*)AllowScriptAccess([^>]*>)",
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
			content = Util.substitute(matcher, p, new Perl5Substitution(
					"$1Allow_Script_Access$2"), content, Util.SUBSTITUTE_ALL);
		return content;
	}

	/**
	 * 单独处理链接字符 〈功能详细描述〉
	 * 
	 * @param content
	 * @return
	 */
	private static String replaceString(String content) {

		String replaceString = "";

		if (content != null && !"".equals(content)) {

			if (content.indexOf("<a") != -1
					&& (content.indexOf("</a>") != -1 || content.indexOf("/>") != -1)
					|| content.indexOf("<a>") != -1) {
				int start = content.indexOf("<a");
				int end = 0;

				int temp_end_1 = content.indexOf("</a>");
				int temp_end_2 = content.indexOf("/>");
				int temp_end_3 = content.indexOf("<a>");

				if (temp_end_1 != -1 && temp_end_2 != -1) {
					end = temp_end_1 < temp_end_2 ? temp_end_1 + 4
							: temp_end_2 + 2;
				} else if (temp_end_1 != -1 && temp_end_2 == -1) {
					end = temp_end_1 + 4;
				} else if (temp_end_2 != -1 && temp_end_1 == -1) {
					end = temp_end_2 + 2;
				} else if (temp_end_3 != -1) {
					end = temp_end_3 + 3;
				}

				replaceString = content.substring(start, end);
			}

		}

		return replaceString;
	}

}
