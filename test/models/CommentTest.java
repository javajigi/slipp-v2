package models;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import supports.ServiceType;

public class CommentTest extends AbstractDomainTest {
	@Test
	public void base() throws Exception {
		Comment comment = comment1();
		comment.insert();
		Comment newComment = new Comment(comment.getId());
		comment.get();
		assertThat(comment, is(newComment));
	}

	@Test
	public void findServiceType() throws Exception {
		comment1().insert();
		comment2().insert();
		comment3().insert();

		List<Comment> comments = Comment.findByServiceType(ServiceType.diaries, comment1().getCommentedId());
		assertThat(comments.size(), is(3));
	}

	static Comment comment1() {
		return new Comment(ServiceType.diaries, 10L, "javajigi", "javajigi@gmail.com", "parent_contents", new Date(),
				"127.0.0.1");
	}

	static Comment comment2() {
		return new Comment(2L, ServiceType.diaries, 10L, "javajigi", "javajigi@gmail.com", "child1", new Date(),
				"127.0.0.1");
	}

	static Comment comment3() {
		return new Comment(3L, ServiceType.diaries, 10L, "javajigi", "javajigi@gmail.com", "child2", new Date(),
				"127.0.0.1");
	}
}
