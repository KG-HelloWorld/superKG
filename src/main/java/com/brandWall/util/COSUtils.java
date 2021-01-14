package com.brandWall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;

/**
 * 腾讯云对象存储工具
 * @author 冼灿康
 *
 */
public class COSUtils {

	//	private static COSClient cosClient;
	private static final String bucketName = "chezhuzhu-1256636484";
	public static final String COS_URL = "https://chezhuzhu-1256636484.cosgz.myqcloud.com/";
	public static final String COS_URL_SINGLE = COS_URL + "single/";//SaaS单商家图片正式路径
	public static final String COS_URL_SINGLE_TEMP = COS_URL_SINGLE + "temp/";//SaaS单商家图片临时路径

	public static final String COS_URL_SAAS = COS_URL + "saas/";//SaaS图片正式路径

	public static final String COS_URL_SAAS_TEMP = COS_URL_SAAS + "temp/";//SaaS图片临时路径

	public static COSClient COSClient() {
		COSCredentials cred = new BasicCOSCredentials("AKIDxoxy7WN3whGdt6S5rRsCMOEtPp35FZ1J",
				"GLEwW9dx53qbEfuxpW4RcbP4YqzIUVKy");
		ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
		COSClient c = new COSClient(cred, clientConfig);
		return c;
	}

	/**
	 * 上传图片
	 * @param request 文件请求
	 * @param shopId 店铺id
	 * @return 腾讯云服务器上创建的临时文件夹下的文件路径
	 * @throws Exception
	 */
	public static String uploadTempSingle(MultipartFile file, String dir) throws Exception {
		if (Config.copyImgToTxy.equals("1")) {
			Calendar calendar = Calendar.getInstance();
			String key = new StringBuilder(dir).append("//").append(calendar.get(Calendar.YEAR)).append("//")
					.append(calendar.get(Calendar.MONTH) + 1).append("//").append(calendar.get(Calendar.DAY_OF_MONTH))
					.append("//").append(FileUtil.getFileName()).append(".").append(FileUtil.getFileSuffix(file))
					.toString();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			COSClient c = COSClient();
			try {
				c.putObject(bucketName, key, file.getInputStream(), objectMetadata);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.shutdown();
			}
			return COS_URL + key;
		}
		return null;
	}

	public static String uploadTempSingle(File file, String dir) throws Exception {
		if (Config.copyImgToTxy.equals("1")) {
			Calendar calendar = Calendar.getInstance();
			String key = new StringBuilder(dir).append("//").append(calendar.get(Calendar.YEAR)).append("//")
					.append(calendar.get(Calendar.MONTH) + 1).append("//").append(calendar.get(Calendar.DAY_OF_MONTH))
					.append("//").append(FileUtil.getFileName()).append(".").append(FileUtil.getFileSuffix(file))
					.toString();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.length());
			COSClient c = COSClient();
			try {
				c.putObject(bucketName, key, new FileInputStream(file), objectMetadata);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.shutdown();
			}
			return COS_URL + key;
		} else {
			return null;
		}
	}
	
	/**
	 * 移动到腾讯云服务器的上设置的正式文件夹
	 * @param tempPath 临时路径
	 * @return 储存在数据库的正式文件路径
	 */
	public static String toOfficialSingle(String tempPath, String tempDir) {
		if (Config.copyImgToTxy.equals("1")) {
			System.out.println(COS_URL_SINGLE + tempDir);
			if (null == tempPath
					|| !tempPath.replaceAll("/", "").contains((COS_URL_SINGLE + tempDir).replaceAll("/", ""))) {
				if (tempPath.contains(COS_URL_SINGLE)) {
					return tempPath.replace(COS_URL_SINGLE, "");
				}
				return tempPath;
			}
			tempPath = tempPath.replace(COS_URL, "");
			String destKey = tempPath.replace("temp//", "").replace("temp/", "");
			System.out.println(tempPath.replaceAll("//", "/"));
			System.out.println(destKey.replaceAll("//", "/"));
			CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, tempPath.replaceAll("//", "/"),
					bucketName, destKey.replaceAll("//", "/"));
			COSClient c = COSClient();
			try {
				c.copyObject(copyObjectRequest);//把临时文件Copy到正式文件中
				c.deleteObject(bucketName, tempPath.replaceAll("//", "/"));//移动成功删除临时文件
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.shutdown();
			}
			//		String url = destKey.replace(COS_URL_SINGLE, "");
			c.shutdown();
			return destKey;
		} else {
			return null;
		}
	}


	public static String uploadHtmlSingle(String html, String name, String shopId, String proName) throws Exception {
		if (Config.copyImgToTxy.equals("1")) {
			//路径
			String dir = "/" + proName + "/" + shopId;
			String fileDir = Config.fileLocationPath + dir;
			//创建文件夹
			FileUtil.createDir(fileDir);
			fileDir = fileDir + "/" + name;
			//上传临时文件夹
			FileUtil.writeToFile(fileDir, html);
			File file = new File(fileDir);
			ObjectMetadata objectMetadata = new ObjectMetadata();
			System.out.println(file.length());
			objectMetadata.setContentLength(file.length());
			COSClient c = COSClient();
			try {
				c.deleteObject(bucketName, dir + "/" + name);//移动成功删除临时文件
				c.putObject(bucketName, dir + "/" + name, new FileInputStream(file), objectMetadata);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				c.shutdown();
				file.delete();
			}

			return shopId + "/" + name;
		} else {
			return null;
		}
	}

	



	

	/**
	 * 获取文件访问路径
	 * @param filePath 数据库储存的路径
	 * @param size 图片大小
	 * @return
	 */
	public static String fileUrl(String filePath, Integer size) {
		String url = filePath;
		if (!filePath.contains(COS_URL)) {
			url = COS_URL_SINGLE + filePath;
		}
		if (size != null) {
			url += "?imageView2/0/w/" + size + "/h/" + size;
		}
		return url;
	}

	

	public static String dowloadNet(String pic) {
		if (Config.copyImgToTxy.equals("1")) {
			// new一个URL对象
			try {
				URL url = new URL(pic);

				File file = new File(pic);
				// 打开链接
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 设置请求方式为"GET"
				conn.setRequestMethod("GET");
				// 超时响应时间为5秒
				conn.setConnectTimeout(5 * 1000);
				// 通过输入流获取图片数据
				InputStream inStream = conn.getInputStream();
				// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
				byte[] data = FileUtil.readInputStream(inStream);
				FileUtil.createDir(Config.fileLocationPath + "//temp//");
				String picUrl = "temp//" + file.getName();

				// new一个文件对象用来保存图片，默认保存当前工程根目录
				File imageFile = new File(Config.fileLocationPath + "//" + picUrl);
				// 创建输出流
				FileOutputStream outStream = new FileOutputStream(imageFile);
				// 写入数据
				outStream.write(data);
				// 关闭输出流
				outStream.close();
				return Config.fileLocationPath + "//" + picUrl;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	

	

	public static void main(String[] args) throws IOException {
		//toOfficialSingle("http://chezhuzhu-1256636484.file.myqcloud.com/single//temp//8ad1fddb62840abf01628464d61c00b0//2018//5//23//7a6addc4379e49efb952f6d5313c4a9b.jpg","/temp");

		//按照时间选出文件夹的文件
	}
}