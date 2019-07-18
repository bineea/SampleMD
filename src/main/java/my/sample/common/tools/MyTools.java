package my.sample.common.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MyTools {
	
	private static final String ChineseRegex = "[\\u4E00-\\u9FA5]+";

	/**
	 * 将汉字转为拼音
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String handleStr2Spell(String str) {
		try {
			StringBuffer buffer = new StringBuffer();
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			// UPPERCASE：大写  (ZHONG)
			// LOWERCASE：小写  (zhong)
		    format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		    // WITHOUT_TONE：无音标  (zhong)
		    // WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)
		    // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
		    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		    // WITH_V：用v表示ü  (nv)
		    // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
		    // WITH_U_UNICODE：直接用ü (nü)
		    format.setVCharType(HanyuPinyinVCharType.WITH_V);
			char[] characters = str.toCharArray();
			for(int i=0; i<characters.length; i++) {
				if(String.valueOf(characters[i]).matches(ChineseRegex)) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(characters[i], format);
					for(int t=0; t<temp.length; t++) {
						buffer.append(String.valueOf(temp[t]));
					}
				} else {
					buffer.append(String.valueOf(characters[i]).toUpperCase());
				}
			}
			return buffer.toString();
		}catch(BadHanyuPinyinOutputFormatCombination e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * byte数组转换成16进制字符串
	 * @param b 待转换byte数组
	 * @param length 共转换byte数组长度 
	 * @return
	 */
	public static String bytesToHexString(byte[] b, int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i<b.length&&i<length; i++)
		{
			int v = b[i] & 0xFF;
		    String hv = Integer.toHexString(v);  
		    if (hv.length() < 2)
		    {
		        stringBuilder.append(0);  
		    }  
		    stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
