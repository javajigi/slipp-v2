package supports;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class CacheTest {
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());
	
	@Before
	public void setup() {
		helper.setUp();
	}
	
	@Test
	public void doTest() throws Exception {
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
        assertFalse(ms.contains("yar"));
        ms.put("yar", "foo");
        assertTrue(ms.contains("yar"));
        assertThat(ms.get("yar").toString(), is("foo"));
	}
	
	@After
	public void teardown() {
		helper.tearDown();
	}
}
