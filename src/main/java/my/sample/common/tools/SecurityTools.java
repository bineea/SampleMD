package my.sample.common.tools;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

public class SecurityTools {

	private static final char[] defaultDigit = { 'L', 'I', 'N', 'X', 'I', 'N', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	private static final char[] commonDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	public static String encryStr(String str, DigestType type) {
		
		return encryStr(str, type, "utf-8", false);
	}
	
	public static String encryStr(String str, DigestType type, String encoding, boolean useCommonDigit) {
		
		try {
			if(!StringUtils.hasText(encoding)) encoding = "utf-8";
			byte[] data = getHashValue(str.getBytes(encoding), type);
			return toHexString(data, useCommonDigit);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private static byte[] getHashValue(byte[] data, DigestType type) {
		
		MessageDigest digest = type.getInstance();
		return digest.digest(data);
	}
	
	private static String toHexString(byte[] data, boolean useCommonDigit) {
		
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (int i = 0; i < data.length; i++)
		{
			if (useCommonDigit)
			{
				sb.append(commonDigit[(data[i] & 0xf0) >>> 4]);
				sb.append(commonDigit[data[i] & 0x0f]);
			}
			else
			{
				sb.append(defaultDigit[(data[i] & 0xf0) >>> 4]);
				sb.append(defaultDigit[data[i] & 0x0f]);
			}
		}
		return sb.toString();
	}
	
	public enum DigestType {
		MD5 ("MD5"){},
		SHA_1 ("SHA-1"){},
		SHA_256 ("SHA-256"){},
		SHA_512 ("SHA-512"){};
		
		private String value;
		private DigestType(String value) {
			
			this.value = value;
		}
		
		public MessageDigest getInstance() {
			
			try {
				return MessageDigest.getInstance(getValue());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new RuntimeException("暂不支持该加密方式");
			}
		}

		public String getValue() {
			return value;
		}
		
	}
}
