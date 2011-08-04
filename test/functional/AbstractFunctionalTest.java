package functional;

import org.junit.After;
import org.junit.Before;

import play.test.FunctionalTest;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class AbstractFunctionalTest extends FunctionalTest {
	protected final LocalServiceTestHelper helper = createLocalServiceTestHelper();

	private LocalServiceTestHelper createLocalServiceTestHelper() {
		LocalDatastoreServiceTestConfig testConfig = new LocalDatastoreServiceTestConfig();
		testConfig.setBackingStoreLocation("tmp/functionaltest-datastore");
		LocalServiceTestHelper testHelper = new LocalServiceTestHelper(testConfig);
		return testHelper;
	}
	
	@Before
	public void setup() {
		helper.setUp();
	}
}
