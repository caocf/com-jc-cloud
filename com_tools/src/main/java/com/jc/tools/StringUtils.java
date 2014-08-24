/**  
 * Description: <类功能描述-必填>字符串帮助类 
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import org.apache.commons.lang.ArrayUtils;



public class StringUtils  extends org.apache.commons.lang.StringUtils {
    

	
    /**
     */
    private final static String[][] SPECIAL_CHARACTER = {
        {"&#33;","!"},{"&quot;","\""},{"&#35;","#" },{"&#36;","$"},{"&#37;","%"},
        {"&amp","&"},{"&#39;","'" },{"&#40;","(" },{"&#41;",")"},{"&#42;","*"},
        {"&#43;","+" },{"&#44;","," },{"&#45;","-" },{"&#46;","."},{"&#47;","/"},
        {"&#58;",":" },{"&#59;",";" },{"&lt;","<" },{"&#61;","="},{"&gt;",">"},
        {"&#63;","?" },{"&#64;","@" },{"&#91;","[" },{"&#92;","\\"},
        {"&#93;","]" },{"&#94;","^" },{"&#96;","`" },{"&#123;","{"},{"&#124;","|"},
        {"&#125;","}" },{"&#126;","~" }
    }; 
    
 
    /**
     * 将字符串按某个分隔字符拆分后返回数组
    
     * @param value
     * @param splitter
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static String[] split2array(String value, String splitter)
    {
    	
        StringTokenizer stringtokenizer = new StringTokenizer(value, splitter);
        String as[] = new String[stringtokenizer.countTokens()];
        for (int i = 0; i < as.length; i++)
            as[i] = stringtokenizer.nextToken();
        return as;
    }


    /**
     * {方法的功能/动作描述}
    
     * @param str
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static String initialCaps(String str)
    {
        return new StringBuffer().append(Character.toUpperCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }


    public static String initialLowercase(String str)
    {
        return new StringBuffer().append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }



    /**
     * 根据输入的String返回BigDecimal，或者若String非数字串，返回null
    
     * @param str
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static java.math.BigDecimal getBigDecimal(String str)
    {
        BigDecimal bd = null;
        if (str == null)
            return null;
        try
        {
            bd = new BigDecimal(str.trim());
        }
        catch (Exception e)
        {
            return null;
        }
        return bd;
    }

  
    /**
     * 获得一个Timestamp对象，参数为时间格式为 2001-12-24
    
     * @param time
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static java.sql.Timestamp stringToTimestamp(String time)
    {

        if (time == null)
            return null;

        Timestamp timestamp = null;
        ;
        if (time != null && !"".equals(time.trim()))
        {
            String tmp = parseDateString(time);
            try
            {
                // test if time is in 'yyyy-mm-dd hh:mm:ss.xxxx' format
                timestamp = Timestamp.valueOf(tmp);
            }
            catch (Exception ex)
            {
              //TODO 
            }

            if (timestamp == null)
            {
                if (tmp.length() == 8 && tmp.indexOf('-') < 0)
                { // yyyymmdd
                    tmp = tmp.substring(0, 4) + "-" + tmp.substring(4, 6) + "-"
                            + tmp.substring(6, 8);
                }
                try
                {
                    timestamp = Timestamp.valueOf(tmp + " 00:00:00.0");
                }
                catch (Exception exc)
                {
                    // throw new Exception("时间格式错误");
                }
            }

            if (timestamp == null)
            {
                try
                {
                    // test if lcd is in lone format
                    timestamp = new Timestamp(Long.parseLong(tmp));
                }
                catch (Exception exc)
                {
                }
            }
        }
        return timestamp;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param time
     * @return


     */
    public static String parseDateString(String time)
    {
        if (time == null || time.indexOf("-") < 0 || time.length() > 10)
        {
            return time;
        }

        // only parse the yyyy-m-d, yyyy-mm-dd, yyyy-mm-d, yyyy-m-dd

        String temp = time;

        try
        {
            String year = temp.substring(0, temp.indexOf("-"));
            temp = temp.substring(temp.indexOf("-") + 1);
            String month = temp.substring(0, temp.indexOf("-"));
            if (Integer.parseInt(month) < 10)
            {
                month = StringUtils.addZero(month, 2);
            }

            String day = temp.substring(temp.indexOf("-") + 1);
            if (Integer.parseInt(day) < 10)
            {
                day = StringUtils.addZero(day, 2);
            }
            return (year + "-" + month + "-" + day);
        }
        catch (Exception ex)
        {
            return time;
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param time
     * @return


     */
    public static String timestampToString(java.sql.Timestamp time)
    {
        if (time == null)
            return "";

        String division = "-";

        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String result = "" + year;
        result += division;
        if (month < 10)
            result += "0";
        result += month;
        result += division;
        if (day < 10)
            result += "0";
        result += day;
        return result;

    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param policyNo
     * @return


     */
    public static String addZero(String policyNo)
    {
        return addZero(policyNo, 15);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @param totalSize
     * @return


     */
    public static String addZero(String str, int totalSize)
    {
        if (str == null)
            str = "";
        int length = str.length();
        for (int i = 0; i < totalSize - length; i++)
        {
            str = "0" + str;
        }
        return str;
    }

    /**
     * Translate a double value to a chinese string
     * 
     * @param dMoney
     *            double value
     * @return String if input value larger than 10^8 or small than 0.01, return ""
     *         else return a chinese string


     */
    public static String toChinese(int dMoney)
    {
        String[] strArr =
        { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] strArr1 =
        { "", "", "", "十", "百", "千", "万", "十", "百", "千" };
        String[] strArr2 = new String[10];
        String sRtn = "";
        int iTmp;
        double dTmp;

        try
        {
            dMoney += 0.001;
            if ((dMoney >= 100000000) || (dMoney < 0.01))
            {
                sRtn = "";
            }
            else
            {
                for (int i = 0; i < 10; i++)
                {
                    dTmp = dMoney / Math.pow(10, 7 - i);
                    iTmp = (new Double(dTmp)).intValue();
                    dMoney -= iTmp * Math.pow(10, 7 - i);

                    if (iTmp != 0)
                        strArr2[i] = strArr[iTmp] + strArr1[9 - i];
                    else strArr2[i] = "";

                    if ("一十".equals(strArr2[i]))
                    {
                        strArr2[i] = "十";
                    }

                }

                boolean bFlag = false;
                for (int i = 0; i < 10; i++)
                {
                    if (!"".equals(strArr2[i]))
                    {
                        sRtn += strArr2[i];
                        bFlag = true;
                    }
                    else
                    {
                        if (i == 3)
                        {
                            sRtn += "万";
                            bFlag = true;
                        }
                        else if (bFlag)
                        {
                            sRtn += "零";
                            bFlag = false;
                        }
                    }
                }

                

                if (sRtn.startsWith("万"))
                    sRtn = sRtn.substring(1, sRtn.length());
                while (sRtn.startsWith("零"))
                    sRtn = sRtn.substring(1, sRtn.length());
                if (sRtn.lastIndexOf("零") == (sRtn.length() - 1))
                    sRtn = sRtn.substring(0, sRtn.length() - 1);
                while (sRtn.startsWith("零"))
                    sRtn = sRtn.substring(1, sRtn.length());
                // sRtn += "整";
            }
        }
        catch (Exception ex)
        {
        }
        return sRtn;
    }

    /**
     * 判断字符串是否为空
     * 
     * @param s
     *            字符串
     * @return boolean


     */
    public static final boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0 ;
    }



    /**
     * 转换空字符
     * 
     * @param o
     * @return


     */
    public static String convertStringToNull(Object o)
    {
        if (o == null)
        {
            return null;
        }
        if (o instanceof String && o.equals(""))
        {
            return null;
        }
        else if (o instanceof Integer && Integer.parseInt(o.toString()) == 0)
        {
            return null;
        }
        else
        {
            return o.toString().trim();
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param o
     * @return


     */
    public static String convertNullToString(Object o)
    {
        if (o == null)
        {
            return "";
        }
        else
        {
            return o.toString().trim();
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param o
     * @param t
     * @return


     */
    public static String convertNullToString(Object o, boolean t)
    {
        if (o == null)
        {
            if (t)
            {
                return "&nbsp;";
            }
            else
            {
                return "";
            }
        }
        else
        {
            return o.toString().trim();
        }
    }

    /**
     * 截取长度－换行
     * 
     * @param str
     * @param length
     * @return


     */
    public static String formatStringInPattern(String str, int length)
    {
        if (str == null)
        {
            return "";
        }
        String s = str;
        StringBuffer value = new StringBuffer();
        value.append("");
        String temp = "";
        if (str.length() <= length)
        {
            return str;
        }
        while (s.length() > length)
        {
            temp = s.substring(0, length) + "<br>";
            s = s.substring(length);
            value.append(temp);
        }
        value.append(s);
        return value.toString();
    }

    /**
     * format the number following the pattern, eg. if the pattern is something
     * like this: "#,###,###.####", then the number 123456.789 will be formatted
     * as "123,456.7890".
     * 
     * @param pattern
     *            String
     * @param d
     *            double
     * @return String


     */
    public static String formatNumber(String pattern, double d)
    {
        NumberFormat numFormatter = new DecimalFormat(pattern);
        return numFormatter.format(d);
    }

    /**
     * format the number following the pattern, eg. if the pattern is something
     * like this: "#,###,###.####", then the number 123456.789 will be formatted
     * as "123,456.7890".
     * 
     * @param pattern
     *            String
     * @param o
     *            Object
     * @return String


     */
    public static String formatNumber(String pattern, Object o)
    {
        NumberFormat numFormatter = new DecimalFormat(pattern);
        return numFormatter.format(o);
    }

    /**
     * this function replaces any single quotation occured with in the passed in
     * string into two immediate single quotations.
     * 
     * @param toBeReplaced
     * @return


     */
    public static String escapeAmp(String toBeReplaced)
    {
        return toBeReplaced.replaceAll("\\\'", "''");
    }
    public StringUtils()
    {
    }


    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @return


     */
    public static String fromGB(String s)
    {
        return Tools.fromGB(s);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param abyte0
     * @return


     */
    public static String fromGB(byte abyte0[])
    {
        return Tools.fromGB(abyte0);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @return


     */
    public static String msNull(String s)
    {
        return Tools.msNull(s);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @return


     */
    public static String emptyStringToNull(String s)
    {
        return Tools.emptyStringToNull(s);
    }


    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @param s1
     * @param s2
     * @return


     */
    public static String replaceString(String s, String s1, String s2)
    {
        return Tools.replaceString(s, s1, s2);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param as
     * @return


     */
    public static String generateArray(String as[][])
    {
        return Tools.generateArray(as);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @return


     */
    public static boolean isNotEmpty(String s)
    {
        return s != null && s.length() > 0;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param al
     * @param s
     * @return


     */
    public static String toString(long al[], String s)
    {
        String s1 = null;
        if(al != null)
        {
            s1 = "";
            for(int i = 0; i < al.length; i++)
                if(i == al.length - 1)
                    s1 = s1 + al[i];
                else
                    s1 = s1 + al[i] + s;

        }
        return s1;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param al
     * @return


     */
    public static String toString(long al[])
    {
        return toString(al, ",");
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param s
     * @param s1
     * @return


     */
    public static int countSubstring(String s, String s1)
    {
        int i = 0;
        for(int j = 0; j >= 0;)
        {
            j = s.indexOf(s1);
            if(j >= 0)
            {
                i++;
                s = s.substring(j + s1.length(), s.length());
            }
        }

        return i;
    }

    /**
     * 将一个包含多行的字符串转换成多个字符串，每个字符串就是其中的一行
     * @param srcString 字符串
     * @param lines 行数
     * @return 字符数组，每元素就是一行


     */
    public static List readLine(String srcString) {
        BufferedReader reader = null;
        String line = null;
        List list = new ArrayList();
        try {
            try {
                if (srcString != null && srcString.trim().length() > 0) {
                    reader = new BufferedReader(new StringReader(srcString.trim()));
                    reader.ready();
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        } else {
                            list.add(line);
                        }
                        line = null;
                    }
                } else {
                    if(srcString != null){
                        list.add(srcString.trim());
                    }
                }
                return list;
            } finally {
                list = null;
                reader = null;
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * 在字符串指定位置插入一字串
     * @param srcString 源字符串
     * @param beginIndex 开始位置
     * @param content 欲插入内容
     * @return 处理后字符串


     */
    public static String insertString(String srcString, int beginIndex, String content) {
        if (beginIndex < 0) {
            throw new RuntimeException("欲插入字串的开始位置不可小于0！");
        }
        String tmp_1 = null;
        String tmp_2 = null;
        try {
            if (srcString.length() > beginIndex) {
                tmp_1 = srcString.substring(0, beginIndex);
                tmp_2 = srcString.substring(beginIndex);
                return tmp_1 + content + tmp_2;
            }
            else {
                return srcString + content;
            }
        }
        finally {
            tmp_1 = null;
            tmp_2 = null;
        }
    }
    
    /**
     * 获取指定字符串内指定字符在该字符串内的数量
     * 
     * @param srcString 指定字符串
     * @param cChar 指定字符
     * @return 指定字符在指定字符串内的数量


     */
    public static int getNumberByChar(String srcString, char cChar) {
        return getIndexsByChar(srcString, cChar).length;
    }

    /**
     * 获取指定字符串内指定字符在该字符串内的位置（存在多少个则返回多少个）
     * @param srcString 指定字符串
     * @param cChar 指定字符
     * @return 指定字符在指定字符串内的位置


     */
    public static int[] getIndexsByChar(String srcString, char cChar) {
        if (srcString == null || srcString.trim().length() == 0) {
            return new int[0];
        }
        int numIndex = 0;
        int[] aryIndex;
        int[] tmpIndex = new int[srcString.length()];
        byte[] arySrc = srcString.trim().getBytes();
        for (int i = 0; i < arySrc.length; i++) {
            if (arySrc[i] == cChar) {
                tmpIndex[numIndex] = i;
                numIndex++;
            }
        }
        aryIndex = new int[numIndex];
        for (int i = 0; i < aryIndex.length; i++) {
            aryIndex[i] = tmpIndex[i];
        }
        try{
            return aryIndex;
        }
        finally{
            aryIndex = null;
            tmpIndex = null;
            arySrc = null;
        }
    }


    /**
     * 分割字符串
     * @param srcString 源字符串
     * @param delim 分割符
     * @return 分割后的字符串


     */
    public static String[] splitString(String srcString, String delim) {
        if ((srcString.lastIndexOf(delim) + 1) == srcString.length()) {
            srcString = srcString + delim;
        }
        String sChar;
        String sValue = "";
        boolean isAdd = false;
        List list = new ArrayList();
        String[] aryReturn = null;

        for (int i = 0; i < srcString.length(); i++) {
            if (srcString.substring(i).length() < delim.length()) {
                sChar = srcString.substring(i);
            }
            else {
                sChar = srcString.substring(i, i + delim.length());
            }
            if (!sChar.equals(delim)) {
                sValue += srcString.substring(i, i + 1);
            }
            else {
                isAdd = true;
                i += delim.length() - 1;
            }
            if (i == srcString.length() - 1) {
                isAdd = true;
            }

            if (isAdd) {
                list.add(sValue);
                sValue = "";
                isAdd = false;
            }
        }
        aryReturn = new String[list.size()];
        for(int i=0;i<list.size();i++){
            aryReturn[i] = list.get(i).toString();
        }
        try{
            return aryReturn;
        }
        finally{
            sChar = null;
            sValue = null;
            list = null;
            aryReturn = null;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    /**     */
    private static Pattern patternUpper = Pattern.compile("[A-Z]+");
    /**     */
    private static Pattern patternLower = Pattern.compile("[a-z]+");
    /**     */
    private static Pattern patternDigit = Pattern.compile("[0-9]+");
    /**     */
    private static Pattern patternPunct = Pattern.compile("\\p{Punct}+");
    /**   邮箱  */
    private static Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
    /**  电话  格式 021-12345678   */
    private static  Pattern patternPhone = Pattern.compile("[0]{1}[0-9]{2,3}-[0-9]{7,8}");
    /**   手机  */
     private static  Pattern patternMobilePhone = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9,\\D])|(18[0-9]))\\d{8}$");
    /**
     * 显示金额
     * @param amount
     * @return
     */
    public static String showAmount(double amount) {
        BigDecimal a = new BigDecimal(amount);
        a = a.setScale(2, BigDecimal.ROUND_HALF_UP);
        return a.toString();
    }
    
    /**
     * 解析字符串，获取指定位置的字符串内容，转换成整形
     * 
     * @param source 源字串
     * @param start 起始位
     * @param length 长度
     * @return 内容
     */
    public static Integer parseInteger(byte[] source, Integer start, Integer length) {
        Integer target = null;
        
        String t = parseString(source, start, length);
        
        target = Integer.valueOf(trim(t));
        
        return target;
    }   
    
    /**
     * 解析字符串，获取指定位置的字符串内容，转换成浮点型
     * 
     * @param source 源字串
     * @param start 起始位
     * @param length 长度
     * @return 内容
     */
    public static BigDecimal parseBigDecimal(byte[] source, Integer start, Integer length) {
        BigDecimal target = null;
        
        String t = parseString(source, start, length);
        
        target = new BigDecimal(trim(t));
        
        return target;
    }
    
    /**
     * 解析字符串，获取指定位置的字符串内容
     * 
     * @param source 源字串
     * @param start 起始位
     * @param length 长度
     * @return 内容
     */
    public static String parseString(byte[] source, Integer start, Integer length) {
        byte[] value = new byte[length];
        System.arraycopy(source, start, value, 0, length);
        String target = new String(value);
        try {
            target = trim(new String(value, "GBK"));
        } catch (UnsupportedEncodingException e) {
          //TODO chenzhao 需要对异常做处理
            //TODO 处理异常或Log日志记录 陈钊 2011-10-28
        }
        
        return target;
    }
    
    /**
     * 获取整数的指定长度的内容
     * 右补0
     * @param source 源整数
     * @param length 长度
     * @return 内容
     */
    public static String generateNumber(BigDecimal source, Integer length) {
        return generateNumber(source, length, null);
    }
    
    /**
     * 获取浮点型的指定长度的内容
     * 右补
     * @param source 源浮点型
     * @param length 长度
     * @param scale 精度
     * @return 内容
     */
    public static String generateNumber(BigDecimal source, Integer length, Integer scale) {
        String target = null;
        BigDecimal s = source;
        if (source != null) {
            if (scale != null) {
                s = s.setScale(scale, BigDecimal.ROUND_HALF_UP);
            }
            target = generateNumber(s.toString(), length);
        } else {
            target = generateNumber("", length);
        }
        
        return target;
    }
    
    /**
     * 获指定长度的内容
     * 右补0
     * @param source 源整数
     * @param length 长度
     * @return 内容

     */
    public static String generateNumber(Object source, Integer length) {
        String target = null;
        
        if (source != null) {
            target = source.toString();
            if (target.length() > length) {
                return null;
            } else if (target.length() < length) {
                target = leftPad(target, length, '0');
            }
        } else {
            target = leftPad("", length, '0');
        }
        
        return target;
    }
    
    /**
     * 获取字符串的指定长度的内容
     * 长度不足则右补空格
     * 长度过长则从左截取
     * 
     * @param source 源字串
     * @param length 长度
     * @return 内容
     */
    public static String generateString(String source, Integer length) {
        String target = source;
        
        if (target != null) {
            if (target.length() > length) {
                target = substring(target, 0, length);
            } else if (target.length() < length) {
                target = rightPad(target, length, ' ');
            }
        } else {
            target = rightPad("", length, ' ');
        }
        
        return target;
    }
    
    /**
     * 校验url是否"/"开头 如果不是"/"开头，则在开头加上"/"
     * 
     * @param url url
     * @return url
     */
    public static String checkUrlFisrtChar(String url) {
        String uri = url;
        if (url != null && url.indexOf("/") != 0) {
            uri = "/" + uri;
        }
        return uri;
    }

    /**
     * 校验密码是否符合要求
     * 必须有大小写和数字
     * 或者标点发符号
     * 
     * @param password 密码
     * @return true-符合;false-不符合


     */
    public static Boolean checkPassword(String password) {
        
        if (password == null || password.length() < 8) {
            return Boolean.FALSE;
        }
        
        Matcher matcherUpper = patternUpper.matcher(password);  // 判断是否含有大写字符
        Matcher matcherLower = patternLower.matcher(password);  // 判断是否含有小写字符
        Matcher matcherDigit = patternDigit.matcher(password);  // 判断是否含有数字
        Matcher matcherPunct = patternPunct.matcher(password);  // 判断是否含有特殊字符
        
        if (!matcherUpper.find(0) 
                || !matcherLower.find(0) 
                || (!matcherDigit.find(0) 
                        && !matcherPunct.find(0))) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 解析路径
     * 组成路径集合
     * @param areaPath 区域路径
     * @return paths


     */
    public static List<String> parsePath(String areaPath) {
        String path = areaPath;
        List<String> paths = new ArrayList<String>();
        
        while (indexOf(path, "/") != -1) {
            paths.add(path);
            
            path = substring(path, 0, lastIndexOf(path, "/"));
        }
        
        return paths;
    }
    
    /**
     * 解析路径，获取上级路径的集合
     * 
     * @param areaPath 区域路径
     * @return paths


     */
    public static List<String> parentPath(String areaPath) {
        String path = areaPath;
        List<String> paths = new ArrayList<String>();
        paths.add("/");
        
        int pos = lastIndexOf(path, "/");
        if (pos >= 0) {
            path = substring(path, 0, pos);
        }
        
        while (indexOf(path, "/") != -1) {
            paths.add(path);
            
            path = substring(path, 0, lastIndexOf(path, "/"));
        }
        
        return paths;
    }
    
    /**
     * 获取名字集合
     * @param name 名字字符串
     * @return 名字集合


     */
    public static List<String> parseName(String name) {
        List<String> receiver = new ArrayList<String>();
        
        String[] receivers1 = split(name, ",") ;
        for (String receiver1 : receivers1) {
            String[] receivers2 = split(receiver1, ";");
            for (String receiver2 : receivers2) {
                receiver.add(receiver2);
            }
        }
        if (receiver.isEmpty()) {
            receiver.add(name);
        }
        return receiver;
    }
    
    /**
     * 异或两个自串
     * 
     * @param source 源
     * @param target 目标
     * @param length 长度
     * @return 数据


     */
    public static String xor(String pan, String pin, int length) {
        return new String(xor(pan.toCharArray(), pin.toCharArray(), length));
    }

    /**
     * 异或两个byte自串
     * 
     * @param source 源
     * @param target 目标
     * @param length 长度
     * @return 数据


     */
    public static byte[] xor(byte[] pan, byte[] pin, int length) {
        int i = 0;
        byte[] result = new byte[length];
        for (i = 0; i < length; i++) {

            result[i] = (byte) (pan[i] ^ pin[i]);

        }
        return result;
    }

    /**
     * 异或两个char数组
     * 
     * @param source 源
     * @param target 目标
     * @param length 长度
     * @return 数据


     */
    public static char[] xor(char[] pan, char[] pin, int length) {
        int i = 0;
        char[] result = new char[length];
        for (i = 0; i < length; i++) {

            result[i] = (char) (pan[i] ^ pin[i]);

        }
        return result;
    }
    
    /**
     * 压缩字符串
     * @param source 源字串
     * @return 压缩后字串
     * @throws IOException


     */
    public static String compress(String source) throws IOException {
        if (source == null || source.length() == 0) {
            return source;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(source.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * 解压字符串
     * @param target 压缩字串
     * @return 源字串
     * @throws IOException


     */
    public static String uncompress(String target) throws IOException {
        if (target == null || target.length() == 0) {
            return target;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(target
                .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }
    
     /**  
     * 压缩  
     *   
     * @param data  
     *            待压缩数据  
     * @return byte[] 压缩后的数据  


     */  
    public static byte[] compress(byte[] data) {   
        byte[] output = new byte[0];   
        
        Deflater compresser = new Deflater();   
        compresser.reset();   
       // compresser.setDictionary(dictionary.getBytes());
        compresser.setLevel(Deflater.BEST_COMPRESSION);
        compresser.setStrategy(Deflater.HUFFMAN_ONLY);
        compresser.setInput(data);
        
        compresser.finish();   
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);   
        try {   
            byte[] buf = new byte[1024];   
            while (!compresser.finished()) {   
                int i = compresser.deflate(buf);   
                bos.write(buf, 0, i);   
            }   
            output = bos.toByteArray();   
        } catch (Exception e) {   
            output = data;   
            //TODO 处理异常或Log日志记录 陈钊 2011-10-28   
        } finally {   
            try {   
                bos.close();   
            } catch (IOException e) {  
              //TODO chenzhao 需要对异常做处理
                //TODO 处理异常或Log日志记录 陈钊 2011-10-28   
            }   
        }   
        compresser.end();   
        return output;   
    }   

    /**  
     * 解压缩  
     *   
     * @param data  
     *            待压缩的数据  
     * @return byte[] 解压缩后的数据  


     */  
    public static byte[] decompress(byte[] data) {   
        byte[] output = new byte[0];   

        Inflater decompresser = new Inflater();   
        decompresser.reset();   
        decompresser.setInput(data);   

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);   
        try {   
            byte[] buf = new byte[1024];   
            while (!decompresser.finished()) {   
                int i = decompresser.inflate(buf);   
                o.write(buf, 0, i);   
            }   
            output = o.toByteArray();   
        } catch (Exception e) {   
            output = data;   
            //TODO 处理异常或Log日志记录 陈钊 2011-10-28   
        } finally {   
            try {   
                o.close();   
            } catch (IOException e) {   
              //TODO chenzhao 需要对异常做处理
                //TODO 处理异常或Log日志记录 陈钊 2011-10-28   
            }   
        }   

        decompresser.end();   
        return output;   
    } 
    
    /**
     * 判断是否全数字
     * 
     * @param str
     * @return


     */
    public static boolean isFullDigit(String str){
        Matcher matcherUpper = patternUpper.matcher(str);   // 判断是否含有大写字符
        Matcher matcherLower = patternLower.matcher(str);   // 判断是否含有小写字符
        Matcher matcherPunct = patternPunct.matcher(str);   // 判断是否含有特殊字符
        
        if (matcherUpper.find(0)||matcherLower.find(0)||matcherPunct.find(0)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
       public static String format(String origin){
           if(origin==null || origin.equalsIgnoreCase("null"))
               return "";
           return origin;
       }
       
       public static String transFromDoubleToChs(double num){
           Map<String, String> tans = new HashMap<String, String>();
           Map<Integer, String>pos = new HashMap<Integer, String>();
           tans.put("0", "零");
           tans.put("1", "一");
           tans.put("2", "二");
           tans.put("3", "三");
           tans.put("4", "四");
           tans.put("5", "五");
           tans.put("6", "六");
           tans.put("7", "七");
           tans.put("8", "八");
           tans.put("9", "九");
           pos.put(1, "");
           pos.put(2, "十");
           pos.put(3, "百");
           pos.put(4, "千");

           StringBuffer result = new StringBuffer();
           if(num==0) return "零";
           NumberFormat   formatter   =   new   DecimalFormat("0.000000000000");    
           String digit = formatter.format(num);
           
           int pIndex = digit.indexOf(".");
           String intPart = digit.substring(0, pIndex);//整数部分
           String douPart = digit.substring(pIndex+1, digit.length());//分数部分

           //处理整数部分
           int length = intPart.length();
           for(int i=0; i<length; i++){
               String val = intPart.substring(i, i+1);
               if(val.equals("0")){
                   if(i<length-1){
                       if(!intPart.substring(i+1, i+2).equals("0")){
                           result.append(tans.get("0"));
                       }
                   }
                   continue;
               }
               String scale = pos.get(length-i);
               result.append(tans.get(val));
               result.append(scale);
           }

           //处理分数部分
           length = douPart.length();
           for(int i=0; i<length; i++){
               if(douPart.charAt(douPart.length()-1)=='0'){
                   douPart = douPart.substring(0,douPart.length()-1);
               }
               else{
                   break;
               }
           }
           length = douPart.length();
           if(length == 0)return result.toString();
           if(intPart.equals("0"))result.append(tans.get("0"));
           result.append("点");
           for(int i=length; i>0; i--){
               String val = douPart.substring(length-i, length-i+1);
               result.append(tans.get(val));
            }
           
           return result.toString();
       }
       
       /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param amount
     * @return


     */
    public static String formatMoney(String amount){
           if(amount==null || amount.equals("")){
               return amount;
           }
           try{
               double damount = Double.parseDouble(amount);
               amount = showAmount(damount);
           }
           catch (Exception e){
             //TODO chenzhao 需要对异常做处理
           }
           finally{
               return amount;
           }
       }

       
       /**
        * 检查上传的Excel文件文件名是否合规
        * @param fileName
        * @return


        */
       public static int fileNameCheck(String fileName){
            if(fileName==null){
                return 1;
            }
            int position = fileName.lastIndexOf(".");
            if(position<16){
                return 1;
            }
            String preName = fileName.substring(position-12, position);
            try{
                long test = Long.parseLong(preName);
            }
            catch (Exception e){
                return 1;
            }
            String extend = fileName.substring(position+1);
            if(!extend.equalsIgnoreCase("xls")){
                return 2;
            }
            return 0;
        }
    
       

       
       
       /**
         * 判断是否电话
         * 
         * @param str
         * @return


         */
       
       public static boolean  isPhone(String phone){
         
           Matcher matcher = patternPhone.matcher(phone);
           boolean ph = matcher.matches();
           if(ph){
               return true;
           }else{
               return false;
           }
       }
       
       
       /**
         * 判断是否手机
         * 
         * @param str
         * @return


         */
       
       public static boolean isMobilePhone(String phone){
           
        
           Matcher mat = patternMobilePhone.matcher(phone);
           boolean ph = mat.matches();
           if(ph){
               return true;
           }else{
               return false;
           }
       }
       
       
       /**
        * 处理特殊字符
        * @param specialString
        * @return


        */
       public static String DealSpecialChar(String specialString){       
           if(isNotEmpty(specialString)){
               StringBuilder result= new StringBuilder();
               @SuppressWarnings("unchecked")
               Map<String, String> specialChars = ArrayUtils.toMap(SPECIAL_CHARACTER);
               for(int i=0;i<specialString.length();i++){
                   String temp = String.valueOf(specialString.charAt(i));
                   if(specialChars.containsKey(temp)){
                       result.append(specialChars.get(temp));
                   }else{
                       result.append(temp);
                   }
               }
               return result.toString();
           }else{
               return specialString;
           }           
       }
       
       
       /**给url添加指定的字符串，比如appendPiex("http://www.sunivo.com/abc.jsp","abc")
        * 返回http://www.sunivo.com/abcabc.jsp
        * 
        * @param url
        * @param piex
        * @return


        */
       public static String appendPiex(String url,String piex){
           if(isNotEmpty(url)){
               StringBuilder builder = new StringBuilder(url.substring(0, url.lastIndexOf(".")));
               String priex = url.substring(url.lastIndexOf("."));
               if(isNotEmpty(piex)){
                   builder.append(piex).append(priex);
               }
               return builder.toString();
           }
           return null;
       }
       
       /***
        * 为一个图片url添加前缀
        * 如:http://style.sunivo.com/img_uploaded/admin/upload/fp/pic/2012/2012525/JwV2DZnBjJANXjuzHLTEMcWpa.jpg
        * 添加后的地址为:
        * http://style.sunivo.com/img_uploaded/admin/upload/fp/pic/2012/2012525/prefixJwV2DZnBjJANXjuzHLTEMcWpa.jpg
        * @param url
        * @param prefix
        * @return


        */
       public static String appendPrefix(String url,String prefix){
           if(isNotEmpty(url)){
               StringBuilder builder = new StringBuilder(url.substring(0, url.lastIndexOf("/")));
               String priex = url.substring(url.lastIndexOf("/")+1,url.length());
               if(isNotEmpty(prefix)){
                   builder.append("/").append(prefix).append(priex);
               }
               return builder.toString();
           }
           return null;
       }
       
       
       static String areas[]={"台湾","海外","香港","澳门"};
       /**
        * 去除地址中的重复字段
        * @author wangwenbo
        * @param address
        * @return


        */
		public static String trimDupAdd(String address){
			if(address==null)return address;
			String result=address;
				for(int i=0;i<areas.length;i++){
					while(result.indexOf(areas[i]+areas[i])!=-1){
						result=result.replaceAll(areas[i]+areas[i], areas[i]);
					}
				}
				return result;
		}
		
       
       public static void main(String[] args) {
        
           System.out.println(appendPiex("http://www.baidu.com/wwww.jsp","_100x200"));
           
       }
       
       
       
       
       
       
      
}
