/**  
 * Description:  HttpClient帮助工具包
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	/** UTF-8编码 */
	private final static String CHARSET_UTF8 = "UTF-8";
	/** 重试次数 */
	private final static int EXECUTION_COUNT = 3;
	/** SSL连接协议头 */
	private final static String SSL_DEFAULT_SCHEME = "https";
	/** SSL连接协议端口 */
	private final static int SSL_DEFAULT_PORT = 443;
	/** 默认HttpClient对象 */
	//private static HttpClient DefaultHttpClient = null;
	/** 代理端口 */
	private final static Integer PROXY_PORT = 3128;
	/** 代理IP */
	private final static String PROXY_IP = null;

	private static final int DEFAULT_TIME_OUT = 20 * 1000;

	/**
	 * 使用GET方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String getHTML(String url) throws ClientProtocolException,
			IOException {
		return getHTML(url, null, null);
	}

	/**
	 * 使用GET方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String getHTML(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		return getHTML(url, params, null);
	}

	/**
	 * 使用POST方式 提交multipar内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @param urlEncode
	 *            URL编码
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String multipartPostHTML(String url,
			Map<String, Object> params, String fileName)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		// post.addHeader("Content-Type", "multipart/form-data");
		if (params != null) {
			MultipartEntity entity = new MultipartEntity();
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, Object> map : params.entrySet()) {
					Object obj = map.getValue();
					if (obj instanceof File) {
						FileBodyEx file = new FileBodyEx((File) obj, fileName);
						/*
						 * FileInputStream input = new
						 * FileInputStream((File)obj); InputStreamBody file =
						 * new InputStreamBody(input,fileName);
						 */entity.addPart(map.getKey(), file);
					} else if (obj instanceof byte[]) {
						byte[] data = (byte[]) obj;
						InputStream input = new ByteArrayInputStream(data);
						InputStreamBody file = new InputStreamBodyEx(input,
								fileName, data.length);
						entity.addPart(map.getKey(), file);
					} else {
						entity.addPart(map.getKey(), new StringBody(
								(String) map.getValue()));
					}
				}
			}

			post.setEntity(entity);

		}
		HttpResponse response = getDefaultHttpClient().execute(post);
		return readHTML(response);
	}

	/**
	 * 使用GET方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @param urlEncode
	 *            URL编码
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String getHTML(String url, Map<String, String> params,
			String urlEncode) throws ClientProtocolException, IOException {
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams.size() > 0) {
			urlEncode = (urlEncode == null ? CHARSET_UTF8 : urlEncode);
			String formatParams = URLEncodedUtils.format(qparams, urlEncode);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		HttpGet get = new HttpGet(url);
		HttpResponse response = getDefaultHttpClient().execute(get);
		return readHTML(response);
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @param urlEncode
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getHTMLWithProxy(String url,
			Map<String, String> params, String urlEncode)
			throws ClientProtocolException, IOException {
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams.size() > 0) {
			urlEncode = (urlEncode == null ? CHARSET_UTF8 : urlEncode);
			String formatParams = URLEncodedUtils.format(qparams, urlEncode);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		HttpGet get = new HttpGet(url);
		HttpResponse response = getHttpClientWithProxy().execute(get);
		return readHTML(response);
	}

	/**
	 * 使用POST方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String postHTML(String url) throws ClientProtocolException,
			IOException {
		return postHTML(url, null, null);
	}

	/**
	 * 使用POST方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String postHTML(String url, Map<String, String> params)
			throws ClientProtocolException, IOException {
		return postHTML(url, params, null);
	}

	/**
	 * 使用POST方式获取网页的内容
	 * 
	 * @param url
	 *            网络资源地址
	 * @param params
	 *            参数集合
	 * @param urlEncode
	 *            URL编码
	 * @return 网页的内容（如果页面返回状态非正常返回null）
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	public static String postHTML(String url, Map<String, String> params,
			String urlEncode) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		if (params != null) {
			UrlEncodedFormEntity formEntity = null;
			if (urlEncode == null) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						urlEncode);
			}
			post.setEntity(formEntity);
		}
		HttpResponse response = getDefaultHttpClient().execute(post);
		return readHTML(response);
	}

	/**
	 * SSL安全连接的POST方式提交,忽略URL中包含的参数,解决SSL双向数字证书认证
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param urlEncode
	 *            URL编码
	 * @param keystoreUrl
	 *            密钥存储库路径
	 * @param keystorePassword
	 *            密钥存储库访问密码
	 * @param truststoreUrl
	 *            信任存储库绝路径
	 * @param truststorePassword
	 *            信任存储库访问密码, 可为null
	 * @return 响应消息
	 * @throws ClientProtocolException
	 *             打开网页时引发的异常
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 * @throws RuntimeException
	 *             错误信息在mssage里有详细描述
	 */
	public static String sslPostHTML(String url, Map<String, String> params,
			String urlEncode, final URL keystoreUrl,
			final String keystorePassword, final URL truststoreUrl,
			final String truststorePassword) throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		if (params != null) {
			UrlEncodedFormEntity formEntity = null;
			if (urlEncode == null) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						urlEncode);
			}
			post.setEntity(formEntity);
		}
		HttpClient httpclient = getDefaultHttpClient();
		try {
			KeyStore keyStore = createKeyStore(keystoreUrl, keystorePassword);
			KeyStore trustStore = createKeyStore(truststoreUrl,
					keystorePassword);
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
					keystorePassword, trustStore);
			Scheme scheme = new Scheme(SSL_DEFAULT_SCHEME, socketFactory,
					SSL_DEFAULT_PORT);
			httpclient.getConnectionManager().getSchemeRegistry().register(
					scheme);
			return httpclient.execute(post, responseHandler);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("指定的加密算法不可用", e);
		} catch (KeyStoreException e) {
			throw new RuntimeException("keytore解析异常", e);
		} catch (CertificateException e) {
			throw new RuntimeException("信任证书过期或解析异常", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("keystore文件不存在", e);
		} catch (IOException e) {
			throw new RuntimeException("I/O操作失败或中断 ", e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException("keystore中的密钥无法恢复异常", e);
		} catch (KeyManagementException e) {
			throw new RuntimeException("处理密钥管理的操作异常", e);
		}
	}

	/**
	 * 清除换行符
	 * 
	 * @param html
	 *            要清除换行符的内容
	 * @return 清除后的内容
	 */
	public static String cleanFinefeed(String html) {
		return html.replaceAll("\r\n", "").replaceAll("\n", "");
	}

	/**
	 * 获取页面编码格式（如果页面没有指定编码格式，默认返回utf-8编码。）<br> 
	 * 〈功能详细描述〉
	 *
	 * @param response 页面请求的结果信息
	 * @return 编码格式，获取失败返回null
	 */
	public static String getCharset(final HttpResponse response) {
		String charset = null;
		Header[] headers = response.getHeaders("Content-Type");
		if (headers.length > 0) {
			for (Header header : headers) {
				Pattern p = Pattern
						.compile("charset=\"*?([0-9a-zA-Z-]{1,})\"*?");
				Matcher m = p.matcher(header.getValue());
				if (m.find()) {
					charset = m.group(1);
					return charset;
				}
			}
		}
		return charset;
	}

	/**
	 * 获取页面编码格式
	 * 
	 * @param htmlBytes
	 *            页面字节
	 * @return 编码格式，获取失败返回null
	 */
	public static String getCharset(byte[] htmlBytes) {
		String charset = null;
		Pattern p = Pattern
				.compile("<meta .*? charset=\"*?([0-9a-zA-Z-]{1,})\"*?.*?>");
		Matcher m = p.matcher(new String(htmlBytes).toLowerCase());
		if (m.find()) {
			charset = m.group(1);
		}
		return charset;
	}

	/**
	 * 读取HTML内容
	 * 
	 * @param response
	 *            返回结果
	 * @return HTML
	 * @throws IOException
	 *             访问网络流信息时引发的异常
	 */
	private static String readHTML(final HttpResponse response)
			throws IOException {
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		} else {
			byte[] htmlBytes = EntityUtils.toByteArray(response.getEntity());
			String charset = getCharset(response);
			charset = charset == null ? getCharset(htmlBytes) : charset;
			charset = charset == null ? CHARSET_UTF8 : charset;
			return new String(htmlBytes, charset);
		}
	}

	/**
	 * 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			return readHTML(response);
		}
	};
	/** 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= EXECUTION_COUNT) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};

	/**
	 * 获取默认HttpClient对象
	 * 
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static HttpClient getDefaultHttpClient() {
		HttpClient DefaultHttpClient = null;
		if (DefaultHttpClient == null) {
			DefaultHttpClient = new DefaultHttpClient();
			// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.USER_AGENT,
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET, CHARSET_UTF8);
			DefaultHttpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_TIME_OUT);
			DefaultHttpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, DEFAULT_TIME_OUT);
			((DefaultHttpClient) DefaultHttpClient)
					.setHttpRequestRetryHandler(requestRetryHandler);
			// 对HttpClient对象设置代理
			if (PROXY_PORT != null && PROXY_IP != null) {
				HttpHost proxy = new HttpHost(PROXY_IP, PROXY_PORT);
				DefaultHttpClient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
		}
		return DefaultHttpClient;
	}

	/**
	 * 获取(有代理)HttpClient对象 addby chenjunfeng 2010-08030
	 * 
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static HttpClient getHttpClientWithProxy() {
		HttpClient DefaultHttpClient = null;
		if (DefaultHttpClient == null) {
			DefaultHttpClient = new DefaultHttpClient();
			// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.USER_AGENT,
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			DefaultHttpClient.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET, CHARSET_UTF8);
			DefaultHttpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_TIME_OUT);
			DefaultHttpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, DEFAULT_TIME_OUT);
			((DefaultHttpClient) DefaultHttpClient)
					.setHttpRequestRetryHandler(requestRetryHandler);
			// 对HttpClient对象设置代理
			if (PROXY_PORT != null && PROXY_IP != null) {
				HttpHost proxy = new HttpHost(PROXY_IP, PROXY_PORT);
				DefaultHttpClient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			((DefaultHttpClient) DefaultHttpClient)
					.setHttpRequestRetryHandler(requestRetryHandler);
		}
		return DefaultHttpClient;
	}

	/**
	 * 从给定的路径中加载此 KeyStore
	 * 
	 * @param url
	 *            URL路径
	 * @param password
	 *            访问密钥
	 * @return keystore 对象
	 */
	private static KeyStore createKeyStore(final URL url, final String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		if (url == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = null;
		try {
			is = url.openStream();
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
		}
		return keystore;
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair 参数集
	 */
	private static List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (paramsMap != null && paramsMap.size() > 0) {
			for (Map.Entry<String, String> map : paramsMap.entrySet()) {
				params
						.add(new BasicNameValuePair(map.getKey(), map
								.getValue()));
			}
		}
		return params;
	}

	/**
	 * 以get方式取json格式的数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getJsonData(String url, Map<String, Object> params)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_TIME_OUT);
		try {
			if (params != null) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entity : params.entrySet()) {
					BasicNameValuePair pare = new BasicNameValuePair(entity
							.getKey(), entity.getValue().toString());
					values.add(pare);
				}
				String str = URLEncodedUtils.format(values, "UTF-8");
				if (url.indexOf("?") > -1) {
					url += "&" + str;
				} else {
					url += "?" + str;
				}
			}
			//LogUtils.getLogger(HttpClientUtils.class).debug(url);
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			//TODO 处理异常或Log日志记录 陈钊 2011-10-28
			return null;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 以post方式取json格式的数据 chenjufeng 转自 彪哥 2010-07-14
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
     * @see 
     * @since [1.0]
	 */
	public static String postJsonData(String url, Map<String, ?> params)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_TIME_OUT);
		try {
			HttpPost httpPost = new HttpPost(url);

			if (params != null) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, ?> entity : params.entrySet()) {
					BasicNameValuePair pare = new BasicNameValuePair(entity
							.getKey(), entity.getValue().toString());
					values.add(pare);
				}

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values,
						"UTF-8");
				httpPost.setEntity(entity);
			}
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpPost, responseHandler);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static enum RespTypes {
		stream, string
	}

	public static Object doGet(String url, RespTypes type) throws Exception {
		return doGet(url, null, null, type);
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @param urlEncode
	 * @param type
	 *            方法返回类型枚举
	 * @return 流或者字符串中的一种
	 * @throws Exception
	 */
	public static Object doGet(String url, Map<String, String> params,
			String urlEncode, RespTypes type) throws Exception {
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams.size() > 0) {
			urlEncode = (urlEncode == null ? CHARSET_UTF8 : urlEncode);
			String formatParams = URLEncodedUtils.format(qparams, urlEncode);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		HttpGet get = new HttpGet(url);
		HttpResponse response = getDefaultHttpClient().execute(get);
		if (type == RespTypes.string)
			return readHTML(response);
		else {
			if (response.getStatusLine().getStatusCode() == 200)
				return response.getEntity().getContent();
			return null;
		}
	}

	/**
	 * 商城接口访问 
	 * @param url
	 * @return
	 */
	public static int httpClientExecute(String url)  throws Exception{
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		HttpMethod method = new GetMethod(url);
		return client.executeMethod(method);
	}
	/**
	 * 商城接口访问 
	 * @param url
	 * @return
	 */
	public static String httpClientExecuteMethod(String url) {
		
		HttpMethod method=null;
		try {
			org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
			method= new GetMethod(url);
			client.executeMethod(method);
			return method.getResponseBodyAsString();
		} catch (UnsupportedEncodingException e) {
		} catch (HttpException e) {
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param url
	 * @param param
	 * @param encode
	 * @return
	 * @throws IOException

	 */
	public static String httpPost(String url,String param,String encode) throws IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader("Content-Type","text/html;charset="+encode);
		List<NameValuePair> values = new ArrayList<NameValuePair>();
		BasicNameValuePair pare = new BasicNameValuePair("Version",param.substring(7));
		values.add(pare);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(values, encode);
		httpPost.setEntity(formEntity);
//		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values,"UTF-8");
//		httpPost.setEntity(entity);
//		ResponseHandler<String> responseHandler = new BasicResponseHandler();
//		httpclient.execute(httpPost, responseHandler);
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		StringBuilder retvl = new StringBuilder();
		System.out.print(url + "?");
		
		try {
			if (entity != null) {
//			    InputStream instream = entity.getContent();
			    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
			    String line;
				while ((line = in.readLine()) != null) {
					retvl.append(line);
				}
			}
			
			} catch (RuntimeException ex) {
				// 终止执行请求
				httpPost.abort();
				throw ex;
			}
		return retvl.toString();
	}

}
    