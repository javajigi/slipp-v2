package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class SlippUserTest {
	@Test
	public void empty() throws Exception {
		
	}
	
	static SlippUser user1() {
		return new SlippUser("javajigi@gmail.com", "자바지기");
	}
	
	static SlippUser user2() {
		return new SlippUser("sanjigi@gmail.com", "자바지기");
	}
}
