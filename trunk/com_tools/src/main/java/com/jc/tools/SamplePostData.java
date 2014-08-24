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


import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * 模拟数据
 * 〈功能详细描述〉
 *
 * @author  chenzhao
 * @version [版本号, 2012-12-18]


 */
public class SamplePostData {

    
    /**
     */
    private static final String[] CHARS_FROM = { "&", "\"", "<", ">" };
    /**
     */
    private static final String[] CHARS_TO = { "&amp;", "&quot;", "&lt;", "&gt;" };
    /**
     * 序列号
     */
    private static final long serialVersionUID = -8568318698824941902L;
    /**
     * request
     */
    private HttpServletRequest request;

    /**
     * 构造函数
     * @param request
     */
    public SamplePostData(HttpServletRequest request)
    {
      this.request = request;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @return


     */
    public String getAllFormFieldsAndValues() {
      StringBuffer sb = new StringBuffer();

      Enumeration e = this.request.getParameterNames();
      while (e.hasMoreElements()) {
        String field = (String)e.nextElement();
        
        String fieldValue = this.request.getParameter(field);
       /* sb.append("<tr>");
        sb.append("<th style=\"vertical-align: top\">");*/
        sb.append(parse(field));
       /* sb.append("</th>");
        sb.append("<td><pre class=\"samples\">");
        sb.append(parse(fieldValue));
        sb.append("</pre></td>");
        sb.append("</tr>");*/
        sb.append(parse(fieldValue));
      }
      return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param fieldValue
     * @return


     */
    private Object parse(String fieldValue) {
      String fv = fieldValue;
      for (int i = 0; i < CHARS_FROM.length; ++i)
        fv = fv.replaceAll(CHARS_FROM[i], CHARS_TO[i]);

      return fv;
    }
    
}

