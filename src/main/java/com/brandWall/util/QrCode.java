package com.brandWall.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrCode {
	    private static final int BLACK = 0xff000000;
	    private static final int WHITE = 0xFFFFFFFF;

	    /**
	     * @param args
	     */
	    public static void main(String[] args) {
	    	QrCode test = new QrCode();
	        File file = new File("d:\\test.png");
	        test.encode("hello, welcome to :http://www.baidu.com", file, BarcodeFormat.QR_CODE, 200, 200, null);
	    }
	    
	    public static String qrCode(String sId,String url){
	    	QrCode test = new QrCode();
	    	FileUtil.createDir(Config.fileTempPath+"//"+sId+"/");
	    	String dz="//"+sId+"//"+FileUtil.getFileName()+".png";
	        File file = new File(Config.fileTempPath+dz);
	        test.encode(url, file, BarcodeFormat.QR_CODE, 200, 200, null);
	        return Config.fileTempUrl+dz;
	        
	    }  

	    /**
	     * 生成QRCode二维码<br> 
	     * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
	     *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
	     *  修改为UTF-8，否则中文编译后解析不了<br>
	     */
	    public static void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
	        try {
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);	            
	            writeToFile(bitMatrix, "png", file);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * 生成二维码图片<br>
	     * 
	     * @param matrix
	     * @param format 图片格式
	     * @param file 生成二维码图片位置
	     * @throws IOException
	     */
	    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
	        BufferedImage image = toBufferedImage(matrix);
	        ImageIO.write(image, format, file);
	    }

	    /**
	     * 生成二维码内容<br>
	     * 
	     * @param matrix
	     * @return
	     */
	    public static BufferedImage toBufferedImage(BitMatrix matrix) {
	        int width = matrix.getWidth();
	        int height = matrix.getHeight();
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
	            }
	        }
	        return image;
	    }
	  

	    public static String QRfromGoogle(String chl) throws Exception {
			int widhtHeight = 300;
			String EC_level = "L";
			int margin = 0;
			chl = UrlEncode(chl);
			String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight
					+ "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;
	 
			return QRfromGoogle;
		}
	 // 特殊字符处理
		public static String UrlEncode(String src)  throws UnsupportedEncodingException {
			return URLEncoder.encode(src, "UTF-8").replace("+", "%20");
		}

}
