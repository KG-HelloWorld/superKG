package com.brandWall.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil {
	
	/**
	 * 解析Html，获取图片标签的属性值
	 * @param content Html格式的字符串
	 * @return img标签的src属性值
	 */
	public static List<String> getImgSrc(String content){
		List<String> imgPaths=new ArrayList<String>();
		Document doc = Jsoup.parse(content);
		Elements imgs = doc.select("img[src]");
		for (Element element : imgs) {
			String imgPath = element.attr("src");
			imgPaths.add(imgPath);
		}
		return imgPaths;
	}
	
	/**
	 * 替换富文本编辑器的图片路径
	 * @param content
	 * @return
	 */
	public static String replaceImgSrc(String content){
		List<String> imgPaths=new ArrayList<String>();
		
		Document doc = Jsoup.parse(content);
		Elements imgs = doc.select("img[src]");
		for (Element element : imgs) {
			String imgPath = element.attr("src");
			if(imgPath.length()>7){
				String head = imgPath.substring(0,7);
				if(!head.equals("http://")&&!imgPath.contains("https://")){
					Element img = null;
					img = element.attr("src",Config.fileRootUrl+imgPath);
					
					imgPath = img.attr("src");
				}
			}
			imgPaths.add(imgPath);
		}
		return doc.toString();
	}
	
	public static String replaceImgToRealSrc(String content){
		if (!ValidateUtil.isValid(content)) {
			return "";
		}
		Document doc = Jsoup.parse(content);
		Elements imgs = doc.select("img[src]");
		for (Element element : imgs) {
			String imgPath = element.attr("src");
			if(imgPath.length()>7){
					if(imgPath.contains(Config.fileTempUrl)){
						String imge=imgPath;
						System.out.println(imge);
						Element img = element.attr("src",imgPath.replaceAll(Config.fileTempUrl, ""));
						imgPath = img.attr("src");
						String imgUrl=FileUtil.moveAbsoluteToFile(imge);
						System.out.println(imgUrl);
						element.attr("src",imgPath.replaceAll(Config.fileTempUrl, ""));
					}else if(imgPath.contains(Config.fileRootUrl)){
						 element.attr("src",imgPath.replaceAll(Config.fileRootUrl, "/"));
					}
				
			}
		}
		return doc.toString();
	}
	
	
	
	
	public static String html(String body){
		String html="<!DOCTYPE html><html>";
		html+="</head><meta charset=\"UTF-8\">";
		html+="<body>";
		html+=HtmlUtil.replaceImgSrc(body);
		html+="</body>";
		html+="</head>";
		html+="</html>";
		return html;
	}
}
