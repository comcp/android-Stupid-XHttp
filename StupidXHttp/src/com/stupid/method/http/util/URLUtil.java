package com.stupid.method.http.util;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class URLUtil {

	/** url and para separator **/
	public static final String URL_AND_PARA_SEPARATOR = "?";
	/** parameters separator **/
	public static final String PARAMETERS_SEPARATOR = "&";
	/** paths separator **/
	public static final String PATHS_SEPARATOR = "/";
	/** equal sign **/
	public static final String EQUAL_SIGN = "=";

	private URLUtil() {
		throw new AssertionError();
	}

	/**
	 * join url and paras
	 * 
	 * <pre>
	 * getUrlWithParas(null, {(a, b)})                        =   "?a=b";
	 * getUrlWithParas("baidu.com", {})                       =   "baidu.com";
	 * getUrlWithParas("baidu.com", {(a, b), (i, j)})         =   "baidu.com?a=b&i=j";
	 * getUrlWithParas("baidu.com", {(a, b), (i, j), (c, d)}) =   "baidu.com?a=b&i=j&c=d";
	 * getUrlWithParas("baidu.com?c=d", {(a, b), (i, j), (c, d)}) =   "baidu.com?c=d&a=b&i=j&c=d";
	 * </pre>
	 * 
	 * @param url
	 *            url
	 * @param parasMap
	 *            paras map, key is para name, value is para value
	 * @return if url is null, process it as empty string
	 */
	public static String getUrlWithParas(String url,
			Map<String, ? extends Object> parasMap) {

		StringBuilder urlWithParas = new StringBuilder(
				StringUtils.isEmpty(url) ? ""
						: url.indexOf(URL_AND_PARA_SEPARATOR) == -1 ? url
								+ URL_AND_PARA_SEPARATOR
								: (url.endsWith(PARAMETERS_SEPARATOR) ? url
										: url + PARAMETERS_SEPARATOR));

		String paras = joinParas(parasMap);
		if (!StringUtils.isEmpty(paras)) {

			urlWithParas.append(paras);
		}
		return urlWithParas.toString();
	}

	/**
	 * join url and encoded paras
	 * 
	 * @param url
	 * @param parasMap
	 * @return
	 * @see #getUrlWithParas(String, Map)
	 * @see StringUtils#utf8Encode(String)
	 */
	public static String getUrlWithValueEncodeParas(String url,
			Map<String, String> parasMap) {
		StringBuilder urlWithParas = new StringBuilder(
				StringUtils.isEmpty(url) ? "" : url);
		String paras = joinParasWithEncodedValue(parasMap);
		if (!StringUtils.isEmpty(paras)) {
			urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
		}
		return urlWithParas.toString();
	}

	/**
	 * join paras
	 * 
	 * @param parasMap
	 *            paras map, key is para name, value is para value
	 * @return join key and value with {@link #EQUAL_SIGN}, join keys with
	 *         {@link #PARAMETERS_SEPARATOR}
	 */
	public static String joinParas(Map<String, ? extends Object> parasMap) {
		if (parasMap == null || parasMap.size() == 0) {
			return null;
		}

		StringBuilder paras = new StringBuilder();
		Iterator<?> ite = parasMap.entrySet().iterator();
		while (ite.hasNext()) {
			Map.Entry entry = (Entry) ite.next();
			paras.append(entry.getKey()).append(EQUAL_SIGN)
					.append(entry.getValue());
			if (ite.hasNext()) {
				paras.append(PARAMETERS_SEPARATOR);
			}
		}
		return paras.toString();
	}

	/**
	 * join paras with encoded value
	 * 
	 * @param parasMap
	 * @return
	 * @see #joinParas(Map)
	 * @see StringUtils#utf8Encode(String)
	 */
	public static String joinParasWithEncodedValue(Map<String, String> parasMap) {
		StringBuilder paras = new StringBuilder("");
		if (parasMap != null && parasMap.size() > 0) {
			Iterator<Map.Entry<String, String>> ite = parasMap.entrySet()
					.iterator();
			try {
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite
							.next();
					paras.append(entry.getKey()).append(EQUAL_SIGN)
							.append(StringUtils.utf8Encode(entry.getValue()));
					if (ite.hasNext()) {
						paras.append(PARAMETERS_SEPARATOR);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paras.toString();
	}

	/**
	 * append a key and value pair to url
	 * 
	 * @param url
	 * @param paraKey
	 * @param paraValue
	 * @return
	 */
	public static String appendParaToUrl(String url, String paraKey,
			String paraValue) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}

		StringBuilder sb = new StringBuilder(url);
		if (!url.contains(URL_AND_PARA_SEPARATOR)) {
			sb.append(URL_AND_PARA_SEPARATOR);
		} else {
			sb.append(PARAMETERS_SEPARATOR);
		}
		return sb.append(paraKey).append(EQUAL_SIGN).append(paraValue)
				.toString();
	}

	private static final SimpleDateFormat GMT_FORMAT = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);

	/**
	 * parse gmt time to long
	 * 
	 * @param gmtTime
	 *            likes Thu, 11 Apr 2013 10:20:30 GMT
	 * @return -1 represents exception otherwise time in milliseconds
	 */
	public static long parseGmtTime(String gmtTime) {
		try {
			return GMT_FORMAT.parse(gmtTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
