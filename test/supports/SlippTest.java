package supports;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import play.Play;

public class SlippTest {
	@Test
	public void isDevMode() {
		Play.id = null;
		assertThat(Slipp.isDevMode(), is(true));
		
		Play.id = "dev";
		assertThat(Slipp.isDevMode(), is(true));
		
		Play.id = "test";
		assertThat(Slipp.isDevMode(), is(false));
	}

	@Test
	public void isTestMode() {
		Play.id = null;
		assertThat(Slipp.isTestMode(), is(false));
		
		Play.id = "dev";
		assertThat(Slipp.isTestMode(), is(false));
		
		Play.id = "test";
		assertThat(Slipp.isTestMode(), is(true));
	}
}
