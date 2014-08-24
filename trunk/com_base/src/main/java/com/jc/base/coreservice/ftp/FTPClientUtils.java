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

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.jc.base.util.ImageMarkLogoByIcon;
import com.jc.base.util.SmallImgUtils;
import com.jc.tools.ArgumentMemoryUtils;

/**
 * 
 * FTP上传工具<br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public class FTPClientUtils {

    /**
     */
    private static final Logger logger = Logger.getLogger(FTPClientUtils.class);

    /**
     * Image Server domain
     */
    private static final String IMAGES_SERVER_HOST = "images.server.host";

    /**
     * images.server.ftp.subdir
     */
    private static final String IMAGES_SERVER_FTP_SUBDIR = "images.server.ftp.subdir";

    /**
     * ftp.server.root.dir
     */
    private static final String FTP_SERVER_ROOT_DIR = "ftp.server.root.dir";

    /**
     * 图片服务器域名
     */
    private static String domain = "";

    /**
     * 图片服务器上的FTP目录
     */
    private static String subdir = "";

    /**
     * 图片服务器根目录
     */
    private static String ftpServerRoot = "";

    /**
     * 随机字符串生成器
     */
    private static final Random random = new Random();

    /**
     * 静态初始化方法
     */
    static {
        init();
    }

    /**
     * 
     * 初始化操作 <br>
     * 
     * 
     * @see

     */
    private static void init() {
        try {

            domain = ArgumentMemoryUtils.getInstance().getValueByName(IMAGES_SERVER_HOST);
            subdir = ArgumentMemoryUtils.getInstance().getValueByName(IMAGES_SERVER_FTP_SUBDIR);
            ftpServerRoot =ArgumentMemoryUtils.getInstance().getValueByName(FTP_SERVER_ROOT_DIR);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 
     * 对外接口-将指定的文件流(InputStream对象)保存至FTP服务器指定目录
     * 
     * @param ftpDir 保存至FTP服务器上的目录
     * @param input 输入流
     * @param fileName 文件名称包括，需要包含后缀
     * @return
     * @throws IOException
     * @see

     */
    public static FTPFileItem saveToFtp(String ftpDir, InputStream input, String fileName) throws IOException {
        return saveToFtp(ftpDir, input, fileName, FTP.BINARY_FILE_TYPE);
    }

    /**
     * 将指定的输入流保存至FTP服务器(不指定目录)
     * 
     * @param input 输入流
     * @param fileName 文件名称包括，需要包含后缀
     * @return
     * @throws IOException
     * @see

     */
    public static FTPFileItem saveToFtp(InputStream input, String fileName) throws IOException {
        return saveToFtp(null, input, fileName, FTP.BINARY_FILE_TYPE);
    }

    /**
     * 
     * 对外接口1-将指定的文件(File对象)保存至FTP服务器指定目录
     * 
     * @param ftpDir 保存至FTP服务器上的目录
     * @param file 要保存的文件
     * @param fileName 文件名称包括，需要包含后缀
     * @return
     * @throws IOException
     * @see

     */
    public static FTPFileItem saveToFtp(String ftpDir, File file, String fileName) throws IOException {
        return saveToFtp(ftpDir, file, fileName, FTP.BINARY_FILE_TYPE);
    }

    /**
     * 
     * 将指定的文件保存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的目录
     * @param file 要保存的文件
     * @param fName 文件名称包括，需要包含后缀
     * @param file_type 文件类型 FTP.BINARY_FILE_TYPE FTP.ASCII_FILE_TYPE
     * @return
     * @throws FileNotFoundException
     * @see

     */
    public static FTPFileItem saveToFtp(String ftpDir, File file, String fName, int file_type)
            throws FileNotFoundException {
        return saveToFtp(ftpDir, new FileInputStream(file), fName, file_type);
    }

    /**
     * 
     * 将指定的文件保存至FTP服务器
     * 
     * @param file 要保存的文件
     * @param fileName 文件名称包括，需要包含后缀
     * @return
     * @throws IOException
     * @see

     */
    public static FTPFileItem saveToFtp(File file, String fileName) throws IOException {
        return saveToFtp(null, file, fileName, FTP.BINARY_FILE_TYPE);
    }

    /**
     * 
     * 将原文件生成指定大小的缩略图保存存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的原始文件存放目录,自动根据大小在此目录下建缩略图目录
     * @param sourceFile 原始文件
     * @param rondamName 原始文件名称
     * @param rectangle 形对象,指定生成缩略图的长和宽
     * @param waterMakerFile 水印文件
     * @return FTPFileItem
     * @throws Exception
     * @see

     */
    public static FTPFileItem saveSmallImgToFtp(String ftpDir, InputStream sourceFile, boolean copyStream,
            String rondamName, Rectangle rectangle, File waterMakerFile) throws Exception {

        InputStream smallImg = sourceFile;
        // 如果不生成缩略图片
        if (rectangle == null) {
            smallImg = sourceFile;
        } else {
            // 1.先生成缩略图,在内存中操作。
            smallImg = SmallImgUtils.getSmallImge(sourceFile, rondamName, rectangle, true);

        }

        ByteArrayOutputStream out = null;

        // 2.再添加水印
        if (waterMakerFile != null) {

            out = ImageMarkLogoByIcon.markImageByIcon(1, rondamName, smallImg, waterMakerFile.getAbsolutePath());
            byte[] by = out.toByteArray();
            out.flush();
            out.close();
            smallImg = new ByteArrayInputStream(by);
        }
        boolean isSmall = false;
        StringBuffer fname = new StringBuffer(50);
        if (rondamName != null && rectangle != null) {
            isSmall = true;
            fname.append(getFileNameWithoutExt(rondamName));
            fname.append("_");
            fname.append((int) rectangle.getWidth());
            fname.append("x");
            fname.append((int) rectangle.getHeight());
            fname.append(getFileExt(rondamName));
        } else {
            fname.append(rondamName);
        }
        // 保存至Ftp服务器
        FTPFileItem ftpfileitem = saveToFtp(ftpDir, smallImg, copyStream, fname.toString(), FTP.BINARY_FILE_TYPE,
                isSmall);
        return ftpfileitem;
    }

    /**
     * 将原文件生成指定大小的缩略图保存存至FTP服务器 ，唯一区别把有水印和无水印的图片名称区分开来
     * 
     * @param ftpDir 保存至FTP服务器上的原始文件存放目录,自动根据大小在此目录下建缩略图目录
     * @param sourceFile 原始文件
     * @param rondamName 原始文件名称
     * @param rectangle 形对象,指定生成缩略图的长和宽
     * @param waterMakerFile 水印文件
     * @return FTPFileItem
     * @throws Exception
     * @see

     */
    public static FTPFileItem saveSmallImgToFtpSubDiffImg(String ftpDir, InputStream sourceFile, boolean copyStream,
            String rondamName, Rectangle rectangle, File waterMakerFile) throws Exception {

        InputStream smallImg = sourceFile;
        // 如果不生成缩略图片
        if (rectangle == null) {
            smallImg = sourceFile;
        } else {
            // 1.先生成缩略图,在内存中操作。
            smallImg = SmallImgUtils.getSmallImge(sourceFile, rondamName, rectangle, true);

        }

        ByteArrayOutputStream out = null;

        // 2.再添加水印
        if (waterMakerFile != null) {

            out = ImageMarkLogoByIcon.markImageByIcon(1, rondamName, smallImg, waterMakerFile.getAbsolutePath());
            byte[] by = out.toByteArray();
            out.flush();
            out.close();
            smallImg = new ByteArrayInputStream(by);
        }
        boolean isSmall = false;
        StringBuffer fname = new StringBuffer(50);
        if (rondamName != null && rectangle != null) {
            isSmall = testSubName(rondamName, rectangle, waterMakerFile, fname);
        } else {
            fname.append(rondamName);
        }
        // 保存至Ftp服务器
        FTPFileItem ftpfileitem = saveToFtp(ftpDir, smallImg, copyStream, fname.toString(), FTP.BINARY_FILE_TYPE,
                isSmall);
        return ftpfileitem;
    }

    /**
     * 
     * 将原文件生成指定大小的缩略图保存存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的原始文件存放目录,自动根据大小在此目录下建缩略图目录
     * @param sourceFile 原始文件
     * @param rondamName 原始文件名称
     * @param rectangle 形对象,指定生成缩略图的长和宽
     * @param waterMakerFile 水印文件
     * @return FTPFileItem
     * @throws Exception
     * @see

     */
    public static FTPFileItem saveSmallImgToFtp(String ftpDir, File sourceFile, boolean copyStream, String rondamName,
            Rectangle rectangle, File waterMakerFile, boolean isUseOldName) throws Exception {
        InputStream smallImg = null;
        // 如果不生成缩略图片
        if (rectangle == null) {
            smallImg = new FileInputStream(sourceFile);
        } else {
            // 1.先生成缩略图,在内存中操作。
            smallImg = SmallImgUtils.getSmallImge(sourceFile, rectangle, true);

        }

        ByteArrayOutputStream out = null;

        // 2.再添加水印
        if (waterMakerFile != null) {

            out = ImageMarkLogoByIcon.markImageByIcon(1, sourceFile.getName(), smallImg,
                    waterMakerFile.getAbsolutePath());
            byte[] by = out.toByteArray();
            out.flush();
            out.close();
            smallImg = new ByteArrayInputStream(by);
        }
        boolean isSmall = false;
        StringBuffer fname = new StringBuffer(50);
        if (rondamName != null && rectangle != null) {
            if (isUseOldName) {
                isSmall = true;
            }
            fname.append(getFileNameWithoutExt(rondamName));
            fname.append("_");
            fname.append((int) rectangle.getWidth());
            fname.append("x");
            fname.append((int) rectangle.getHeight());
            fname.append(getFileExt(rondamName));
        } else if (rondamName != null && rectangle == null) {
            fname.append(rondamName);
        } else {
            fname.append(sourceFile.getName());
        }

        FTPFileItem fileItem = saveToFtp(ftpDir, smallImg, copyStream, fname.toString(), FTP.BINARY_FILE_TYPE, isSmall);

        return fileItem;
    }

    /**
     * * 将原文件生成指定大小的缩略图保存存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的原始文件存放目录,自动根据大小在此目录下建缩略图目录
     * @param file 原始文件
     * @param rectangle 形对象,指定生成缩略图的长和宽
     * @param waterMakerFile 水印文件
     * @return FTPFileItem
     */
    /*
     * public static FTPFileItem saveSmallImgToFtp(File sourceFile,Rectangle rectangle,File waterMakerFile) throws
     * Exception { return saveSmallImgToFtp(null,sourceFile,false,null,rectangle,waterMakerFile); }
     */

    /*********************************************** 以下私有方法 *********************************************/

    /**
     * 
     * 将指定的流保存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的目录
     * @param fin 输入流
     * @param fName 文件名称包括，需要包含后缀
     * @param file_type 文件类型 FTP.BINARY_FILE_TYPE FTP.ASCII_FILE_TYPE
     * @return
     * @see

     */
    private static FTPFileItem saveToFtp(String ftpDir, InputStream fin, String fName, int file_type) {
        return saveToFtp(ftpDir, fin, true, fName, file_type, false);
    }

    /**
     * 
     * 将指定的流保存至FTP服务器
     * 
     * @param ftpDir 保存至FTP服务器上的目录
     * @param fin 输入流
     * @param fName 文件名称包括，需要包含后缀
     * @param file_type 文件类型 FTP.BINARY_FILE_TYPE FTP.ASCII_FILE_TYPE
     * @return
     * @see

     */
    private static FTPFileItem saveToFtp(String ftpDir, InputStream input, boolean copyStream, String fName,
            int file_type, boolean isSmall) {

        FTPFileItem item = new FTPFileItem();
        FTPClient client = FTPClientFactory.getFTPClient();
        if (client == null || !client.isAvailable()) {
            item.setSuccessed(false);
            return item;
        }
        int DEFAULT_FILE_TYPE = file_type;
        if (file_type != FTP.ASCII_FILE_TYPE && file_type != FTP.BINARY_FILE_TYPE) {
            DEFAULT_FILE_TYPE = FTP.BINARY_FILE_TYPE;
        }
        boolean savedSucessed = false;
        ByteArrayInputStream innerCopy = null;
        ByteArrayInputStream outterCopy = null;
        try {

            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            int read = 0;
            while ((read = input.read()) != -1) {
                ba.write(read);
            }
            input.close();

            byte[] bin = ba.toByteArray();

            outterCopy = new ByteArrayInputStream(bin);
            innerCopy = new ByteArrayInputStream(bin);

            client.changeWorkingDirectory("/");
            client.setFileType(DEFAULT_FILE_TYPE);
            String fileName = fName;
            if (!isSmall) {
                fileName = randomString(25) + getFileExt(fName);
            }
            String path = createFtpDir(client, ftpDir);
            logger.debug("save to FTP fileName:" + fileName);
            savedSucessed = client.storeUniqueFile(fileName, innerCopy);
            if (savedSucessed) {
                item.setFileName(fileName);
                item.setFilePath("/" + path + fileName);
                item.setFtpBaseUrl(domain);
                item.setUrl(item.getFtpBaseUrl() + ftpServerRoot + item.getFilePath());
                item.setSuccessed(savedSucessed);
                item.setInputStream(outterCopy);
                client.changeWorkingDirectory("/");
                logger.debug("saved FTP URL:" + item.getUrl());
            } else {
                item = new FTPFileItem();
                item.setSuccessed(false);
            }
        } catch (FileNotFoundException e) {
            item = new FTPFileItem();
            item.setSuccessed(false);
            logger.error(e);
        } catch (IOException e) {
            item = new FTPFileItem();
            item.setSuccessed(false);
            logger.error(e);
        } finally {
            try {
                if (!copyStream) {
                    if (outterCopy != null) {
                        // outterCopy.close();
                    }
                }
                if (innerCopy != null) {
                    innerCopy.close();
                }
                if (client != null) {
                    client.disconnect();
                }

            } catch (IOException e) {
                // TODO 需要对异常做处理
            }
        }
        return item;
    }

    /**
     * 
     * 创建FTP目录，根据路径<br>
     * 
     * 
     * @param client
     * @param ftpDir
     * @return
     * @throws IOException
     * @see

     */
    private static String createFtpDir(FTPClient client, String ftpDir) throws IOException {
        StringBuffer path = new StringBuffer();
        if (client == null) {
            return null;
        }
        if (ftpDir == null) {
            ftpDir = "";
        }
        client.changeWorkingDirectory("/");
        String dir = subdir + "/" + ftpDir + generateDatePath();
        dir = regularPath(dir);
        String works[] = dir.split("/");
        for (int i = 0; i < works.length; i++) {
            if (works[i].trim().length() > 0) {
                boolean hasDir = client.changeWorkingDirectory(works[i]);
                if (!hasDir) {
                    client.makeDirectory(works[i]);
                    client.changeWorkingDirectory(works[i]);
                }
                path.append(works[i]);
                path.append("/");
            }
        }
        return path.toString();
    }

    private static String regularPath(String path) {
        String dir = path;
        while (dir.indexOf("\\\\") != -1) {
            dir = dir.replaceAll("\\\\", "/");
        }
        while (dir.indexOf("//") != -1) {
            dir = dir.replaceAll("//", "/");
        }
        return dir;
    }

    /**
     * 
     * 生成日期格式的文件夹 <br>
     * 
     * 
     * @return
     * @see

     */
    private static String generateDatePath() {
        Calendar now = new java.util.GregorianCalendar(Locale.CHINESE);
        StringBuffer datepath = new StringBuffer();
        datepath.append("/");
        datepath.append(now.get(Calendar.YEAR));
        datepath.append("/");
        datepath.append(now.get(Calendar.YEAR));
        datepath.append(now.get(Calendar.MONTH) + 1);
        datepath.append(now.get(Calendar.DATE));
        datepath.append("/");
        return datepath.toString();
    }

    /**
     * 
     * 获取文件扩展名，若没有扩展名，返回空字符串 <br>
     * 
     * 
     * @param fname
     * @return
     * @see

     */
    private static String getFileExt(String fname) {
        String result = "";
        if (fname == null) {
            return result;
        }
        String ext = fname;
        if (ext != null && ext.indexOf('.') != -1) {
            result = ext.substring(ext.lastIndexOf('.'));
        }
        return result;
    }

    private static String getFileNameWithoutExt(String fname) {
        String result = "";
        if (fname == null) {
            return result;
        }
        String ext = fname;
        if (ext != null && ext.indexOf('.') != -1) {
            result = ext.substring(0, ext.lastIndexOf('.'));
        } else {
            result = ext;
        }
        return result;
    }

    /**
     * 
     * 生成随机字符串 <br>
     * 
     * 
     * @param count
     * @return
     * @see

     */
    private static String randomString(int count) {
        final int end = 132;
        final int start = 32;
        StringBuffer buffer = new StringBuffer();
        int gap = end - start;
        while (count-- != 0) {
            char ch;
            ch = (char) (random.nextInt(gap) + start);
            if (Character.isLetterOrDigit(ch)) {
                buffer.append(ch);
            } else {
                count++;
            }
        }
        return buffer.toString();
    }

    /**
     * 
     * 删除指定的文件
     * 
     * @param filePath 指定文件的全路径
     * @return 如果成功删除，返回true，否则返回false。
     * @see

     */
    private static boolean deleteFileWithAbsPath(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 
     * 删除指定的目录及其下所有的对象
     * 
     * @param folderPath 指定的目录路径
     * @return 如果删除成功，返回true，否则返回false。
     * @see

     */
    private static boolean deleteTreeWithAbsPath(String folderPath) {
        File dirFile = new File(folderPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        // 首先列出该目录下的所有对象
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                // 只要有文件删除不成功，则直接返回false。
                if (!deleteFileWithAbsPath(file.getAbsolutePath())) {
                    return false;
                }
            } else if (file.isDirectory()) {
                deleteTreeWithAbsPath(file.getAbsolutePath());
            }
        }
        // 删除当前目录
        dirFile.delete();
        return true;
    }

    /**
     * 
     * 根据在FTP上的相对目录路径和文件名删除该文件
     * 
     * @param relativeDir 相对目录路径
     * @param fileName 文件名
     * @return 如果删除成功，返回true，否则返回false。
     * @see

     */
    public static boolean deleteFile(String relativeDir, String fileName) {
        if (subdir == null || "".equals(subdir)) {
            return false;
        }
        String baseDir = regularPath(subdir);
        if (relativeDir == null) {
            relativeDir = "";
        }
        relativeDir = regularPath(relativeDir);
        if (!relativeDir.endsWith("/")) {
            relativeDir = relativeDir + "/";
        }
        String absolutePath = baseDir.endsWith("/") ? baseDir + relativeDir + fileName : baseDir + "/" + relativeDir
                + fileName;
        return deleteFileWithAbsPath(absolutePath);
    }

    /**
     * 
     * 根据在FTP上的相对目录路径来删除此目录以及它下面的所有对象，这是一个递归删除的过程。
     * 
     * @param relativeDir 在FTP上的相对目录路径
     * @return 如果删除成功，返回true，否则返回false。
     * @see

     */
    public static boolean deleteTree(String relativeDir) {
        if (subdir == null || "".equals(subdir)) {
            return false;
        }
        String baseDir = regularPath(subdir);
        if (relativeDir == null) {
            relativeDir = "";
        }
        relativeDir = regularPath(relativeDir);
        String absolutePath = baseDir.endsWith("/") ? baseDir + relativeDir : baseDir + "/" + relativeDir;
        return deleteTreeWithAbsPath(absolutePath);
    }

    /******** private method ********/

    /**
     * 处理图片名称,并返回是否生成缩略图
     * 
     * @param rondamName
     * @param rectangle
     * @param waterMakerFile
     * @param fname
     * @return
     * @see

     */
    private static boolean testSubName(String rondamName, Rectangle rectangle, File waterMakerFile, StringBuffer fname) {
        boolean isSmall;
        isSmall = true;
        fname.append(getFileNameWithoutExt(rondamName));
        if (waterMakerFile != null)
            fname.append("_W");
        fname.append("_");
        fname.append((int) rectangle.getWidth());
        fname.append("x");
        fname.append((int) rectangle.getHeight());
        fname.append(getFileExt(rondamName));
        return isSmall;
    }

}