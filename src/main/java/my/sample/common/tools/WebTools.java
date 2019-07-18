package my.sample.common.tools;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

public class WebTools extends WebUtils {

	private static String contextPath;

	// 请求URI = context path + servlet path + path info
	public static String getUri(HttpServletRequest request, boolean withParams) {

		if (!StringUtils.hasText(contextPath))
			contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		if (StringUtils.hasText(contextPath) && servletPath.startsWith(contextPath))
			servletPath.replaceFirst(contextPath, "");
		String uri = servletPath + request.getPathInfo();
		if (!withParams)
			return uri;
		StringBuilder builder = new StringBuilder(uri).append("?");
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			if (ObjectUtils.isEmpty(values))
				continue;
			for (String value : values) {
				builder.append(key).append("=").append(value).append("&");
			}
		}
		return builder.substring(0, builder.length() - 1).toString();
	}

	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getAgentInfo(HttpServletRequest request) {
		//TODO 获取浏览器信息，系统信息；依赖UserAgentUtils-1.2.4.jar
		return null;
	}
}
