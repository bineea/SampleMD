package my.sample.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import my.sample.common.pub.CommonAbstract;
import my.sample.common.pub.MyManagerException;
import my.sample.common.tools.HttpResponseHelper;
import my.sample.model.NoteModel;

public abstract class AbstractController extends CommonAbstract {
	
	private static final String HEADER_NOTE = "header_note";
	private static final String HEADER_ERROR = "header_error";
	private static final String HEADER_JUMP = "header_jump";

	public void addSuccess(HttpServletResponse response, String message) throws IOException {
		logger.debug("success:{}", message);
		response.setHeader(HEADER_NOTE, Base64Utils.encodeToString(message.getBytes()));
	}
	
	public void toJump(HttpServletResponse response, String uri) throws IOException {
		logger.debug("to jump:{}", uri);
		response.setHeader(HEADER_JUMP, Base64Utils.encodeToString(uri.getBytes()));
	}
	
	@ExceptionHandler(value = MyManagerException.class)
	public void handleMyManagerException(MyManagerException e, HttpServletResponse response) throws IOException {
		logger.debug("handle MyManagerException:{}", e.getMessage());
		NoteModel model = new NoteModel(false, e.getMessage());
		jsonResponse(HEADER_ERROR, model.toJson(), response);
	}
	
	private void jsonResponse(String header, String msg, HttpServletResponse response) throws IOException
	{
		response.setHeader(header, Base64Utils.encodeToString(header.getBytes()));
		HttpResponseHelper.responseJson(msg, response);
	}
	
}
