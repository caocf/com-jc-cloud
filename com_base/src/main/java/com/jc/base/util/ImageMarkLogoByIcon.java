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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
/**
 * 原始图片添加水印
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class ImageMarkLogoByIcon {
	private static final Logger logger = Logger.getLogger(ImageMarkLogoByIcon.class);// 日志记录器
    /**
     * 水印图片坐标（横坐标）相对于背景图片右下角位置，单位像素
     */
    private static final int coordinate_x=20;
    /**
     * 水印图片坐标（纵坐标）相对于背景图片右下角位置，单位像素
     */
    private static final int coordinate_y=20;
    
    /**
     * 给指定的文件 添加水印,返回处理后流
     * 〈功能详细描述〉
     *
     * @param alpha
     * @param fileNameWithExt
     * @param imageStream
     * @param absoluteIconPath
     * @return
     * @throws Exception


     */
    public static ByteArrayOutputStream markImageByIcon(float alpha,String fileNameWithExt,InputStream imageStream, String absoluteIconPath)
            throws Exception {
        if (imageStream == null) {
            throw new Exception("找不到资源图片");
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream(1024*10);
        try {
            Image srcImg = ImageIO.read(imageStream);
            
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);

            String imgType=fileNameWithExt.substring(fileNameWithExt.lastIndexOf(".")+1);
            
            BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(absoluteIconPath);
            Image img = imgIcon.getImage();
            //float alpha = 0.5f; //透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(img, (width-coordinate_x)-(img.getWidth(null)), (height-coordinate_y)-img.getHeight(null), null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            
            ImageIO.write(buffImg, imgType, os);
            
        }
        catch (Exception e) {
//replaced
        	logger.error(e);
        }
        
        return os;
    }
    
    

    /**
     * 给指定的文件 添加水印,可以指定旋转角度,返回处理后流
     * 〈功能详细描述〉
     *
     * @param alpha
     * @param ext
     * @param imageStream
     * @param path
     * @param degree
     * @return
     * @throws Exception


     */
    public static ByteArrayOutputStream markImageByIcon(float alpha,String ext,InputStream imageStream, String path,Integer degree)
            throws Exception {
        if (imageStream == null) {
            throw new Exception("找不到资源图片");
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream(1024*10);
        try {
            Image srcImg = ImageIO.read(imageStream);
            
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);

            String imgType=ext.substring(ext.lastIndexOf(".")+1);
            
            BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(path);
            Image img = imgIcon.getImage();
            //float alpha = 0.5f; //透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            if (null != degree) {   
              //设置水印旋转   
              g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);   
            }  
            
            g.drawImage(img, (width-coordinate_x)-(img.getWidth(null)), (height-coordinate_y)-img.getHeight(null), null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            
            ImageIO.write(buffImg, imgType, os);
            
        }
        catch (Exception e) {
//replaced
        	logger.error(e);
        }
        
        return os;
    }
    

    /**
     * 给指定的文件 添加水印,可以指定旋转角度,无返回值
     * 〈功能详细描述〉
     *
     * @param alpha
     * @param sourceFile
     * @param iconPath
     * @param targetPath
     * @param degree
     * @throws Exception


     */
    public static void markImageByIcon(float alpha,File sourceFile, String iconPath, String targetPath,Integer degree)
            throws Exception {
        if (sourceFile == null) {
            throw new Exception("找不到资源图片");
        }
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(sourceFile);
            
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);

            String imgType=sourceFile.getName().substring(sourceFile.getName().lastIndexOf(".")+1);
            
            BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            Image img = imgIcon.getImage();
            //float alpha = 0.5f; //透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            if (null != degree) {   
                //设置水印旋转   
                g.rotate(Math.toRadians(degree),(width-20)-(img.getWidth(null)), (height-20)-img.getHeight(null));   
            }  
            
            g.drawImage(img, (width-20)-(img.getWidth(null)), (height-20)-img.getHeight(null), null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targetPath);
            ImageIO.write(buffImg, imgType, os);
        }
        catch (Exception e) {
//replaced
        	logger.error(e);
        }
        finally {
            try {
                if (null != os){
                    os.close();
                }      
            }
            catch (Exception e) {
//replaced
            	logger.error(e);
            }
        }
    }


}
