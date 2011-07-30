package controllers;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import models.AbstractDomainTest;
import models.Tag;
import models.TagTest;

import org.junit.Test;

public class TagHelperFinderTest extends AbstractDomainTest {
	@Test
	public void findTagsWhenExisted() throws Exception {
		TagTest.createTag("java");
		TagTest.createTag("javascript");
		TagTest.createTag("gae");
		
		List<Tag> tags = TagHelper.getTagsFromName(Arrays.asList("java", "javascript", "gae"));
		assertThat(tags.size(), is(3));
	}
	
	@Test(expected=TagNotFoundException.class)
	public void findTagsWhenDontExisted() throws Exception {
		TagTest.createTag("java");
		TagTest.createTag("javascript");
		TagTest.createTag("gae");
		
		TagHelper.getTagsFromName(Arrays.asList("java", "자바스크립트", "gae"));
	}
}
