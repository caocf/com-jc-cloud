/**  
 * Description: 文件上传类 
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 文件上传工具类<br> 
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [1.0, 2012-12-17]


 */
public class FileUploadUtils {

	public final static String _SUCCESS = "success";
	public final static String _MSG = "msg";
	
	public final static long BYTE_BASE = 1024;      //1M=1024KB

	/**
	 * 验证上传附件是否符合规则<br> 
	 * 〈功能详细描述〉
	 *
	 * @param request
     *            HttpServletRequest
     * @param ext
     *            上传文件的后缀限制。如 {"jpg", "jpeg", "gif", "bmp", "png"}
     * @param fileSize
     *            上传文件的大小限制
     * @return 返回map，
     *         如果失败，则返回的map对象为：{"success":boolean,"msg":String}；如果成功，则返回的map对象为
     *         ：{"success":boolean,"msg":String,"fileSet":MultipartFileSet}
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public static Map<String, Object> validateFile(HttpServletRequest request,
			String[] ext, Long fileSize) {
		return validateFile(request, null, ext, fileSize);
	}
	
    /**
     * 获取文件字节数<br> 
     * 〈功能详细描述〉
     *
     * @param file
     * @return 返回文件流的字节大小
     * @throws Exception
     * @see 
     * @since [1.0]
     */
    public static Long getFileSize(File file) throws Exception{//取得文件大小
        long s=0;
        FileInputStream fis = null;
        try{
            if (file.exists()) {
                fis = new FileInputStream(file);
                s= fis.available();
            } else {
            	if(!file.createNewFile()){
            		
            	}
            }
        }catch(Exception ex){
        	throw new Exception(ex);
        }finally{
            if(null != fis){
            	fis.close();  
            }
        }
        return s;
    }
    
    /**
     * 字节转换为千字节<br> 
     * 〈功能详细描述〉
     *
     * @param bytes
     * @return
     * @see 
     * @since [1.0]
     */
    public static long byteToKilobyte(long bytes){
    	return bytes/BYTE_BASE;
    }
    
    /**
     * 千字节转换为兆字节<br> 
     * 〈功能详细描述〉
     *
     * @param kilobyte
     * @return
     * @see 
     * @since [1.0]
     */
    public static long kilobyteToMegabyte(long kilobyte){
    	return kilobyte/BYTE_BASE;
    }
    

