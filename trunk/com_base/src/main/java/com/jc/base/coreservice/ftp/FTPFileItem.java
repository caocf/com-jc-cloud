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
package com.jc.base.coreservice.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * FTP服务器上返回的文件对象 <br> 
 *   
 *
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see  

 */

public class FTPFileItem {

    
    private byte[] input_arry = null;
    
    /**
     * 文件名称
     */
    private String fileName = "";

    /**
     * 文件路径，相对于FTP更目录
     */
    private String filePath = "";

    /**
     * HTTP URL 通过Http协议可以访问的全路径
     * 例如，http://style.sunivo.com/lvjinImgs/imgs/service
     * /201111291042481322534568719.JPG
     */
    private String url = "";

    /**
     * FTP 所在图片服务器的HTTP访问域名+FTP根目录 如:http://style.sunivo.com/sunivoImgs
     */
    private String ftpBaseUrl = "";

    public String getFtpBaseUrl() {
        return ftpBaseUrl;
    }

    public void setFtpBaseUrl(String ftpBaseUrl) {
        this.ftpBaseUrl = ftpBaseUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 是否上传成功
     */
    private boolean successed;

    public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    /**
     * 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 文件路径，相对于FTP更目录
     */
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(input_arry);
    }

    public void setInputStream(InputStream inputStream) {
    	ByteArrayOutputStream ba = new ByteArrayOutputStream();
        int read = 0;
        try {
			while ((read = inputStream.read()) != -1) {
			    ba.write(read);
			}
		} catch (IOException e) {
		  //TODO chenzhao 需要对异常做处理
		}

        input_arry = ba.toByteArray();

    }
}
