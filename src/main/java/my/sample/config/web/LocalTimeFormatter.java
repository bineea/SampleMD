package my.sample.config.web;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class LocalTimeFormatter implements Formatter<LocalTime> {

	@Override
	public String print(LocalTime object, Locale locale) {
		return object == null ? null : object.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@Override
	public LocalTime parse(String text, Locale locale) throws ParseException {
		if(!StringUtils.hasText(text)) return null;
		return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

}
