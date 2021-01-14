package com.brandWall.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class WatermarkPicUtil {

	public static void main(String[] args) throws Exception {
		
		
		//mark("D:/05.jpg","D:/2.jpg",new Color(189,188,186,80),"交通厅",200);
		
		 markImageByText("石小科","D:/05.jpg","D:2.jpg",0,new Color(0,0,0),"JPG");
	}

	/**
	 * 图片添加水印
	 * 
	 * @param srcImgPath
	 *            需要添加水印的图片的路径
	 * @param outImgPath
	 *            添加水印后图片输出路径
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param waterMarkContent
	 *            水印的文字
	 */
	public static String mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent,int width) throws Exception {
		 	BufferedImage im =ImageIO.read(new FileInputStream(srcImgPath));
	        Graphics g = im.getGraphics();
	        g.setColor(markContentColor);
	        Font font = new Font("宋体",Font.PLAIN,width);  //设置字体
	        g.setFont(font);	
	        int w = im.getWidth();
	        int h = im.getHeight();
	        g.drawString(waterMarkContent,(w/2),(h/2));
	        g.dispose();
	
	        ImageIO.write(im,"jpg",new File(outImgPath));
	        return outImgPath;
	}
	
	 /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * @param logoText 要写入的文字
     * @param srcImgPath 源图片路径
     * @param newImagePath 新图片路径
     * @param degree 旋转角度
     * @param color  字体颜色
     * @param formaName 图片后缀
     */
    public static void markImageByText(String logoText, String srcImgPath,String newImagePath,Integer degree,Color color,String formaName) {
        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            java.awt.Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),  buffImg.getWidth()/2,buffImg.getHeight() /2);
            }
            
           
            
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, buffImg.getHeight() /4));
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.15f));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            FontMetrics fm = g.getFontMetrics(new java.awt.Font("宋体", java.awt.Font.BOLD, buffImg.getHeight() /2));
            int textWidth = fm.stringWidth(logoText);
            int widthX = (buffImg.getWidth() - textWidth) / 2;
            System.out.println(widthX);
            System.out.println( buffImg.getHeight());
            g.drawString(logoText, buffImg.getWidth()/2-400, buffImg.getHeight()/2+200);
            
            
            
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(newImagePath);
            ImageIO.write(buffImg, formaName, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
	
	

}
