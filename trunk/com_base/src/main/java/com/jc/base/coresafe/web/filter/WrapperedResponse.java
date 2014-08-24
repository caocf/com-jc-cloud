/**
 * Description: //模块目的、功能描述 
 * Copyright:	Copyright(C), 2002-2013
 * Company:		SUNIVO
 * User:		lukejia
 * Version:		1.0
 * Create at:	13-9-23 上午11:29
 *
 * Modification History:
 * Date			User		Version		Description
 * --------------------------------------------------------
 * 13-9-23		lukejia		1.0			Initial
 */
package com.jc.base.coresafe.web.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * 功能详细描述:<br>
 * 〈功能详细描述〉
 * <p/>
 * User		lukejia
 * Version	1.0
 * Date		13-9-23
 * Since	[产品/模块版本]
 */
public class WrapperedResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream buffer = null;
	private ServletOutputStream out = null;
	private PrintWriter writer = null;

	/**
	 * Constructs a response adaptor wrapping the given response.
	 *
	 * @throws IllegalArgumentException
	 *          if the response is null
	 */
	public WrapperedResponse(HttpServletResponse response) throws IOException {
		super(response);
		buffer = new ByteArrayOutputStream();
		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	public byte[] getResponseData() throws IOException {
		//将out、writer中的数据强制输出到WrapperedResponse的buffer里面，否则取不到数据
		flushBuffer();
		String str = new String(buffer.toByteArray());
		str = this.reverseXSS(str);
		return str.getBytes();
	}

	private String reverseXSS(String str) {
		String result = str;
		if (result == null)
			return result;
		result = result.replaceAll("&#60;", "<").replaceAll("&#62;", ">");
		result = result.replaceAll("& lt;", "<").replaceAll("& gt;", ">");
		result = result.replaceAll("& #40;", "\\(").replaceAll("& #41;", "\\)");
		result = result.replaceAll("& #39;", "'");
		return result;
	}

	private class WapperedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream bos = null;
		public WapperedOutputStream(ByteArrayOutputStream stream) {
			bos = stream;
		}

		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}
	}
}
