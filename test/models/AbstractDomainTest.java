package models;

import org.junit.After;
import org.junit.Before;

import play.Play;
import play.test.UnitTest;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.ApiProxy;

public abstract class AbstractDomainTest extends UnitTest {
	static final long now = System.currentTimeMillis();
	
	protected final LocalServiceTestHelper helper = createLocalServiceTestHelper();
	protected final LocalServiceTestHelper cacheHelper =
	        new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

	private LocalServiceTestHelper createLocalServiceTestHelper() {
		LocalDatastoreServiceTestConfig testConfig = new LocalDatastoreServiceTestConfig();
		testConfig.setBackingStoreLocation("tmp/unittest-datastore");
		LocalServiceTestHelper testHelper = new LocalServiceTestHelper(testConfig);
		return testHelper;
	}
	
	@Before
	public void setup() {
		helper.setUp();
		cacheHelper.setUp();
	}
	
	@After
	public void teardown() {
		helper.tearDown();
	}
}