	/**
	 * 验证上传附件是否符合规则
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param fileFormName
	 *            页面file对象名，可有多个。如页面上有两个file对象
	 *            imageFile和txtFile，则示例参数为："imageFile,txtFile"。
	 *            若此参数为null，则直接取得表单提交的所有file对象
	 * @param ext
	 *            上传文件的后缀限制。如 {"jpg", "jpeg", "gif", "bmp", "png"}
	 * @param fileSize
	 *            上传文件的大小限制
	 * @return 返回map，
	 *         如果失败，则返回的map对象为：{"success":boolean,"msg":String}；如果成功，则返回的map对象为
	 *         ：{"success":boolean,"msg":String,"fileSet":MultipartFileSet}
     * @see 
     * @since [1.0]
	 */
	public static Map<String, Object> validateFile(HttpServletRequest request,
			String fileFormName, String[] ext, Long fileSize) {

		// see MultipartHttpServletRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		// 取得MultipartFile的集合
		Set<MultipartFile> fileSet = null;

		// 可以针对表单file对象取数据，也可以不需要数据
		if (null == fileFormName || "".equals(fileFormName.trim()))
			fileSet = getMultipartFileSet(multipartRequest);
		else {
			String[] files = fileFormName.split(",");
			fileSet = new LinkedHashSet<MultipartFile>();
			for (String s : files) {
				MultipartFile mf = multipartRequest.getFile(s);
				if (0 < mf.getOriginalFilename().length())
					fileSet.add(mf);
			}
		}

		// 上传文件为空判断
		if (null == fileSet) {
			return returnResult(false, "您没有选择任何附件");
		}

		// 判断文件后缀是否符合约束
		if (null != ext && ext.length > 0) {
			for (MultipartFile file : fileSet) {
				if (!okExt(file, ext)) {
					return returnResult(false, "可以上传的文件类型为：" + extString(ext));
				}
			}
		}

		// 判断文件大小是否符合
		if (null != fileSize && 0 != fileSize) {
			for (MultipartFile file : fileSet) {
				if (!okSize(file, fileSize)) {
					return returnResult(false, "最大上传文件大小为"
							+ sizeString(fileSize));
				}
			}
		}

		return returnResult(true, "success");
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public static String getUUIDFileNameWithoutExt() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * <pre>
	 * 当总数小于10000000的时候，分为3级目录 100(0&tilde;99) * 100 * 1000
	 * 当总数介于10000000 和 1000000000 分为4级目录 101/100(0&tilde;99) * 100 * 100 * 1000
	 * 当总数介于1000000000 和 100000000000 分为4级目录 102/100(0&tilde;99) * 100 * 100 * 100 * 1000
	 * Example:
	 * 	
	 * 
	 * 
	 * 
	 * ============= OUT ========================
	 * /23/49/983
	 * /01/00/00/000
	 * /01/00/00/00/000
	 * 	/33/03/23/00/00/34/32/32/322
	 * </pre>
	 * 
	 * @param l
	 * @return
     * @see 
     * @since [1.0]
	 */
	public static String calcPath(long l) {
		if (100000000000L <= l) {
			return calcPath(new StringBuffer(), String.valueOf(l));
		}
		if (1000000000 <= l) {
			return calcPath(11, String.valueOf(l));
		}
		if (10000000 <= l) {
			return calcPath(9, String.valueOf(l));
		} else {
			return calcPath(7, String.valueOf(l));
		}
	}

	/**
	 * <pre>
	 * 生成目录和文件名称，最外层目录为季度。
	 * 当总数小于10000000的时候，分为3级目录 100(0&tilde;99) * 100 * 1000。示例：
	 * 当总数介于10000000 和 1000000000 分为4级目录 101/100(0&tilde;99) * 100 * 100 * 1000
	 * 当总数介于1000000000 和 100000000000 分为4级目录 102/100(0&tilde;99) * 100 * 100 * 100 * 1000
	 * Example:
	 * 	
	 * 
	 * 
	 * 
	 * ============= OUT ========================
	 * /20093/23/49	 983 + random(1000)
	 * /20093/01/00/00 	 000 + random(1000)
	 * /20093/01/00/00/00	 000 + random(1000)
	 * 	/20093/33/03/23/00/00/34/32/32	 322 + random(1000)
	 * </pre>
	 * 
	 * @param l
	 * @return
     * @see 
     * @since [1.0]
	 */
	public static String[] calcPathAndFile(long l) {
		String str = calcPath(l);
		int lastIndex = str.lastIndexOf("/");
		return new String[] {
				quarter() + str.substring(0, lastIndex),
				new StringBuffer(str.substring(lastIndex + 1)).append(new Random().nextInt(1000)).toString() };
	}

	/**
	 * 获取季度，格式YYYYQ
	 * 
	 * @return
	 */
	static String quarter() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(Calendar.YEAR))
				+ (c.get(Calendar.MONTH) / 3 + 1);
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param length
	 * @param s
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	private static String calcPath(int length, String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length - s.length(); i++) {
			sb.append("0");
		}
		return calcPath(new StringBuffer(), sb.append(s).toString());
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param sb
	 * @param s
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	private static String calcPath(StringBuffer sb, String s) {
		if (s.length() <= 3) {
			return sb.append("/").append(s).toString();
		} else {
			sb.append("/").append(s.substring(0, 2));
			return calcPath(sb, s.substring(2));
		}
	}

