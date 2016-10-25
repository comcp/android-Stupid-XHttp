package com.stupid.method.http.util;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContentType {

	private static final Pattern TYPE_SUBTYPE = Pattern
			.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
	private static final Pattern PARAMETER = Pattern
			.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
	private final String mediaType;
	private final String type;
	private final String subtype;
	private final String charset;

	private ContentType(String mediaType, String type, String subtype,
			String charset) {
		this.mediaType = mediaType;
		this.type = type;
		this.subtype = subtype;
		this.charset = charset;
	}

	static final Map<String, String> types;
	static {
		types = new HashMap<String, String>();
		types.put(".001", "application/x-001");
		types.put(".323", "text/h323");
		types.put(".907", "drawing/907");
		types.put(".acp", "audio/x-mei-aac");
		types.put(".aif", "audio/aiff");
		types.put(".aiff", "audio/aiff");
		types.put(".asa", "text/asa");
		types.put(".asp", "text/asp");
		types.put(".au", "audio/basic");
		types.put(".awf", "application/vnd.adobe.workflow");
		types.put(".bmp", "application/x-bmp");
		types.put(".c4t", "application/x-c4t");
		types.put(".cal", "application/x-cals");
		types.put(".cdf", "application/x-netcdf");
		types.put(".cel", "application/x-cel");
		types.put(".cg4", "application/x-g4");
		types.put(".cit", "application/x-cit");
		types.put(".cml", "text/xml");
		types.put(".cmx", "application/x-cmx");
		types.put(".crl", "application/pkix-crl");
		types.put(".csi", "application/x-csi");
		types.put(".cut", "application/x-cut");
		types.put(".dbm", "application/x-dbm");
		types.put(".dcd", "text/xml");
		types.put(".der", "application/x-x509-ca-cert");
		types.put(".dib", "application/x-dib");
		types.put(".doc", "application/msword");
		types.put(".drw", "application/x-drw");
		types.put(".dwf", "Model/vnd.dwf");
		types.put(".dwg", "application/x-dwg");
		types.put(".dxf", "application/x-dxf");
		types.put(".emf", "application/x-emf");
		types.put(".ent", "text/xml");
		types.put(".eps", "application/x-ps");
		types.put(".etd", "application/x-ebx");
		types.put(".fax", "image/fax");
		types.put(".fif", "application/fractals");
		types.put(".frm", "application/x-frm");
		types.put(".gbr", "application/x-gbr");
		types.put(".gif", "image/gif");
		types.put(".gp4", "application/x-gp4");
		types.put(".hmr", "application/x-hmr");
		types.put(".hpl", "application/x-hpl");
		types.put(".hrf", "application/x-hrf");
		types.put(".htc", "text/x-component");
		types.put(".html", "text/html");
		types.put(".htx", "text/html");
		types.put(".ico", "image/x-icon");
		types.put(".iff", "application/x-iff");
		types.put(".igs", "application/x-igs");
		types.put(".img", "application/x-img");
		types.put(".isp", "application/x-internet-signup");
		types.put(".java", "java/*");
		types.put(".jpe", "image/jpeg");
		types.put(".jpeg", "image/jpeg");
		types.put(".jpg", "application/x-jpg");
		types.put(".jsp", "text/html");
		types.put(".lar", "application/x-laplayer-reg");
		types.put(".lavs", "audio/x-liquid-secure");
		types.put(".lmsff", "audio/x-la-lms");
		types.put(".ltr", "application/x-ltr");
		types.put(".m2v", "video/x-mpeg");
		types.put(".m4e", "video/mpeg4");
		types.put(".man", "application/x-troff-man");
		types.put(".mdb", "application/msaccess");
		types.put(".mfp", "application/x-shockwave-flash");
		types.put(".mhtml", "message/rfc822");
		types.put(".mid", "audio/mid");
		types.put(".mil", "application/x-mil");
		types.put(".mnd", "audio/x-musicnet-download");
		types.put(".mocha", "application/x-javascript");
		types.put(".mp1", "audio/mp1");
		types.put(".mp2v", "video/mpeg");
		types.put(".mp4", "video/mpeg4");
		types.put(".mpd", "application/vnd.ms-project");
		types.put(".mpeg", "video/mpg");
		types.put(".mpga", "audio/rn-mpeg");
		types.put(".mps", "video/x-mpeg");
		types.put(".mpv", "video/mpg");
		types.put(".mpw", "application/vnd.ms-project");
		types.put(".mtx", "text/xml");
		types.put(".net", "image/pnetvue");
		types.put(".nws", "message/rfc822");
		types.put(".out", "application/x-out");
		types.put(".p12", "application/x-pkcs12");
		types.put(".p7c", "application/pkcs7-mime");
		types.put(".p7r", "application/x-pkcs7-certreqresp");
		types.put(".pc5", "application/x-pc5");
		types.put(".pcl", "application/x-pcl");
		types.put(".pdf", "application/pdf");
		types.put(".pdx", "application/vnd.adobe.pdx");
		types.put(".pgl", "application/x-pgl");
		types.put(".pko", "application/vnd.ms-pki.pko");
		types.put(".plg", "text/html");
		types.put(".plt", "application/x-plt");
		types.put(".png", "application/x-png");
		types.put(".ppa", "application/vnd.ms-powerpoint");
		types.put(".pps", "application/vnd.ms-powerpoint");
		types.put(".ppt", "application/x-ppt");
		types.put(".prf", "application/pics-rules");
		types.put(".prt", "application/x-prt");
		types.put(".ps", "application/postscript");
		types.put(".pwz", "application/vnd.ms-powerpoint");
		types.put(".ra", "audio/vnd.rn-realaudio");
		types.put(".ras", "application/x-ras");
		types.put(".rdf", "text/xml");
		types.put(".red", "application/x-red");
		types.put(".rjs", "application/vnd.rn-realsystem-rjs");
		types.put(".rlc", "application/x-rlc");
		types.put(".rm", "application/vnd.rn-realmedia");
		types.put(".rmi", "audio/mid");
		types.put(".rmm", "audio/x-pn-realaudio");
		types.put(".rms", "application/vnd.rn-realmedia-secure");
		types.put(".rmx", "application/vnd.rn-realsystem-rmx");
		types.put(".rp", "image/vnd.rn-realpix");
		types.put(".rsml", "application/vnd.rn-rsml");
		types.put(".rtf", "application/msword");
		types.put(".rv", "video/vnd.rn-realvideo");
		types.put(".sat", "application/x-sat");
		types.put(".sdw", "application/x-sdw");
		types.put(".slb", "application/x-slb");
		types.put(".slk", "drawing/x-slk");
		types.put(".smil", "application/smil");
		types.put(".snd", "audio/basic");
		types.put(".sor", "text/plain");
		types.put(".spl", "application/futuresplash");
		types.put(".ssm", "application/streamingmedia");
		types.put(".stl", "application/vnd.ms-pki.stl");
		types.put(".sty", "application/x-sty");
		types.put(".swf", "application/x-shockwave-flash");
		types.put(".tg4", "application/x-tg4");
		types.put(".tif", "image/tiff");
		types.put(".tiff", "image/tiff");
		types.put(".top", "drawing/x-top");
		types.put(".tsd", "text/xml");
		types.put(".uin", "application/x-icq");
		types.put(".vcf", "text/x-vcard");
		types.put(".vdx", "application/vnd.visio");
		types.put(".vpg", "application/x-vpeg005");
		types.put(".vsd", "application/x-vsd");
		types.put(".vst", "application/vnd.visio");
		types.put(".vsw", "application/vnd.visio");
		types.put(".vtx", "application/vnd.visio");
		types.put(".wav", "audio/wav");
		types.put(".wb1", "application/x-wb1");
		types.put(".wb3", "application/x-wb3");
		types.put(".wiz", "application/msword");
		types.put(".wk4", "application/x-wk4");
		types.put(".wks", "application/x-wks");
		types.put(".wma", "audio/x-ms-wma");
		types.put(".wmf", "application/x-wmf");
		types.put(".wmv", "video/x-ms-wmv");
		types.put(".wmz", "application/x-ms-wmz");
		types.put(".wpd", "application/x-wpd");
		types.put(".wpl", "application/vnd.ms-wpl");
		types.put(".wr1", "application/x-wr1");
		types.put(".wrk", "application/x-wrk");
		types.put(".ws2", "application/x-ws");
		types.put(".wsdl", "text/xml");
		types.put(".xdp", "application/vnd.adobe.xdp");
		types.put(".xfd", "application/vnd.adobe.xfd");
		types.put(".xhtml", "text/html");
		types.put(".xls", "application/x-xls");
		types.put(".xml", "text/xml");
		types.put(".xq", "text/xml");
		types.put(".xquery", "text/xml");
		types.put(".xsl", "text/xml");
		types.put(".xwd", "application/x-xwd");
		types.put(".sis", "application/vnd.symbian.install");
		types.put(".x_t", "application/x-x_t");
		types.put(".apk", "application/vnd.android.package-archive");
		types.put(".tif", "image/tiff");
		types.put(".301", "application/x-301");
		types.put(".906", "application/x-906");
		types.put(".a11", "application/x-a11");
		types.put(".ai", "application/postscript");
		types.put(".aifc", "audio/aiff");
		types.put(".anv", "application/x-anv");
		types.put(".asf", "video/x-ms-asf");
		types.put(".asx", "video/x-ms-asf");
		types.put(".avi", "video/avi");
		types.put(".biz", "text/xml");
		types.put(".bot", "application/x-bot");
		types.put(".c90", "application/x-c90");
		types.put(".cat", "application/vnd.ms-pki.seccat");
		types.put(".cdr", "application/x-cdr");
		types.put(".cer", "application/x-x509-ca-cert");
		types.put(".cgm", "application/x-cgm");
		types.put(".class", "java/*");
		types.put(".cmp", "application/x-cmp");
		types.put(".cot", "application/x-cot");
		types.put(".crt", "application/x-x509-ca-cert");
		types.put(".css", "text/css");
		types.put(".dbf", "application/x-dbf");
		types.put(".dbx", "application/x-dbx");
		types.put(".dcx", "application/x-dcx");
		types.put(".dgn", "application/x-dgn");
		types.put(".dll", "application/x-msdownload");
		types.put(".dot", "application/msword");
		types.put(".dtd", "text/xml");
		types.put(".dwf", "application/x-dwf");
		types.put(".dxb", "application/x-dxb");
		types.put(".edn", "application/vnd.adobe.edn");
		types.put(".eml", "message/rfc822");
		types.put(".epi", "application/x-epi");
		types.put(".eps", "application/postscript");
		types.put(".exe", "application/x-msdownload");
		types.put(".fdf", "application/vnd.fdf");
		types.put(".fo", "text/xml");
		types.put(".g4", "application/x-g4");
		types.put(".gl2", "application/x-gl2");
		types.put(".hgl", "application/x-hgl");
		types.put(".hpg", "application/x-hpgl");
		types.put(".hqx", "application/mac-binhex40");
		types.put(".hta", "application/hta");
		types.put(".htm", "text/html");
		types.put(".htt", "text/webviewhtml");
		types.put(".icb", "application/x-icb");
		types.put(".ico", "application/x-ico");
		types.put(".ig4", "application/x-g4");
		types.put(".iii", "application/x-iphone");
		types.put(".ins", "application/x-internet-signup");
		types.put(".IVF", "video/x-ivf");
		types.put(".jfif", "image/jpeg");
		types.put(".jpe", "application/x-jpe");
		types.put(".jpg", "image/jpeg");
		types.put(".js", "application/x-javascript");
		types.put(".la1", "audio/x-liquid-file");
		types.put(".latex", "application/x-latex");
		types.put(".lbm", "application/x-lbm");
		types.put(".ls", "application/x-javascript");
		types.put(".m1v", "video/x-mpeg");
		types.put(".m3u", "audio/mpegurl");
		types.put(".mac", "application/x-mac");
		types.put(".math", "text/xml");
		types.put(".mdb", "application/x-mdb");
		types.put(".mht", "message/rfc822");
		types.put(".mi", "application/x-mi");
		types.put(".midi", "audio/mid");
		types.put(".mml", "text/xml");
		types.put(".mns", "audio/x-musicnet-stream");
		types.put(".movie", "video/x-sgi-movie");
		types.put(".mp2", "audio/mp2");
		types.put(".mp3", "audio/mp3");
		types.put(".mpa", "video/x-mpg");
		types.put(".mpe", "video/x-mpeg");
		types.put(".mpg", "video/mpg");
		types.put(".mpp", "application/vnd.ms-project");
		types.put(".mpt", "application/vnd.ms-project");
		types.put(".mpv2", "video/mpeg");
		types.put(".mpx", "application/vnd.ms-project");
		types.put(".mxp", "application/x-mmxp");
		types.put(".nrf", "application/x-nrf");
		types.put(".odc", "text/x-ms-odc");
		types.put(".p10", "application/pkcs10");
		types.put(".p7b", "application/x-pkcs7-certificates");
		types.put(".p7m", "application/pkcs7-mime");
		types.put(".p7s", "application/pkcs7-signature");
		types.put(".pci", "application/x-pci");
		types.put(".pcx", "application/x-pcx");
		types.put(".pdf", "application/pdf");
		types.put(".pfx", "application/x-pkcs12");
		types.put(".pic", "application/x-pic");
		types.put(".pl", "application/x-perl");
		types.put(".pls", "audio/scpls");
		types.put(".png", "image/png");
		types.put(".pot", "application/vnd.ms-powerpoint");
		types.put(".ppm", "application/x-ppm");
		types.put(".ppt", "application/vnd.ms-powerpoint");
		types.put(".pr", "application/x-pr");
		types.put(".prn", "application/x-prn");
		types.put(".ps", "application/x-ps");
		types.put(".ptn", "application/x-ptn");
		types.put(".r3t", "text/vnd.rn-realtext3d");
		types.put(".ram", "audio/x-pn-realaudio");
		types.put(".rat", "application/rat-file");
		types.put(".rec", "application/vnd.rn-recording");
		types.put(".rgb", "application/x-rgb");
		types.put(".rjt", "application/vnd.rn-realsystem-rjt");
		types.put(".rle", "application/x-rle");
		types.put(".rmf", "application/vnd.adobe.rmf");
		types.put(".rmj", "application/vnd.rn-realsystem-rmj");
		types.put(".rmp", "application/vnd.rn-rn_music_package");
		types.put(".rmvb", "application/vnd.rn-realmedia-vbr");
		types.put(".rnx", "application/vnd.rn-realplayer");
		types.put(".rpm", "audio/x-pn-realaudio-plugin");
		types.put(".rt", "text/vnd.rn-realtext");
		types.put(".rtf", "application/x-rtf");
		types.put(".sam", "application/x-sam");
		types.put(".sdp", "application/sdp");
		types.put(".sit", "application/x-stuffit");
		types.put(".sld", "application/x-sld");
		types.put(".smi", "application/smil");
		types.put(".smk", "application/x-smk");
		types.put(".sol", "text/plain");
		types.put(".spc", "application/x-pkcs7-certificates");
		types.put(".spp", "text/xml");
		types.put(".sst", "application/vnd.ms-pki.certstore");
		types.put(".stm", "text/html");
		types.put(".svg", "text/xml");
		types.put(".tdf", "application/x-tdf");
		types.put(".tga", "application/x-tga");
		types.put(".tif", "application/x-tif");
		types.put(".tld", "text/xml");
		types.put(".torrent", "application/x-bittorrent");
		types.put(".txt", "text/plain");
		types.put(".uls", "text/iuls");
		types.put(".vda", "application/x-vda");
		types.put(".vml", "text/xml");
		types.put(".vsd", "application/vnd.visio");
		types.put(".vss", "application/vnd.visio");
		types.put(".vst", "application/x-vst");
		types.put(".vsx", "application/vnd.visio");
		types.put(".vxml", "text/xml");
		types.put(".wax", "audio/x-ms-wax");
		types.put(".wb2", "application/x-wb2");
		types.put(".wbmp", "image/vnd.wap.wbmp");
		types.put(".wk3", "application/x-wk3");
		types.put(".wkq", "application/x-wkq");
		types.put(".wm", "video/x-ms-wm");
		types.put(".wmd", "application/x-ms-wmd");
		types.put(".wml", "text/vnd.wap.wml");
		types.put(".wmx", "video/x-ms-wmx");
		types.put(".wp6", "application/x-wp6");
		types.put(".wpg", "application/x-wpg");
		types.put(".wq1", "application/x-wq1");
		types.put(".wri", "application/x-wri");
		types.put(".ws", "application/x-ws");
		types.put(".wsc", "text/scriptlet");
		types.put(".wvx", "video/x-ms-wvx");
		types.put(".xdr", "text/xml");
		types.put(".xfdf", "application/vnd.adobe.xfdf");
		types.put(".xls", "application/vnd.ms-excel");
		types.put(".xlw", "application/x-xlw");
		types.put(".xpl", "audio/scpls");
		types.put(".xql", "text/xml");
		types.put(".xsd", "text/xml");
		types.put(".xslt", "text/xml");
		types.put(".x_b", "application/x-x_b");
		types.put(".sisx", "application/vnd.symbian.install");
		types.put(".ipa", "application/vnd.iphone");
		types.put(".xap", "application/x-silverlight-app");

	}

	/**
	 * 添加content type
	 * 
	 * @param key
	 * @param value
	 */
	public static void addContentType(String key, String value) {
		types.put(key, value);
	}

	/**
	 * 移除content type
	 * 
	 * @param key
	 * @return
	 */
	public static String removeContentType(String key) {
		return types.remove(key);
	}

	/**
	 * <pre>
	 * getContentTyep(".txt") return "text/plain"
	 * getContentTyep(".ajaalalskkljsdf") return "application/octet-stream"
	 * getContentTyep(null) return "application/octet-stream"
	 * getContentTyep("") return "application/octet-stream"
	 * </pre>
	 * 
	 * @param ext
	 * 
	 * @return contentType
	 */
	public static String getContentTyep(String ext) {
		if (ext == null)
			return "application/octet-stream";
		String content = types.get(ext.toLowerCase().trim());
		if (content == null) {
			return "application/octet-stream";
		} else
			return content;
	}

	/**
	 * @see ContentType#getContentTyep(String)
	 * @param file
	 * @return
	 */
	public static String getContentTyep(File file) {
		String filename = file.getName();
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				filename = filename.substring(dot);
			}
		}
		return getContentTyep(filename);
	}

	public static ContentType parse(String string) {
		Matcher typeSubtype = TYPE_SUBTYPE.matcher(string);
		if (!typeSubtype.lookingAt())
			return null;
		String type = typeSubtype.group(1).toLowerCase(Locale.US);
		String subtype = typeSubtype.group(2).toLowerCase(Locale.US);

		String charset = null;
		Matcher parameter = PARAMETER.matcher(string);
		for (int s = typeSubtype.end(); s < string.length(); s = parameter
				.end()) {
			parameter.region(s, string.length());
			if (!parameter.lookingAt())
				return null;

			String name = parameter.group(1);
			if ((name != null) && (name.equalsIgnoreCase("charset"))) {
				String charsetParameter = parameter.group(2) != null ? parameter
						.group(2) : parameter.group(3);

				if ((charset != null)
						&& (!charsetParameter.equalsIgnoreCase(charset))) {
					throw new IllegalArgumentException(
							"Multiple different charsets: " + string);
				}
				charset = charsetParameter;
			}
		}
		return new ContentType(string, type, subtype, charset);
	}

	public String type() {
		return this.type;
	}

	public String subtype() {
		return this.subtype;
	}

	public String toString() {
		return this.mediaType;
	}

	public boolean equals(Object o) {
		return ((o instanceof ContentType))
				&& (((ContentType) o).mediaType.equals(this.mediaType));
	}

	public int hashCode() {
		return this.mediaType.hashCode();
	}

	public String getCharset() {
		return charset;
	}
}