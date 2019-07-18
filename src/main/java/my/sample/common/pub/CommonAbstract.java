package my.sample.common.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class CommonAbstract {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String like(String str) {
		
		return StringUtils.hasText(str) ? "%" + str + "%" : null;
	}
	
	protected boolean hasText(String str) {
		
		return StringUtils.hasText(str);
	}
	
}