	// String[] to String with ','
	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param exts
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	private static String extString(String[] exts) {
		StringBuffer b = new StringBuffer();
		for (String s : exts) {
			b.append(s).append(", ");
		}
		// 私有方法，因为传入的String[] 参数不为空，则可以直接去除最后一个逗号
		return b.substring(0, b.length() - 2).toString();
	}

	// live size
	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param size
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	private static String sizeString(long size) {
		int g = 1024 * 1024 * 1024;
		int m = 1024 * 1024;
		int k = 1024;

		if (size > g)
			return size / g + "G";

		if (size > m)
			return size / m + "M";

		if (size > k)
			return size / k + "K";

		return null;
	}

	/**
	 *  判断文件大小是否符合规则<br> 
	 * 〈功能详细描述〉
	 *
	 * @param file
	 * @param length
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	static boolean okSize(MultipartFile file, long length) {
		return 0 < file.getSize() && length > file.getSize();
	}

	/**
	 * 判断文件后缀是否符合规则<br> 
	 * 〈功能详细描述〉
	 *
	 * @param file
	 * @param ext
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	static boolean okExt(MultipartFile file, String[] ext) {
		String fileName = file.getOriginalFilename();
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		if (null == ext || ext.length <= 0)
			return false;
		for (int i = 0; i < ext.length; i++) {
			if ((ext[i].toLowerCase()).equals(extName))
				return true;
		}
		return false;
	}
	
	/**
	 * 判断文件格式是否符合规则<br> 
	 * 〈功能详细描述〉
	 *
     * @param fileType 要验证的文件类型
     * @param fileTypes 允许的文件类型
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public static boolean okType(String fileType,String[] fileTypes){
		
		for(String allowType : fileTypes){
			if(allowType.equalsIgnoreCase(fileType)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回结果map<br> 
	 * 〈功能详细描述〉
	 *
	 * @param success
	 * @param msg
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	private static Map<String, Object> returnResult(boolean success, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(_SUCCESS, success);
		map.put(_MSG, msg);
		return map;
	}

	/**
	 * 得到MultipartFile集合<br> 
	 * 〈功能详细描述〉
	 *
	 * @param multipartRequest
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	protected static Set<MultipartFile> getMultipartFileSet(
			MultipartHttpServletRequest multipartRequest) {
		Set<MultipartFile> fileSet = new LinkedHashSet<MultipartFile>();
		for (Iterator<?> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (0 < file.getOriginalFilename().length())
				fileSet.add(file);
		}
		return fileSet;
	}

	/**
	 * 复制文件<br> 
	 * 〈功能详细描述〉
	 *
	 * @param filefrom
	 * @param fileto
	 * @param rewrite
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public static boolean copyFile(File filefrom, File fileto, boolean rewrite) {
		if (!filefrom.exists()) {
			return false;
		}
		if (!filefrom.isFile()) {
			return false;
		}
		if (!filefrom.canRead()) {
			return false;
		}
		if (fileto.exists() && rewrite) {
			fileto.delete();
		}
		if (!fileto.getParentFile().exists()) {
			fileto.getParentFile().mkdirs();
		}
		if (!fileto.exists()) {
			try {
				fileto.createNewFile();
			} catch (Exception ex) {
				return false;
			}
		}
		if (!fileto.canWrite()) {
			return false;
		}
		try {
			FileInputStream fosfrom = new FileInputStream(filefrom);
			FileOutputStream fosto = new FileOutputStream(fileto);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// UploadFileUtil x = new UploadFileUtil();
		// System.out.println(extString(new String[] {"jpg", "jpeg", "gif",
		// "bmp", "png"}));
		// System.out.println(calcPath(new StringBuffer(),
		// 3303230000343232322L));
		// String[] s1 = calcPathAndFile(123456);
		// String[] s2 = calcPathAndFile(12);
		// String[] s3 = calcPathAndFile(0);
		// String[] s4 = calcPathAndFile(12345678);
		// String[] s5 = calcPathAndFile(12345678911L);
		// String[] s6 = calcPathAndFile(123456789101112131L);
	}

}
