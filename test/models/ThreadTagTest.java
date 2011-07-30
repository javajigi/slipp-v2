package models;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ThreadTagTest extends AbstractDomainTest {
	@Test
	public void findThreadTagWhenExist() throws Exception {
		Thread thread = ThreadTest.createThread();
		Tag tag = TagTest.createTag("java");
		ThreadTag threadTag = ThreadTag.tagged(thread, tag, new Date());
		ThreadTag actual = ThreadTag.find(thread, tag);
		assertThat(actual, is(threadTag));
	}
	
	@Test
	public void findThreadTagWhenNonExist() throws Exception {
		Thread thread = ThreadTest.createThread();
		Tag tag = TagTest.createTag("java");
		ThreadTag.tagged(thread, tag, new Date());
		ThreadTag actual = ThreadTag.find(thread, TagTest.createTag("javascript"));
		assertThat(actual, nullValue());
	}
	
	@Test
	public void findsByTag() throws Exception {
		Thread thread = ThreadTest.createThread();
		Tag tag = TagTest.createTag("java");
		ThreadTag.tagged(thread, tag, new Date());
		List<Thread> threads = ThreadTag.findThreadsByTag(tag, 0, 10);
		assertThat(threads.size(), is(1));
	}
	
	@Test
	public void tagged() throws Exception {
		Thread thread = ThreadTest.createThread();
		Tag tag = TagTest.createTag("java");
		ThreadTag threadTag = ThreadTag.tagged(thread, tag, new Date());
		assertThat(threadTag.getThread(), is(thread));
		assertThat(threadTag.getTag(), is(tag));
		assertThat(tag.getTaggedCount(), is(1));
	}	
	
	@Test
	public void deTagged() throws Exception {
		Thread thread = ThreadTest.createThread();
		Tag tag = TagTest.createTag("java");
		ThreadTag.tagged(thread, tag, new Date());
		ThreadTag.deTagged(thread, tag);
		assertThat(ThreadTag.find(thread, tag.getParentTag()), nullValue());
		assertThat(tag.getTaggedCount(), is(0));
	}
}
