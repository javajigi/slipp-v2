package models;

import org.junit.After;
import org.junit.Before;

import play.test.UnitTest;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class AbstractDomainTest extends UnitTest {
	static final long now = System.currentTimeMillis();
	
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setup() {
		helper.setUp();
	}
	
	@After
	public void teardown() {
		helper.tearDown();
	}
}
