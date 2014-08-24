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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片缩略图工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class SmallImgUtils {

    /**
     * 生成缩略图并补白
     * 〈功能详细描述〉
     *
     * @param origImg
     * @param targetFile
     * @param rectangle
     * @param isBgBlank
     * @throws Exception


     */
    public static void getSmallImge(File origImg,String targetFile,Rectangle rectangle,Boolean isBgBlank) throws Exception {
        String fileType = "JPEG";
        BufferedImage srcImage = ImageIO.read(origImg);//源文件流
        if (origImg.getName().toLowerCase().endsWith(".png")) {
            fileType = "PNG";
        }
        if ((rectangle.getWidth() > 0 || rectangle.getHeight() > 0) && 
                (srcImage.getWidth() > rectangle.getWidth()) || (srcImage.getHeight() > rectangle.getHeight())) {
            srcImage = resize(srcImage, (int)rectangle.getWidth(),(int) rectangle.getHeight());
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream(50);//获取精确的图片大小
        output.toByteArray();
        ImageIO.write(srcImage, fileType, output);//TODO 修改为OutputStream
        byte[] stream = output.toByteArray();//生成缩略图
        if (isBgBlank) {//如果补白
            stream =  addBlankBg(output.toByteArray(),fileType, rectangle).toByteArray();
        }
        FileOutputStream o = new FileOutputStream(new File(targetFile));
        o.write(stream);
        o.flush();
        o.close();
    }
    
    /**
     * 通过源文件流-生成缩略图并补白
     * 〈功能详细描述〉
     *
     * @param origImg
     * @param fileName
     * @param rectangle
     * @param isBgBlank
     * @return
     * @throws Exception


     */
    public static InputStream getSmallImge(InputStream origImg,String fileName,Rectangle rectangle,Boolean isBgBlank) throws Exception {
        String fileType = "JPEG";
        
//        BufferedInputStream bis = null;  
//        FileOutputStream fos = null;    
//        int BUFFER_SIZE = 1024;
//        byte[] buf = new byte[BUFFER_SIZE];   
//        int size = 0;    
//        while ( (size = origImg.read(buf)) != -1)    
//            fos.write(buf, 0, size);      
//            fos.close();   
//            bis.close();   
//       } 
    
        
        BufferedImage srcImage = ImageIO.read(origImg);//源文件流
        
        if (fileName.toLowerCase().endsWith(".png")) {
            fileType = "PNG";
        }
        if (rectangle!=null&&((rectangle.getWidth() > 0 || rectangle.getHeight() > 0) && 
                (srcImage.getWidth() > rectangle.getWidth()) || (srcImage.getHeight() > rectangle.getHeight()))) {
            srcImage = resize(srcImage, (int)rectangle.getWidth(),(int) rectangle.getHeight());
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream(50);//获取精确的图片大小
        output.toByteArray();
        ImageIO.write(srcImage, fileType, output);//TODO 修改为OutputStream
        byte[] stream = output.toByteArray();//生成缩略图
        if (isBgBlank) {//如果补白
            stream =  addBlankBg(output.toByteArray(),fileType, rectangle).toByteArray();
        }
        ByteArrayInputStream input=new ByteArrayInputStream(stream);
        
        return input;
    }
    
    
    /**
     * 通过源文件-生成缩略图并补白
     * 〈功能详细描述〉
     *
     * @param origImg
     * @param rectangle
     * @param isBgBlank
     * @return
     * @throws Exception


     */
    public static InputStream getSmallImge(File origImg,Rectangle rectangle,Boolean isBgBlank) throws Exception {
        String fileType = "JPEG";
        BufferedImage srcImage = ImageIO.read(origImg);//源文件流
        if (origImg.getName().toLowerCase().endsWith(".png")) {
            fileType = "PNG";
        }
        if ((rectangle.getWidth() > 0 || rectangle.getHeight() > 0) && 
                (srcImage.getWidth() > rectangle.getWidth()) || (srcImage.getHeight() > rectangle.getHeight())) {
            srcImage = resize(srcImage, (int)rectangle.getWidth(),(int) rectangle.getHeight());
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream(50);//获取精确的图片大小
        output.toByteArray();
        ImageIO.write(srcImage, fileType, output);//TODO 修改为OutputStream
        byte[] stream = output.toByteArray();//生成缩略图
        if (isBgBlank) {//如果补白
            stream =  addBlankBg(output.toByteArray(),fileType, rectangle).toByteArray();
        }
        ByteArrayInputStream input=new ByteArrayInputStream(stream);
        
        return input;
    }
    

    /**
     * 添加白色背景
     * 〈功能详细描述〉
     *
     * @param sourceFile
     * @param fileType
     * @param rateRectangle
     * @return
     * @throws Exception


     */
    private static ByteArrayOutputStream addBlankBg(byte[] sourceFile,String fileType,Rectangle rateRectangle) throws Exception { 
        //生成白色图片
        BufferedImage bi = new BufferedImage((int)rateRectangle.getWidth(),(int)rateRectangle.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.getGraphics();
        gr.setColor(Color.white);
        gr.fillRect(0, 0, (int)rateRectangle.getWidth(),(int)rateRectangle.getHeight());
        gr.dispose();
        bi.flush();
        
        ByteArrayInputStream input=new ByteArrayInputStream(sourceFile);
        BufferedImage sImage=ImageIO.read(input);
       
        int x=((int)rateRectangle.getWidth()-sImage.getWidth())/2;
        int y=((int)rateRectangle.getHeight()-sImage.getHeight())/2;
        
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024*4);
        try {
            BufferedImage buffImg = new BufferedImage(bi.getWidth(null),bi.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(bi.getScaledInstance(bi.getWidth(null), bi.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            
            //源文件
            ImageIcon imgIcon = new ImageIcon(sImage);
            Image img = imgIcon.getImage();
            
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1));
            g.drawImage(img, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            ImageIO.write(buffImg, fileType, os);
        }catch (Exception e) {
            e.printStackTrace();
          //TODO chenzhao 需要对异常做处理
        }finally {
            try {
                if (null != os){
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
              //TODO chenzhao 需要对异常做处理
            }
        }
        return os;
    }

    /**
     * resize 方法用来设置缩略图大小
     * @param source
     * @param targetW  表示目标宽度
     * @param targetH 表示目标高度
     * @return


     */
    private static BufferedImage resize(BufferedImage source, int targetW,int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        }
        else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        }
        else
            target = new BufferedImage(targetW, targetH, type);Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }
    
    
}
