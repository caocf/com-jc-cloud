package com.jc.tools;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * @category 2013.7.17
 * @author chenzhao
 * 
 */
//@Component("ftpFileUtls")
public class FTPFileUtils {
	static Logger logger = Logger.getLogger(FTPFileUtils.class);
	private static String userName; // FTP 登录用户名
	private static String password; // FTP 登录密码
	private static String ip; // FTP 服务器地址IP地址
	private static String  port; // FTP 端口
	private static FTPClient ftpClient = null; // FTP 客户端代理
	// 时间格式化
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");
	// FTP状态码
	public static int i = 1;
	
	public FTPFileUtils(){}

	public FTPFileUtils(String username, String password, String ip, String port) {
		super();
		this.userName = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		connectServer();
	}

	/**
	 * 上传文件
	 * 
	 * @param localFilePath
	 *            本地文件路径及名称(F:\cms\gg.txt)
	 * @param remoteFileName
	 *            FTP 服务器文件名称(gg2.txt)
	 * @param remoteDir
	 *            FTP 服务器目录(/text) 如果ftp服务器text目录不存在，将会自动创建
	 * @return
	 */
	public static boolean uploadFile(String localFilePath,
			String remoteFileName, String remoteDir) {
		openFtpConnection();
		makeDirs(remoteDir);
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			inStream = new BufferedInputStream(new FileInputStream(
					localFilePath));
			changeWorkingDirectory(remoteDir);
			success = ftpClient.storeFile(remoteFileName, inStream);
		} catch (FileNotFoundException e) {
			logger.error("文件未找到", e);
		} catch (IOException e) {
			logger.error("IO异常", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					logger.error("IO异常", e);
				}
			}
		}
		return success;
	}

	/**
	 * 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录
	 * 
	 * @param remoteDir
	 *            ftp目录
	 * @return 状态值
	 */
	public static boolean makeDirs(String remoteDir) {
		openFtpConnection();
		boolean flag = false;
		String dirs[] = remoteDir.split("/");
		String parentName = "";
		for (int i = 0; i < dirs.length; i++) {
			if (i + 1 != dirs.length) {
				if (i == 0) {
					flag = isExists(dirs[i + 1], "/");
					parentName = "/";
					isCreateDir(flag, dirs[i + 1], parentName);
				} else {
					flag = isExists(dirs[i + 1], parentName + dirs[i]);
					parentName += dirs[i] + "/";
					isCreateDir(flag, dirs[i + 1], parentName);
				}
			}
		}
		return true;
	}

	/**
	 * 代替检查目录或文件是否存在
	 * 
	 * @param dirName
	 *            本地目录名称(d:f.txt)
	 * @param remoteDir
	 *            ftp目录名称(/f)
	 * @return 状态
	 */
	public static boolean isExists(String dirName, String remoteDir) {
		openFtpConnection();
		List<String> retList = null;
		boolean falg = false;
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
			retList = new ArrayList<String>();
			if (ftpFiles == null) {
				return false;
			}
			for (int i = 0; i < ftpFiles.length; i++) {
				FTPFile file = (FTPFile) ftpFiles[i];
				if (file.isDirectory()) {
					if (dirName.equals(file.getName().toString()))
						return true;
				}
			}
		} catch (IOException e) {
			logger.debug("", e);
		}
		return falg;
	}

	/**
	 * 没有文件夹创建该文件夹
	 * 
	 * @param flag
	 * @param dirName
	 * @param remoteDir
	 */
	private static void isCreateDir(boolean flag, String dirName,
			String remoteDir) {
		if (!flag) {
			openFtpConnection();
			makeDir(dirName, remoteDir);
		}
	}

	/**
	 * 创建一个给定的新的子目录路径是在FTP服务器上的当前
	 * 
	 * @param dirPath
	 *            目录名称
	 * @param remoteDir
	 *            ftp目录
	 * @return 状态值 true 创建成功 false 创建失败
	 */
	public static boolean makeDir(String dirPath, String remoteDir) {
		openFtpConnection();
		boolean success = false;
		try {
			changeWorkingDirectory(remoteDir);
			success = ftpClient.makeDirectory(dirPath);
		} catch (IOException e) {
			logger.error("IO异常", e);
		}
		return success;
	}

	/**
	 * 下载文件(单个)
	 * 
	 * @param remoteFileName
	 *            --服务器上的文件名 (gg2.txt)
	 * @param localFileName--本地文件路径及文件名(F:/FTP/gg.txt)
	 * @param remoteDir--ftp目录（/text）
	 *            return 下载到本地的文件名（F:/FTP/66.txt）
	 */
	public String downloadFile(String remoteFileName, String localFileName,
			String remoteDir) {
		openFtpConnection();
		BufferedOutputStream buffOut = null;
		try {
			changeWorkingDirectory(remoteDir);
			buffOut = new BufferedOutputStream(new FileOutputStream(
					localFileName));
			ftpClient.retrieveFile(remoteFileName, buffOut);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (buffOut != null)
					buffOut.close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return localFileName;
	}

	/**
	 * 删除一个文件
	 * 
	 * @param remoteFileName
	 *            删除的文件名称
	 * @param remoteDir
	 *            删除文件所在服务器上的目录
	 * @return true 删除成功 false 删除失败
	 */
	public static boolean deleteFile(String remoteFileName, String remoteDir) {
		openFtpConnection();
		try {
			changeWorkingDirectory(remoteDir);
			ftpClient.deleteFile(remoteFileName);
			return true;
		} catch (IOException e) {
			logger.error("", e);
		}
		return false;
	}

	/**
	 * 删除空目录
	 * 
	 * @param remoteDir
	 *            ftp目标路径
	 * @return true 删除空目录成功 false 删除失败
	 */
	public static boolean deleteEmptyDirectory(String remoteDir) {
		openFtpConnection();
		boolean falg = false;
		try {
			ftpClient.removeDirectory(remoteDir);
			falg = true;
		} catch (IOException e) {
			logger.debug("", e);
		}
		return falg;

	}

	/**
	 * 删除目录(及目录下所有文件和子目录)
	 * 
	 * @param path
	 *            ftp删除目录路径
	 * @return true 删除成功 false 删除失败
	 */
	public static boolean removeDirectory(String path) {
		openFtpConnection();
		boolean flag = false;
		try {
			FTPFile[] ftpFileArr = ftpClient.listFiles(path);
			if (ftpFileArr == null || ftpFileArr.length == 0) {
				return deleteEmptyDirectory(path);
			}
			for (FTPFile ftpFile : ftpFileArr) {
				String name = ftpFile.getName();
				if (".".equals(name) || "..".equals(name)) {
					continue;
				}
				if (ftpFile.isDirectory()) {// 文件夹递归删除
					removeDirectory(path + "/" + name);
				} else {
					deleteFile(name, path);
				}
			}

			flag = ftpClient.removeDirectory(path);
		} catch (IOException e) {
			logger.debug("", e);
		}
		return flag;
	}

	/**
	 * 检查目录(或文件)是否存在
	 * 
	 * @param path
	 *            （/f/text）
	 * @return
	 */
	public boolean existDirectory(String path) {
		openFtpConnection();
		boolean flag = false;
		try {
			FTPFile[] ftpFileArr = ftpClient.listFiles(path);
			for (FTPFile ftpFile : ftpFileArr) {
				if (ftpFile.isDirectory()
						&& ftpFile.getName().equalsIgnoreCase(path)) {
					flag = true;
					break;
				}
			}
			return flag;
		} catch (IOException e) {
			logger.debug("", e);
		}
		return flag;
	}

	/**
	 * 列出当前工作目录下所有文件
	 * 
	 * @param regStr
	 *            ftp目录
	 * 
	 */
	public static String[] listRemoteFiles(String regStr) {
		connectServer();
		String[] files = null;
		try {
			changeWorkingDirectory(regStr);
			files = ftpClient.listNames();
			if (files == null)
				return null;
			else {
				return files;
			}
		} catch (Exception e) {
			logger.debug("列出服务器上文件和目录失败!", e);
		}
		return files;
	}

	/**
	 * 列出ftp跟目录下所有文件 默认 ftp的根路径
	 */
	public static String[] listRemoteAllFiles() {
		openFtpConnection();
		String[] names = null;
		try {
			names = ftpClient.listNames();
		} catch (Exception e) {
			logger.debug("列出Ftp服务器上的所有文件和目录 失败!", e);
		}
		return names;
	}

	/**
	 * 返回 ftp路径
	 * 
	 * @param remoteDir
	 *            ftp路径
	 * @return
	 */
	public static String getFtpPath(String remoteDir) {
		openFtpConnection();
		try {
			ftpClient.changeWorkingDirectory(remoteDir);
			return ftpClient.printWorkingDirectory();
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 返回到上一层目录
	 * 
	 * @param remoteDir
	 *            ftp路径
	 */
	public static String changeToParentDirectory(String remoteDir) {
		openFtpConnection();
		try {
			ftpClient.changeWorkingDirectory(remoteDir);
			ftpClient.changeToParentDirectory();
			return ftpClient.printWorkingDirectory();
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
		return null;
	}

	/**
	 * 检查FTP 是否关闭 ，如果关闭打开FTP
	 * 
	 * @throws Exception
	 */
	public static boolean openFtpConnection() {
		if (null == ftpClient)
			return false;
		boolean flag = true;
		try {
			if (!ftpClient.isConnected()) {
				flag = connectServer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 关闭连接
	 */
	public static void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			logger.debug("关闭ftp服务器 " + ip + " 失败，FTP服务器无法打开！", e);
		}
	}

	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 * 
	 * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
	 * 
	 */
	public static void setFileType(int fileType) {
		try {
			connectServer();
			ftpClient.setFileType(fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扩展使用
	 * 
	 * @return ftpClient
	 */
	protected static FTPClient getFtpClient() {
		connectServer();
		return ftpClient;
	}

	/**
	 * 设置参数
	 * 
	 * @param configFile
	 *            --参数的配置文件
	 */
	private static void setArg() {
		// 读取本地配置文件
		// property = new Properties();
		// BufferedInputStream inBuff = null;
		// try {
		// File file = new File(configFile);
		// inBuff = new BufferedInputStream(new FileInputStream(file));
		// property.load(inBuff);
		// userName = property.getProperty("username");
		// password = property.getProperty("password");
		// ip = property.getProperty("ip");
		// port = Integer.parseInt(property.getProperty("port"));
		// } catch (FileNotFoundException e1) {
		// System.out.println("配置文件 " + configFile + " 不存在！");
		// } catch (IOException e) {
		// System.out.println("配置文件 " + configFile + " 无法读取！");
		// }
		// 从web应用中读取配置文件
		try {
			userName = ArgumentMemoryUtils.getInstance()
					.getValueByName("ftp.server.username").trim();
			password = ArgumentMemoryUtils.getInstance()
					.getValueByName("ftp.server.password").trim();
			ip = ArgumentMemoryUtils.getInstance()
					.getValueByName("ftp.server.hostname").trim();
			port = ArgumentMemoryUtils.getInstance()
					.getValueByName("ftp.server.port").trim();
		} catch (Exception e) {
			//logger.debug("配置文件出错！");
		}

	}

	/**
	 * 连接到服务器
	 * 
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	public static boolean connectServer() {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
			     setArg();//从config.properties或其他配置文件中读取ftp的链接属性
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("GBK");
				ftpClient.configure(getFtpConfig());
				ftpClient.connect(ip);
				ftpClient.login(userName, password);
				ftpClient.setDefaultPort(Integer.valueOf(port));
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(120000);

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					logger.debug("FTP 服务拒绝连接！");
					flag = false;
				}
				i++;
				logger.debug("登录ftp服务器 " + ip + " 完成！");
			} catch (SocketException e) {
				flag = false;
				logger.debug("登录ftp服务器 " + ip + " 失败,连接超时！", e);
			} catch (IOException e) {
				flag = false;
				logger.debug("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！", e);
			}
		}

		return flag;
	}

	/**
	 * 设置FTP客服端的配置--一般可以不设置
	 * 
	 * @return ftpConfig
	 */
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(
				FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}

	/**
	 * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
	 * 
	 * @param obj
	 * @return ""
	 */
	private static String iso8859togbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 进入到服务器的某个目录下
	 * 
	 * @param directory
	 */
	public static void changeWorkingDirectory(String directory) {
		try {
			connectServer();
			ftpClient.changeWorkingDirectory(directory);
		} catch (IOException ioe) {
			logger.debug("登录ftp服务器目录 " + directory + " 失败！", ioe);
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param oldFileName
	 *            --原文件名
	 * @param newFileName
	 *            --新文件名
	 */
	public static void renameFile(String oldFileName, String newFileName) {
		try {
			connectServer();
			ftpClient.rename(oldFileName, newFileName);
		} catch (IOException ioe) {
			logger.debug("", ioe);
		}
	}

	public static void main(String[] args) {
		FTPFileUtils util = new FTPFileUtils("ufy631", "c7u7f6a5", "115.29.141.145", "21");
		// String name=util.downloadFile("555.txt", "F:/FTP/66.txt", "/f");
		boolean f = false;
		f = util.removeDirectory("/chemon_zh");

		System.out.println(f);
		// String[] list=listRemoteFiles("/lixin");
		// System.out.println(list.length);
		// String d=changeToParentDirectory("/f/dd");
		// System.out.println(d);
		// System.out.println(list.size());
		// connectServer();
		// //String[] names = listRemoteAllFiles();
		// List list=getFileList("/f");
		// System.out.println(list.size());
		// // setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件cgi-bin
		// //ftplogs
		// //htdocs
		// //radf
		// //wwwlogs
		// // uploadManyFile(new File("C:\\ooo\\upx"), new File("C:\\ooo\\upx"),
//// "/admin/ttt");
		//		closeConnect();// 关闭连接 
	}

}

