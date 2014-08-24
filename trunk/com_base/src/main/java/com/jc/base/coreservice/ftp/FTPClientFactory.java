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

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.jc.tools.ArgumentMemoryUtils;


/**
 * 
 * FTPClient pool<br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public class FTPClientFactory {

    /**
	 */
    private static final Logger logger = Logger.getLogger(FTPClientFactory.class);

    /**
     * FTP服务器IP地址
     */
    private static final String FTP_SERVER_HOSTNAME = "ftp.server.hostname";
    /**
     * FTP服务器用户名
     */
    private static final String FTP_SERVER_NAME = "ftp.server.username";

    /**
     * FTP服务器密码
     */
    private static final String FTP_SERVER_PASSWORD = "ftp.server.password";

    /**
     * 
     * 获取FTPClient连接 <br>
     * 
     * 
     * @return
     * @see

     */
    private static FTPClient getClient() {

        FTPClient client = null;
        String hostname = null, username = null, password = null;
        try {
            //ConfigurationUtils.init("/properties/systemConfig.properties");
            hostname = ArgumentMemoryUtils.getInstance().getValueByName(FTP_SERVER_HOSTNAME);
            username = ArgumentMemoryUtils.getInstance().getValueByName(FTP_SERVER_NAME);
            password = ArgumentMemoryUtils.getInstance().getValueByName(FTP_SERVER_PASSWORD);
        } catch (Exception e) {
            logger.error("FTP server configuration error, Please check configuration file:<systemConfig.properties> ");

        }
        try {
            client = new FTPClient();
            client.connect(hostname);
            client.setKeepAlive(true);
            client.login(username, password);
            client.setControlEncoding("GBK");
            int code = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(code)) {
                logger.error(client.getReplyString());
                client.disconnect();
                client = null;
            }
        } catch (SocketException ee) {
            logger.error(ee);
        } catch (IOException e) {
            logger.error(e);
        }
        return client;
    }

    /**
     * 
     * 获取FTPClient对象 <br>
     * 
     * 
     * @return
     * @see

     */
    public static FTPClient getFTPClient() {
        FTPClient client = null;
        try {
            client = getClient();
        } catch (Exception e) {
            logger.error(e);
        }
        return client;

    }

}
