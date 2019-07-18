package my.sample.config.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class TrimStringFormatter implements Formatter<String> {

	@Override
	public String print(String object, Locale locale) {
		return object == null ? null : object.trim();
	}

	@Override
	public String parse(String text, Locale locale) throws ParseException {
		return text == null ? null : text.trim();
	}

}
