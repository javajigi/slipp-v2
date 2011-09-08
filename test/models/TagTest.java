package models;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import play.Logger;

public class TagTest extends AbstractDomainTest {
	@Test
	public void createRetrieveWhenParent() throws Exception {
		Tag parent = createTag("java");
		Tag actual = Tag.findByName(parent.getName());
		assertThat(actual, is(parent));
	}
	
	@Test
	public void findParents() throws Exception {
		Tag parent1 = createTag("java");
		createTag("자바", parent1);
		Tag parent2 = createTag("javascript");
		createTag("자바스크립트", parent2);
		
		List<Tag> parents = Tag.findParents();
		assertThat(parents.size(), is(2));
		assertThat(parents, is(Arrays.asList(parent1, parent2)));
	}

	@Test
	public void findsByName() throws Exception {
		Tag parent1 = createTag("java");
		createTag("자바", parent1);
		Tag parent2 = createTag("javascript");
		createTag("자바스크립트", parent2);
		List<Tag> tags = Tag.findsByName("자바");
		assertThat(tags.size(), is(2));
	}
	
	@Test
	public void createTagWhenChild() throws Exception {
		Tag parent = createTag("java");
		Tag child = new Tag("자바", parent);
		child.insert();
	}
	
	@Test
	public void findByTaggedCount() {
		Tag java = createTag("java");
		java.taggedCount = 3;
		java.update();
		Tag javascript = createTag("javascript");
		javascript.taggedCount = 2;
		javascript.update();
		createTag("spring");
		List<Tag> tags = Tag.findByTaggedCount();
		assertThat(tags.size(), is(2));
		tags = Tag.findByTaggedCount();
		Logger.debug("SortedTags : " + tags);
		assertThat(tags.size(), is(2));
	}
	
	public static Tag createTag(String name) {
		return createTag(name, null);
	}
	
	public static Tag createTag(String name, Tag parent) {
		if (Tag.findByName(name) == null ) {
			Tag tag = new Tag(name, parent);
			tag.insert();
			return tag;
		} else {
			return Tag.findByName(name);
		}
	}
}
