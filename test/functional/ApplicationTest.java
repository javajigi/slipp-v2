package functional;
import org.junit.Test;

import play.mvc.Http.Response;

public class ApplicationTest extends AbstractFunctionalTest {
    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }
}