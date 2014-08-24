/**  
 * Description: <类功能描述-必填>验证码图片生成工具 
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Component;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Component("dynamicImageUtils")
public final class DynamicImageUtils {

    private static String[] chars = { "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z","上","海","春","宇" };
    private static String[] nums = { "1", "2", "3", "4", "5", "6", "7", "8","9", "0"};

    private static final int WIDTH = 200;

    private static final int HEIGHT = 100;

    private static final int NUM = 4;

    private static final int LINE = 5;

    private static Random random = new Random();

    /**
     * 生成一个随机图片 : <br>
     * 
     *
     * @return 随机字符 image随机图片
     */
    public static Map<String, BufferedImage> getDynamicImage() {
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        StringBuilder imageValue = new StringBuilder();
        // 创建一个BufferedImage
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        // 画背景色
        graphics.setColor(new Color(247,247,247));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        // 画随机字符
        for (int i = 0; i < NUM; i++) {
            graphics.setColor(getRandomColor());
            graphics.setFont(new Font(null, Font.BOLD, random.nextInt(20) + 48));
            String temp = chars[random.nextInt(chars.length)];
            imageValue.append(temp);
            graphics.drawString(temp, WIDTH / NUM * (i), HEIGHT - 40);
        }
        // 画干扰线
        for (int j = 0; j < LINE; j++) {
            graphics.setColor(getRandomColor());
            graphics.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
                    random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }
        map.put(imageValue.toString(), image);
        return map;
    }

    /**
     * 生成一个随机数字图片验证码 : <br>
     *
     * @return
     */
    public static Map<String, BufferedImage> getDynamicNumImage() {
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        StringBuilder imageValue = new StringBuilder();
        // 创建一个BufferedImage
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        // 画背景色
        graphics.setColor(new Color(247,247,247));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        // 画随机字符
        for (int i = 0; i < NUM; i++) {
            graphics.setColor(getRandomColor());
            graphics.setFont(new Font(null, Font.BOLD, random.nextInt(20) + 48));
            String temp = nums[random.nextInt(nums.length)];
            imageValue.append(temp);
            graphics.drawString(temp, WIDTH / NUM * (i), HEIGHT - 40);
        }
        // 画干扰线
        for (int j = 0; j < LINE; j++) {
            graphics.setColor(getRandomColor());
            graphics.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
                    random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }
        map.put(imageValue.toString(), image);
        return map;
    }

    public static Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random
                .nextInt(255));
    }

    /**
     * 将随机图片转换成inputStream
     * 
     * @param image
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream(BufferedImage image)
            throws IOException {
        // 将BufferedImage转换成符合JPEG格式字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(bos);
        encode.encode(image);
        byte[] byteArr = bos.toByteArray();
        return new ByteArrayInputStream(byteArr);
    }

}
