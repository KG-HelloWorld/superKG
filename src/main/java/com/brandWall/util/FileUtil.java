package com.brandWall.util;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

public class FileUtil {
	public static String fileLocation = "";// 图片相对路径
	public static String tempLocation = "";// 图片绝对路径

	public static String bUrl(String url) {
		if (null == url) {
			return "";
		}
		if (url.contains("http")) {
			return url;
		}
		
		return Config.fileRootUrl + url;

		
	}
	
	/**
	 * 把请求中的临时文件保存到服务器
	 * 
	 * @param paramName
	 *            参数名
	 * @param request
	 * @return 文件
	 */
	public static File toFile(String paramName, MultipartHttpServletRequest request)
	        throws IllegalStateException, IOException {
		
		MultipartFile file = request.getFile(paramName);
		String fileName=file.getName();
		String relativePath = Config.fileSavePath;// 获取到tomcat的相对路径
		String ml = relativePath + "//" + CayUtil.getFolderByDate();// 获取文件的上传路径
		createDir(ml);
		String prefix = getFileSuffix(file);
		String originFileName = fileName + "." + prefix;
		File uploadFile = new File(ml, originFileName);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.transferTo(uploadFile);
		return uploadFile;
	}
	
	public static Map<String, Object> slUrl(String url) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateUtil.isValid(url)) {
			if(Config.imgWay==0){
				int index = url.lastIndexOf(".");
				if (index >= 0) {
					String fix = url.substring(0, url.lastIndexOf("."));
					String prefix = url.substring(url.lastIndexOf(".") + 1);
					map.put("img_720", fix + "_720." + prefix);
					map.put("img_360", fix + "_360." + prefix);
					map.put("img_120", fix + "_120." + prefix);
					return map;
				}
			}else{
				if(!url.contains(COSUtils.COS_URL)){
					url = Config.proName+"//"+url;
				}
				map.put("img_720", COSUtils.fileUrl(url, 720));
				map.put("img_360", COSUtils.fileUrl(url, 360));
				map.put("img_120", COSUtils.fileUrl(url, 120));
				return map;
			}
		}
		map.put("img_720", "");
		map.put("img_360", "");
		map.put("img_120", "");
		return map;
	}

	
	public static String getRootUrl() {
		if(Config.imgWay==0){
			return Config.fileRootUrl ;
		}else{
			return COSUtils.COS_URL_SINGLE ;
		}
	}
	
	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param file
	 * @param catalog
	 */
	public static String uploadFile(MultipartFile file, String path, String shopId) {
		if(Config.imgWay==0){
		String fileTempLocation = uploadImage(file, Config.fileTempLocation, shopId);
		if (fileTempLocation == null) {
			return null;
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Config.fileTempPath);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		File f = new File(fileTempLocation);
		FileBody bin = new FileBody(f);
		StringBody stringBody1 = new StringBody(Config.proName, ContentType.MULTIPART_FORM_DATA);

		StringBody stringBody2 = new StringBody(shopId, ContentType.MULTIPART_FORM_DATA);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		builder.addPart("sGetFile", bin);
		builder.addPart("sProjectName", stringBody1);
		builder.addPart("shopId", stringBody2);
		HttpEntity entity = builder.build();
		httppost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
			HttpEntity e = response.getEntity();
			String content = EntityUtils.toString(e);
			JSONObject jb = JSONObject.fromObject(content);
			String fileUrl = jb.get("picUrl").toString();
			fileUrl = fileUrl.replace("//" + Config.proName + "//", "");
			fileLocation = fileUrl;
			// 文件移动
			String url = FileUtil.moveAbsoluteToFile(Config.fileTempUrl + fileUrl);
//			COSUtils.movesingle(Config.fileRootUrl+url);
			return url;
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		}else{
			String dir = Config.proName+"//temp//"+shopId;
			try {
				String urlTemp = COSUtils.uploadTempSingle(file, dir);
				String dir1="/temp//";
				String url = COSUtils.toOfficialSingle(urlTemp,dir1).replace(Config.proName, "");
				return url;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public static String uploadFile(MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			String relativePath = Config.wechatGaveFile;// 获取到tomcat的相对路径
			String fileName = file.getOriginalFilename();
			File uploadFile = new File(relativePath, fileName);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fileName;
		} else {
			return null;
		}
	}

	/**
	 * 获取随机文件名
	 * 
	 * @return
	 */
	public static String getFileName() {
		String name = UUID.randomUUID().toString();
		String fileName = name.replace("-", "");
		return fileName;
	}

	/**
	 * 获取tomcat相对路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRelativePath(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("//");
		return realPath;
	}

	/**
	 * 创建文件夹目录
	 * 
	 * @param destDirName
	 *            目录
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(HttpServletRequest request, String path) {
		File file = new File(getRelativePath(request) + path);
		boolean flag = false;

		if (file.exists() && file.isFile()) {// 判断文件是否存在
			flag = file.delete();// 删除文件
		} else {
		}

		return flag;
	}

	/**
	 * 删除文件(nginx)
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static boolean deleteFileNginx(String path) {
		File file = new File(Config.fileSavePath + path);
		boolean flag = false;

		if (file.exists() && file.isFile()) {// 判断文件是否存在
			flag = file.delete();// 删除文件
		} else {
		}

		return flag;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(File file) {
		String fileName = file.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param file
	 * @param catalog
	 */
	public static String uploadFile(HttpServletRequest request, MultipartFile file, String catalog) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			String relativePath = getRelativePath(request);// 获取到tomcat的相对路径
			String ml = relativePath + "//" + catalog;// 获取文件的上传路径
			createDir(ml);
			String prefix = getFileSuffix(file);
			// 原文件名
			String originFileName = getFileName() + "." + prefix;

			File uploadFile = new File(ml, originFileName);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return catalog + "//" + originFileName;
		} else {
			return null;
		}
	}

	public static String uploadHtml(String html, String name, String shopId) {
		try {
			String proName = Config.proName;// 项目名称
			if(Config.imgWay==0){
				List<NameValuePair> list = new ArrayList<>();
				list.add(new BasicNameValuePair("html", html));
				list.add(new BasicNameValuePair("sProjectName", proName));
				list.add(new BasicNameValuePair("shopId", shopId));
				list.add(new BasicNameValuePair("fileName", name));
				//String url = Config.fileHtml.trim();
				String url ="";
				String result = HttpClientUtil.requestByPostMethod(url, list, "utf-8");
				JSONObject json = JSONObject.fromObject(result);
				
				//CopyHtml到腾讯云
				html=html.replaceAll(Config.getFileRootUrl(), COSUtils.COS_URL_SINGLE);
				
				COSUtils.uploadHtmlSingle(html, name, shopId, proName);
				
				return json.getString("url");
			}else{
				return COSUtils.uploadHtmlSingle(html, name, shopId, proName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getHtml(String htmlUrl) {
		String html = "";
		try {
			if (!ValidateUtil.isValid(htmlUrl)) {
				return "";
			}
			JSONObject json = null;
			if(Config.imgWay==0){
				List<NameValuePair> list = new ArrayList<>();
				list.add(new BasicNameValuePair("url", htmlUrl.replace(Config.fileRootUrl, "//" + Config.proName + "//")));
//				String url = Config.htmlUrl;
				String url = "";
				String result = HttpClientUtil.requestByPostMethod(url, list, "utf-8");
				if (!ValidateUtil.isValid(result)) {
					return htmlUrl;
				}
				json = JSONObject.fromObject(result);
				html = json.getString("html");
			}
			html = html.replace("<!DOCTYPE html>", "");
			return html;
		} catch (Exception e) {
			return "";
		}
	}

	
	
	/**
	 * 去掉文件后缀
	 */
	public static String ridSuffix(String fileName) {
		String sFileName = "";
		if (ValidateUtil.isValid(fileName)) {
			int index = fileName.lastIndexOf(".");
			String newName = fileName.substring(index);
			sFileName = fileName.replace(newName, "");
			// String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return sFileName;
	}

	/**
	 * 上传模板文件
	 * 
	 * @param request
	 * @param file
	 * @param catalog
	 */
	public static String uploadTempFile(MultipartFile file, String catalog, int flag) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			if(Config.imgWay==0){
				String ml = Config.fileRootUrl + "//" + catalog;// 获取文件的上传路径
				createDir(ml);
				String originFileName = file.getOriginalFilename();
				File uploadFile = new File(ml, originFileName);
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (flag == 1) {// 解压压缩文件
					String fileDre = originFileName.substring(0, originFileName.lastIndexOf("."));
					// createDir(fileDre);
					try {
						ZipDecompressing.unzip(uploadFile.getPath(), ml);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (uploadFile.exists() && uploadFile.isFile()) {// 判断文件是否存在
						uploadFile.delete();// 删除文件
					} else {
					}
					return catalog + "//" + fileDre;
				}
				return catalog + "//" + originFileName;
			}
			
		}
		
		return null;
	}

	/**
	 * 上传文件（base64）
	 * 
	 * @param request
	 * @param baseCode
	 * @param type
	 * @param role
	 * @return
	 */
	public static String toFile(HttpServletRequest request, String baseCode, String type, String role) {

		String fileName = getFileName();
		String relativePath = getRelativePath(request);// 获取到tomcat的相对路径
		String folder = role + "//" + fileName + "." + type;
		String ml = getRelativePath(request) + "//" + role;
		// 先创建目录
		createDir(ml);
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(baseCode.replace(" ", "+"));
			FileOutputStream out = new FileOutputStream(ml + "//" + fileName + "." + type);
			out.write(buffer);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return folder;

	}
	/**
	 * 上传图片（nginx）
	 * 
	 * @author wsy
	 * @time 2016年6月29日下午5:46:54
	 * @param file
	 * @param catalog
	 * @return
	 */
	public static String uploadImage(InputStream in, String path, String catalog) {

		// 判断文件是否为空
		String relativePath = Config.fileSavePath + path;
		String ml = relativePath + "//" + catalog;// 获取文件的上传路径
		createDir(ml);
		String originFileName = getFileName() + ".jpg";
		File uploadFile = new File(ml, originFileName);
		try {
			FileUtils.copyInputStreamToFile(in, uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  path + "/" + catalog + "/" + originFileName;
	}
	/**
	 * 上传图片（nginx）
	 * 
	 * @author wsy
	 * @time 2016年6月29日下午5:46:54
	 * @param file
	 * @param catalog
	 * @return
	 */
	public static String uploadImage(MultipartFile file, String path, String catalog) {
			// 判断文件是否为空
			if (!file.isEmpty()) {
				String relativePath = path;
				String ml = relativePath + catalog;// 获取文件的上传路径
				createDir(ml);
				String prefix = getFileSuffix(file);
				String originFileName = getFileName() + "." + prefix;
				// 中等格式文件名
				String mediumFileName = ridSuffix(originFileName);
				mediumFileName = mediumFileName + "Medium" + "." + prefix;
				// 小格式文件名
				String smallFileName = ridSuffix(originFileName);
				smallFileName = smallFileName + "Small" + "." + prefix;
				
				File uploadFile = new File(ml, originFileName);
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
//					//生成中等图片
//					ImageUtils.zoomImageScale(uploadFile, ml+"\\"+mediumFileName, 300);
//					//生成小图片
//					ImageUtils.zoomImageScale(uploadFile, ml+"\\"+smallFileName, 100);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return Config.fileTempUrl + "/" + catalog + "/" + originFileName;
			} 
		
		return null;
	}

	public static String uploadImageTemp(MultipartFile file, String catalog) {
		return uploadImage(file, Config.fileTempPath, catalog);

	}

	/**
	 * 获取request域中的文件
	 * 
	 * @author wsy
	 * @time 2016年7月11日下午5:21:30
	 * @param request
	 * @param fileName
	 * @return
	 */
	public static MultipartFile getMultipartFile(HttpServletRequest request, String fileName) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multiRequest.getFile(fileName);
			return multipartFile;
		} else {
			return null;
		}
	}

	public static String moveAbsoluteToFile(String path) {
		if(Config.imgWay==0){
			File file = new File(path);
			String name = file.getName();
			if (!path.contains(Config.fileTempUrl)) {
				return path;
			}
			String formPath = path.replace(Config.fileTempUrl, "");
			boolean flag = movie(Config.getFileTempPath() + formPath,
					Config.getFileSavePath() + formPath.replace(name, ""));
			if (flag) {
				return formPath;
			}
			return "temp/" + formPath;
		}
		return path;
		
	}

	// 模板数据移动
	public static String moveTempAbsoluteToFile(String path) {
		
		return null;
	}

	public static String unzipTemp(String path) {
		
		return null;
	}

	public static boolean movie(String formPath, String toPath) {
		boolean flag = false;
		File file = new File(formPath);
		if (!file.exists()) { // 源文件或源文件夹不存在
			return false;
		}
		if (file.isFile()) { // 文件复制
			try {
				flag = copyFile(formPath, toPath, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		File file2 = new File(formPath);
		file2.delete();
		return flag;
	}

	/**
	 * 复制文件到目标目录
	 * 
	 * @param srcPath
	 *            源文件绝对路径
	 * @param destDir
	 *            目标文件所在目录
	 * @param overwriteExistFile
	 *            是否覆盖目标目录下的同名文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean copyFile(String srcPath, String destDir, boolean overwriteExistFile) throws IOException {
		boolean flag = false;

		File srcFile = new File(srcPath);
		if (!srcFile.exists() || !srcFile.isFile()) { // 源文件不存在
			return false;
		}

		// 获取待复制文件的文件名
		String fileName = srcFile.getName();
		String destPath = destDir + File.separator + fileName;
		File destFile = new File(destPath);
		if (destFile.getAbsolutePath().equals(srcFile.getAbsolutePath())) { // 源文件路径和目标文件路径重复
			return false;
		}
		if (destFile.exists() && !overwriteExistFile) { // 目标目录下已有同名文件且不允许覆盖
			return false;
		}

		File destFileDir = new File(destDir);
		if (!destFileDir.exists() && !destFileDir.mkdirs()) { // 目录不存在并且创建目录失败直接返回
			return false;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcPath);
			fos = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];
			int c;
			while ((c = fis.read(buf)) != -1) {
				fos.write(buf, 0, c);
			}

			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fos.flush();
			fis.close();
			fos.close();
		}

		return flag;
	}

	/**
	 * 复制一个目录及其子目录、文件到另外一个目录
	 * 
	 * @param path
	 *            :复制的文件来源目录
	 * @param copyPath
	 *            ：复制到目录
	 * @throws IOException
	 */
	public static void copyFolder(String path, String copyPath) throws IOException {

		File filePath = new File(path);
		InputStream read;
		OutputStream write;

		File dest = new File(copyPath);

		if (filePath.isDirectory()) {// 是目录
			dest.mkdir();
			File[] list = filePath.listFiles();
			for (int i = 0; i < list.length; i++) {
				String newPath = path + File.separator + list[i].getName();
				String newCopyPath = copyPath + File.separator + list[i].getName();
				copyFolder(newPath, newCopyPath);
			}
		} else if (filePath.isFile()) {// 是文件
			read = new FileInputStream(filePath);
			write = new FileOutputStream(dest);
			int byteread = 0;
			byte[] buf = new byte[1024];// 字节
			while ((byteread = read.read(buf)) != -1) {
				write.write(buf, 0, byteread);
			}
			read.close();
			write.close();
		} else {
		}
	}

	public static String readFile(String path) throws Exception {
		StringBuffer sb = new StringBuffer();
//		FileInputStream fis = null;
//		BufferedReader bufferedReader = null;
//		UnicodeReader ur = null;
//		File file = new File(path);
//		if (!file.exists()) {
//			throw new MyException("抱歉，该模板维护中");
//		}
//		try {
//			fis = new FileInputStream(file);
//			ur = new UnicodeReader(fis);
//			bufferedReader = new BufferedReader(ur);
//			String lineTxt = null;
//			while ((lineTxt = bufferedReader.readLine()) != null) {
//				sb.append(lineTxt);
//			}
//		} catch (Exception e) {
//			throw new Exception(e);
//		} finally {
//			if (null != bufferedReader) {
//				bufferedReader.close();
//			}
//			if (null != ur) {
//				ur.close();
//			}
//			if (null != fis) {
//				fis.close();
//			}
//
//		}
		return sb.toString();
	}

	public static final String removeBOM(String data) {

		if (TextUtils.isEmpty(data)) {
			return data;
		}

		if (data.startsWith("ufeff")) {
			// Log.e(TAG, "Json字符串BOM头处理");
			return data.substring(1);
		} else {
			return data;
		}
	}

	/**
	 * 写入数据到文件
	 * 
	 * @author only_U
	 * @date:2016年12月9日 下午7:26:32
	 * @param fileUrl：文件路径
	 * @param value：值
	 * @throws Exception
	 */
	public static void writeToFile(String path, String value) throws Exception {
		File filePath = new File(path);
		OutputStream write;
		write = new FileOutputStream(filePath);
		write.write(value.toString().getBytes("utf-8"));
		write.close();
	}

	/*
	 * public static uploadFile(HttpServletRequest request){ //创建一个通用的多部分解析器.
	 * CommonsMultipartResolver commonsMultipartResolver = new
	 * CommonsMultipartResolver(request.getSession().getServletContext()); //设置编码
	 * commonsMultipartResolver.setDefaultEncoding("utf-8"); //判断 request
	 * 是否有文件上传,即多部分请求... if (commonsMultipartResolver.isMultipart(request)) {
	 * //转换成多部分request MultipartHttpServletRequest multipartRequest =
	 * commonsMultipartResolver.resolveMultipart(request);
	 * 
	 * // file 是指 文件上传标签的 name=值 // 根据 name 获取上传的文件... MultipartFile file =
	 * multipartRequest.getFile("file"); }
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	
	public static String dowloadNet(String pic) {
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
			byte[] data = readInputStream(inStream);
			String filePath=Config.fileTempPath + "//" + CayUtil.getFolderByDate();
			FileUtil.createDir(filePath);
			String picUrl = filePath + "//" + file.getName();
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			File imageFile = new File(picUrl);
			// 创建输出流
			FileOutputStream outStream = new FileOutputStream(imageFile);
			// 写入数据
			outStream.write(data);
			// 关闭输出流
			outStream.close();
			return picUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String dowloadNet(String shopId, String pic) {
		// new一个URL对象
		if(!ValidateUtil.isValid(pic)){
			return null;
		}
		try {
			pic = pic.replace("https://", "http://");
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
			byte[] data = readInputStream(inStream);
			FileUtil.createDir(Config.fileLocationPath + "//" + shopId + "//" + CayUtil.getFolderByDate());
			String picUrl = shopId + "//" + CayUtil.getFolderByDate() + "//" + getFileName() + "."
					+ getFileSuffix(file);
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			File imageFile = new File(Config.fileLocationPath + "//" + picUrl);
			//查看文件是否存在
			if(imageFile.exists()){
				return Config.fileLocationPath + "//" + picUrl;
			}
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

		return null;
	}


	public static void main(String[] args) {
	}

	
}
