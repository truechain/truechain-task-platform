package com.truechain.task.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangbinqi
 * 图片验证码工具类
 */
public class RandomValidateCodeUtil {

	public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//放到session中的key
//    private String randString = "0123456789";//随机产生只有数字的字符串 private String
    //private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生只有字母的字符串
    private static final String RAND_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
    private static final int WIDTH = 95;// 图片宽
    private static final int HEIGHT = 25;// 图片高
    private static final int LINE_SIZE = 40;// 干扰线数量
    private static final int STRING_NUM = 4;// 随机产生字符数量

    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);

    private static final Random RANDOM = new Random();

    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + RANDOM.nextInt(bc - fc - 16);
        int g = fc + RANDOM.nextInt(bc - fc - 14);
        int b = fc + RANDOM.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 生成随机图片
     */
    public static String getRandcode(OutputStream outputStream){
//    		HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, WIDTH, HEIGHT);//图片大小
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小
        g.setColor(getRandColor(110, 133));//字体颜色
        // 绘制干扰线
        for (int i = 0; i <= LINE_SIZE; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= STRING_NUM; i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info(randomString);
        //将生成的随机字符串保存到session中
//        session.removeAttribute(RANDOMCODEKEY);
//        session.setAttribute(RANDOMCODEKEY, randomString);
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
//            ImageIO.write(image, "JPEG", response.getOutputStream());
        	ImageIO.write(image, "JPEG", outputStream);
        } catch (Exception e) {
            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
        }
        return randomString;
    }

    /**
     * 绘制字符串
     */
    private static String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(RANDOM.nextInt(RAND_STRING
                .length())));
        randomString += rand;
        g.translate(RANDOM.nextInt(3), RANDOM.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private static void drowLine(Graphics g) {
        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HEIGHT);
        int xl = RANDOM.nextInt(13);
        int yl = RANDOM.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    public static String getRandomString(int num) {
        return String.valueOf(RAND_STRING.charAt(num));
    }

}
