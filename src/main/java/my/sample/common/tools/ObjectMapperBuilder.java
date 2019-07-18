package my.sample.common.tools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import my.sample.common.pub.CommonAbstract;

public class ObjectMapperBuilder  extends CommonAbstract {
	
	private static final String DEFAULT_FILTER_NAME = JsonTools.FILTER_NAME;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private String filterName;
	private SimpleDateFormat dateFormat;
	private LocalDateTimeDeserializer localDateTimeDeserializer;
	private LocalDateDeserializer localDateDeserializer;
	private LocalTimeDeserializer localTimeDeserializer;
	
	public static ObjectMapperBuilder create() {
		
		return new ObjectMapperBuilder();
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setLocalDateTimeDeserializer(LocalDateTimeDeserializer localDateTimeDeserializer) {
		this.localDateTimeDeserializer = localDateTimeDeserializer;
	}

	public void setLocalDateDeserializer(LocalDateDeserializer localDateDeserializer) {
		this.localDateDeserializer = localDateDeserializer;
	}

	public void setLocalTimeDeserializer(LocalTimeDeserializer localTimeDeserializer) {
		this.localTimeDeserializer = localTimeDeserializer;
	}
	
	public ObjectMapper build() {
		//设置日期解析格式
		objectMapper.setDateFormat( dateFormat == null ? new SimpleDateFormat():dateFormat);
		objectMapper.setTimeZone(TimeZone.getDefault());
		//设置Java8 日期解析格式
		JavaTimeModule javaTimeModule=new JavaTimeModule();
        // Hack time module to allow 'Z' at the end of string (i.e. javascript json's) 
        javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer == null ? new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME) : localDateTimeDeserializer);
        javaTimeModule.addDeserializer(LocalDate.class, localDateDeserializer == null ? new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE) : localDateDeserializer);
        javaTimeModule.addDeserializer(LocalTime.class, localTimeDeserializer == null ? new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME) : localTimeDeserializer);
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置枚举格式解析
        //该特性决定对Enum 枚举值使用标准的序列化机制。如果true，则返回Enum.toString()值，否则为Enum.name()
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, false);
		//该特性决定了使用枚举值的标准序列化机制：如果允许，则枚举假定使用Enum.toString()返回的值作为序列化结构；如果禁止, 则返回Enum.name()的值。
        //注意：默认使用的时Enum.name()的值作为枚举序列化结果。这个的设置和WRITE_ENUMS_USING_TO_STRING需要一致。
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, false);
        //该特性决定了当遇到未知属性（没有映射到属性，没有任何setter或者任何可以处理它的handler），是否应该抛出一个
        //JsonMappingException异常。这个特性一般式所有其他处理方法对未知属性处理都无效后才被尝试，属性保留未处理状态。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //是否允许一个类型没有注解表明打算被序列化。默认true，抛出一个异常；否则序列化一个空对象，比如没有任何属性。
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 默认filter
 		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
 				StringUtils.hasText(filterName) ? filterName : DEFAULT_FILTER_NAME,
 				SimpleBeanPropertyFilter.serializeAllExcept(""));
 		objectMapper.setFilterProvider(filterProvider);
		return objectMapper;
	}
	
}
