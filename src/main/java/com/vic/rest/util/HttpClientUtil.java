
package com.vic.rest.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	/**
	 * @method:doGet
	 * @description:請求GET方法
	 * @author:   
	 * @date: -30
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doGet(String url, Map<String, String> param) {

		// 創建Httpclient對象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 創建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 創建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 執行請求
			response = httpclient.execute(httpGet);
			// 判斷返回狀態碼是否為200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * @method:doGet
	 * @description:請求GET方法
	 * @author:   
	 * @date: -30
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * @method:doPost
	 * @description:請求POST方法
	 * @author:   
	 * @date: -30
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 創建Httpclient對象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 創建Http Post請求
			HttpPost httpPost = new HttpPost(url);
			// 創建參數列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模擬表單
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 執行http請求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
	
	/**
	 * @method:doPostJson
	 * @description:doPostJson
	 * @author:   
	 * @date: -30
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPostJson(String url, String json) {
		// 創建Httpclient對象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 創建Http Post請求
			HttpPost httpPost = new HttpPost(url);
			// 創建請求內容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 執行http請求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}

	/**
	 * @method:doPost
	 * @description:請求POST方法
	 * @author:   
	 * @date: -30
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	/**
	 * @method:doPut
	 * @description:請求PUT方法
	 * @author:   
	 * @date: -02
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPut(String url, Map<String, String> param) {
		// 創建Httpclient對象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 創建Http Put請求
			HttpPut httpPut = new HttpPut(url);
			// 創建參數列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模擬表單
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPut.setEntity(entity);
			}
			// 執行http請求
			response = httpClient.execute(httpPut);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
	
	/**
	 * @method:doPutJson
	 * @description:請求PUT方法
	 * @author:   
	 * @date: -02
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPutJson(String url, String json) {
		// 創建Httpclient對象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 創建Http Post請求
			HttpPut httpPut = new HttpPut(url);
			// 創建請求內容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPut.setEntity(entity);
			// 執行http請求
			response = httpClient.execute(httpPut);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}

	/**
	 * @method:doPut
	 * @description:請求PUT方法
	 * @author:   
	 * @date: -02
	 * @param url
	 * @return
	 */
	public static String doPut(String url) {
		return doPut(url, null);
	}
	
	/**
	 * @method:doGet
	 * @description:請求DELETE方法
	 * @author:   
	 * @date: -02
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doDelete(String url, Map<String, String> param) {

		// 創建Httpclient對象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 創建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 創建http DELETE请求
			HttpDelete httpDelete = new HttpDelete(uri);

			// 執行請求
			response = httpclient.execute(httpDelete);
			// 判斷返回狀態碼是否為200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * @method:doGet
	 * @description:請求DELETE方法
	 * @author:   
	 * @date: -02
	 * @param url
	 * @return
	 */
	public static String doDelete(String url) {
		return doDelete(url, null);
	}
	
}
