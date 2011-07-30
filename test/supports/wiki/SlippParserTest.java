package supports.wiki;

import java.io.StringWriter;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.junit.Test;

import supports.ServiceType;

public class SlippParserTest {
	@Test
	public void parse() throws Exception {
		String source = "!1234!";
		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		builder.setEmitAsDocument(false);
		MarkupParser parser = new MarkupParser(new SlippLanguage());
		parser.setBuilder(builder);
		parser.parse(source);
		
		System.out.println(writer.toString());
	}
}
