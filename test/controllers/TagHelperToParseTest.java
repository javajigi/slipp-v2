package controllers;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import java.util.Arrays;
import java.util.List;

import models.TagTest;

import org.junit.Test;

public class TagHelperToParseTest{
	@Test
	public void parseTagNamesWhenisEmpty() throws Exception {
		List<String> tagNames = TagHelper.parse(null);
		assertThat(tagNames.size(), is(0));
		tagNames = TagHelper.parse("");
		assertThat(tagNames.size(), is(0));
		tagNames = TagHelper.parse(" ");
		assertThat(tagNames.size(), is(0));
	}
	
	@Test
	public void parseTagNamesWhenhasOneTag() throws Exception {
		List<String> tagNames = TagHelper.parse("java");
		assertThat(tagNames.size(), is(1));
		
		tagNames = TagHelper.parse("java ");
		assertThat(tagNames.size(), is(1));
	}

	@Test
	public void parseTagNamesWhenhasManyTag() throws Exception {
		List<String> tagNames = TagHelper.parse("java javascript");
		assertThat(tagNames.size(), is(2));
		
		tagNames = TagHelper.parse("java javascript ");
		assertThat(tagNames.size(), is(2));
		
		tagNames = TagHelper.parse("java javascript gae ");
		assertThat(tagNames.size(), is(3));
	}
}
