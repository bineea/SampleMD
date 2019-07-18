package my.sample.config.web;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class LocalDateFormatter implements Formatter<LocalDate> {

	@Override
	public String print(LocalDate object, Locale locale) {
		return object == null ? null : object.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		if(!StringUtils.hasText(text)) return null;
		return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
	}

}
