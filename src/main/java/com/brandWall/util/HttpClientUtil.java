package com.brandWall.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpClientUtil {

	/**
	 * 通过GET方式发起http请求
	 */
	public static String requestByGetMethod(String url) {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = getHttpClient();
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url);
//			System.out.println("执行get请求:...." + get.getURI());
			CloseableHttpResponse httpResponse = null;
			// 发送get请求
			httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
//					System.out.println("响应状态码:" + httpResponse.getStatusLine());
//					System.out.println("-------------------------------------------------");
					return EntityUtils.toString(entity);
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * POST方式发起http请求
	 */
	public static String requestByPostMethod(String url, List<NameValuePair> list, String encode) {
		CloseableHttpClient httpClient = getHttpClient();
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			HttpPost post = new HttpPost(url);
			// 创建参数列表
			// List<NameValuePair> list1 = new ArrayList<NameValuePair>();

			/*
			 * list.add(new BasicNameValuePair("appkey", "714fb85ef98b184c"));
			 * list.add(new BasicNameValuePair("carorg", "guangdong"));
			 * list.add(new BasicNameValuePair("lsprefix", "粤")); list.add(new
			 * BasicNameValuePair("lsnum", "SR418S")); list.add(new
			 * BasicNameValuePair("lstype", "02")); list.add(new
			 * BasicNameValuePair("frameno", "197003")); list.add(new
			 * BasicNameValuePair("engineno", "0410"));
			 */
			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, encode);
			post.setEntity(uefEntity);
			// System.out.println("POST 请求...." + post.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					reader = new BufferedReader(new InputStreamReader(entity.getContent(), encode));

					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}

				}

			} finally {
				httpResponse.close();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sb);
		return sb.toString();
	}
	
	
	/**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url,String jsonString)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
	
	
	@SuppressWarnings({ "rawtypes" })
	public static Msg upload(String url, Map<String, Object> param, Map<String, File> files) {
		// PostMethod filePost = new
		// PostMethod("http://localhost:9092/api/open/common/test");
		PostMethod filePost = new PostMethod(url);
		HttpClient client = new HttpClient();
		filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		Part[] parts = new Part[param.size() + files.size()];
		byte[] responseBody = null;
		String result = null;
		Msg msg = null;
		int i = 0;
		try {
			// 页面参数提交
			Iterator iter = param.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				String val = String.valueOf(entry.getValue());
				StringPart fp = new StringPart(key, val);
				parts[i] = fp;
				i++;
				// filePost.setParameter(key, val);
			}

			// 页面文件参数提交
			Iterator iteFiles = files.entrySet().iterator();
			// Part[] parts =new Part[files.size()];
			// int i=0;
			while (iteFiles.hasNext()) {
				Map.Entry entry = (Map.Entry) iteFiles.next();
				String key = entry.getKey().toString();
				File file = files.get(key);
				FilePart fp = new FilePart(key, file);
				parts[i] = fp;
				i++;
			}
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

			client.getHttpConnectionManager().getParams().setConnectionTimeout(500000);

			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
				responseBody = filePost.getResponseBody();

				result = new String(responseBody, "utf-8");
				JSONObject jsonObject = JSONObject.fromObject(result);
				System.out.println(jsonObject.get("data"));
				msg = new Msg(jsonObject.get("code").toString(), jsonObject.get("message").toString(),
						jsonObject.get("data"));
			} else {
				System.out.println("上传失败");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return msg;
	}

	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	}

	public static String getHtml(String sUrl) {
		StringBuffer html = new StringBuffer();
		try {
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getHtml("http://chezhuzhu-1256636484.cos.ap-guangzhou.myqcloud.com/single/8ad1fddb62840abf01628464d61c00b0/4028bb81638fc2ec01638fd6ee670000.html"));
		/*
		 * List<NameValuePair> list=new ArrayList<>(); NameValuePair pair =new
		 * HttpClientUtil();
		 */

		// requestByPostMethod("", list, "utf-8");
		// requestByGetMethod("http://apis.map.qq.com/ws/geocoder/v1/?location=39.984154,116.307490&get_poi=1&key=2L7BZ-CDHWX-6YM4T-73SGJ-VTVPS-I7FX3");
	}
}
