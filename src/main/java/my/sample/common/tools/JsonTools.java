package my.sample.common.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.MapType;

import my.sample.common.entity.BaseEntity;

public class JsonTools {

	public static final String FILTER_NAME = "myFilter";
	protected static Logger logger = LoggerFactory.getLogger(JsonTools.class);
	protected static ObjectMapper mapper;

	static {
		mapper = ObjectMapperBuilder.create().build();
	}

	/**
	 * 将实体转为json字符串
	 * 
	 * @param entity
	 * @param ingoreProperties
	 * @return
	 * @throws IOException
	 */
	public final static String entityToJson(BaseEntity entity, String... ingoreProperties) throws IOException {
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(FILTER_NAME,
				SimpleBeanPropertyFilter.serializeAllExcept(ingoreProperties));
		return mapper.writer(filterProvider).writeValueAsString(entity);
	}

	public final static String writeValueAsString(Object value) throws IOException {
		return mapper.writeValueAsString(value);
	}

	/**
	 * 将json字符串转换成entity对象
	 * 
	 * @param <T>
	 *            对象类型
	 * @param jsonStr
	 *            json字符串
	 * @param clazz
	 *            转换后的对象类型，必须是继承BaseEntity的对象
	 * @return 转换后的对象
	 * @throws IOException
	 */
	public final static <T extends BaseEntity> T json2Object(String jsonStr, Class<T> clazz) throws IOException {
		if (!StringUtils.hasText(jsonStr))
			return null;
		return mapper.readValue(jsonStr, clazz);
	}

//	public final static <E extends BaseEntity> Pagination json2Pagn(String jsonStr, Class<E> resultClazz)
//			throws IOException {
//		if (!StringUtils.hasText(jsonStr) || resultClazz == null)
//			return Pagination.emptyInstance();
//		int reFirst = jsonStr.indexOf("[");
//		int reLast = jsonStr.lastIndexOf("]");
//		if (reLast <= reFirst)
//			throw new IllegalArgumentException("不是合法的Pagination Json 字符串.");
//		String reStr = jsonStr.substring(reFirst, reLast + 1);
//		Pagination pg = mapper.readValue(jsonStr, Pagination.class);
//		pg.setResult(json2List(reStr, resultClazz));
//		return pg;
//	}

	/**
	 * 将json字符串转换成集合对象(尽量使用继承BaseEntity的对象)
	 * 
	 * @param <E>
	 *            对象类型
	 * @param jsonStr
	 *            json字符串
	 * @param eleClazz
	 *            转换后的对象类型，必须是继承BaseEntity的对象
	 * @return 转换后的集合
	 * @throws IOException
	 */
	public final static <E> List<E> json2List(String jsonStr, Class<E> eleClazz) throws IOException {
		if (!StringUtils.hasText(jsonStr) || eleClazz == null)
			return new ArrayList<E>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, eleClazz);
		return mapper.readValue(jsonStr, type);
	}

	/**
	 * json字符串转换成Map对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param keyClass
	 *            key的对象类型
	 * @param valueClass
	 *            value的对象类型
	 * @return 转换后的Map
	 * @throws IOException
	 */
	public final static <K, V> Map<K, V> json2Map(String jsonStr, Class<K> keyClass, Class<V> valueClass)
			throws IOException {
		if (!StringUtils.hasText(jsonStr) || keyClass == null || valueClass == null)
			return new HashMap<K, V>();
		MapType type = mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
		return mapper.readValue(jsonStr, type);
	}

	/**
	 * 格式化json-- from 杨境的JsonFormatTools
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static String formatJson(String jsonStr) {
		if (jsonStr == null || "".equals(jsonStr))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				// 如果是{ [ 下一行需要缩进
				indent++;
				addIndentBlank(sb, indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent--;
				addIndentBlank(sb, indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\') {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}
		return sb.toString();
	}

	/**
	 * 添加空格
	 * 
	 * @param sb
	 * @param indent
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		// “\t”为“转义字符”,代表的是一个tab，也就是8个空格。
		for (int i = 0; i < indent; i++) {
			// sb.append('\t');
			// 八个空格太多了，改为4个空格
			sb.append("    ");
		}
	}
}
