package my.sample.common.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class Html2TextTools {
	
	private static final Html2TextTools Html2TextTools = new Html2TextTools();

	public static String parse(String str, int length) throws IOException {
		Html2TextTools.InnerParserCallBack inner = Html2TextTools.new InnerParserCallBack(length);
		InputStream in = new ByteArrayInputStream(str.getBytes());
		Reader reader = new InputStreamReader(in);
		ParserDelegator delegator = new ParserDelegator();
		delegator.parse(reader, inner, Boolean.TRUE);
		in.close();
		reader.close();
		return inner.getText();
	}

	private class InnerParserCallBack extends HTMLEditorKit.ParserCallback {
		
		private StringBuilder builder;
		private int length;
		
		public InnerParserCallBack(int length) {
			this.builder = new StringBuilder();
			this.length = length;
		}
		
		@Override
		public void handleText(char[] text, int pos) {
			builder.append(text);
		}

		public String getText() {
			builder.setLength(length);
			return builder.append("......").toString();
		}
		
	}
	
}
