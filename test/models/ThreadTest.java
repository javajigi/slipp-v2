package models;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import supports.DateTimeUtils;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

public class ThreadTest extends AbstractDomainTest {
	@Test
	public void createAndRetrieve() throws Exception {
		Thread thread = createThread();
		Thread persisted = Thread.findById(thread.getId());
		assertThat(thread, is(persisted));
		assertThat(persisted.getUser(), notNullValue(SlippUser.class));
	}

	@Test
	public void modifyAndRetrieve() throws Exception {
		SlippUser user = SlippUserTest.user1();
		Thread thread = createThread(user);
		thread.tag(TagTest.createTag("java"));
		thread.tag(TagTest.createTag("javascript"));
		thread.tag(TagTest.createTag("spring"));
		
		List<Tag> tags = Arrays.asList(
				TagTest.createTag("java"), TagTest.createTag("spring"), TagTest.createTag("hibernate"));
		thread.modify(user, "title2", "contents2", new Date(), tags);
		List<Tag> updatedTags = thread.getTags();
		assertThat(updatedTags.size(), is(3));
		assertThat(updatedTags.contains(TagTest.createTag("hibernate")), is(true));
		assertThat(updatedTags.contains(TagTest.createTag("javascript")), is(false));
	}
	
	@Test
	public void checkModifyRoleWhenMyThread() throws Exception {
		SlippUser user = new SlippUser("javajigi@gmail.com", "javajigi");
		Thread thread = createThread(user);
		assertThat(thread.checkModifyRole(user), is(Boolean.TRUE));
	}
	
	@Test(expected=HasNotRoleException.class)
	public void checkModifyRoleWhenLogoutUser() throws Exception {
		SlippUser user = new SlippUser("javajigi@gmail.com", "javajigi");
		Thread thread = createThread(user);
		thread.checkModifyRole(null);
	}
	
	@Test(expected=HasNotRoleException.class)
	public void checkModifyRoleWhenYourThread() throws Exception {
		SlippUser user = new SlippUser("javajigi@gmail.com", "javajigi");
		Thread thread = createThread(user);
		SlippUser loginUser = new SlippUser("javajigi@naver.com", "javajigi");
		assertThat(thread.checkModifyRole(loginUser), is(Boolean.FALSE));
	}
	
	@Test(expected=HasNotRoleException.class)
	public void modifyWhenAnotherUser() throws Exception {
		SlippUser user = SlippUserTest.user1();
		Thread thread = createThread(user);
		
		List<Tag> tags = Arrays.asList(
				TagTest.createTag("java"), TagTest.createTag("spring"), TagTest.createTag("hibernate"));
		SlippUser modifiedUser = SlippUserTest.user2();
		thread.modify(modifiedUser, "title2", "contents2", new Date(), tags);
	}
	
	@Test
	public void finds() throws Exception {
		createThread();
		List<Thread> threads = Thread.finds(0, 1);
		assertThat(threads.size(), is(1));
	}
	
	@Test
	public void answer() throws Exception {
		SlippUser answeredUser = SlippUserTest.user2();
		Thread thread = createThread();
		Answer answer1 = thread.answer(answeredUser, "contents1", new Date());
		assertThat(answer1, notNullValue());
		Answer answer2 = thread.answer(answeredUser, "contents2", new Date());
		assertThat(answer2, notNullValue());
		List<Answer> answers = thread.getAnswers();
		assertThat(answers.size(), is(2));
		assertThat(thread.getAnswerCount(), is(2));
	}
	
	@Test
	public void tag() throws Exception {
		Thread thread = createThread();
		Tag tag1 = TagTest.createTag("java");
		thread.tag(tag1);
		Tag tag2 = TagTest.createTag("javascript");
		thread.tag(tag2);
		List<Tag> tags = thread.getTags();
		assertThat(tags, is(Arrays.asList(tag1, tag2)));
	}
	
	@Test
	public void updateShowCount() throws Exception {
		Thread thread = createThread();
		Thread showThread = Thread.show(thread.getId());
		assertThat(showThread.getShowCount(), is(1));
		showThread = Thread.show(thread.getId());
		assertThat(showThread.getShowCount(), is(2));
	}
	
	static Thread createThread() {
		SlippUser user = SlippUserTest.user1();
		return createThread(user);
	}
	
	static Thread createThread(SlippUser user) {
		Thread thread = thread1(user);
		thread.insert();
		return thread;		
	}
	
	public void updateTag() throws Exception {
		List<Tag> original = Arrays.asList(new Tag("java"), new Tag("javascript"), new Tag("spring"));
		List<Tag> newTags = Arrays.asList(new Tag("java"), new Tag("struts"), new Tag("spring"));

		List<Tag> removedTags = Thread.notContains(original, newTags);
		assertThat(removedTags.size(), is(1));
		assertThat(removedTags, is(Arrays.asList(new Tag("javascript"))));
		List<Tag> addedTags = Thread.notContains(newTags, original);
		assertThat(addedTags.size(), is(1));
		assertThat(addedTags, is(Arrays.asList(new Tag("struts"))));
		
	}

	static Thread thread1(SlippUser user) {
		return new Thread(user, "title1", "contents1", new Date());
	}
}
