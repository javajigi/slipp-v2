package models;

import java.util.Date;
import java.util.List;

import siena.Generator;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Query;
import supports.ServiceType;
import supports.wiki.WikiContents;

public class Diary extends Model {
	@Id(Generator.AUTO_INCREMENT)
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private String contents;
	private Integer commentCount = 0;
	@NotNull
	private Date createdDate;
	
	public Diary() {
	}
	
	public Diary(Long id) {
		this.id = id;
	}

	public Diary(String title, String contents, Date createdDate) {
		this(null, title, contents, createdDate);
	}

	public Diary(Long id, String title, String contents, Date createdDate) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
	
	public String getDisplayContents() {
		return WikiContents.convert(getContents());
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void commented() {
		commentCount++;	
		update();
	}
	
	public void update(String title, String contents, Date createdDate) {
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		update();
	}
	
	public Thread diaryToThread() {
		SlippUser slippUser = new SlippUser("javajigi@gmail.com", "javajigi");
		Thread thread = new Thread(slippUser, getTitle(), getContents(), getCreatedDate());
		thread.insert();
		Tag tag = Tag.findByName("개발일지");
		thread.tag(tag);
		List<Comment> comments = Comment.findByServiceType(ServiceType.diaries, getId());
		for (Comment comment : comments) {
			thread.answer(comment.getSlippUser(), comment.getContents(), comment.getCreatedDate());
		}
		
		return thread;
	}
	
	static Query<Diary> all() {
        return Model.all(Diary.class);
    }
	
	public static List<Diary> finds(int limit) {
		return all().order("createdDate").fetch(limit);
	}
	
	public static List<Diary> findsBeforeDiary(Diary current) {
		List<Diary> diaries = all()
			.filter("createdDate <", current.createdDate)
			.order("-createdDate")
			.fetch(5);
	    return diaries;
    }

	@Override
	public String toString() {
		return "Diary [id=" + id + ", title=" + title + ", contents=" + contents + ", commentCount=" + commentCount
				+ ", createdDate=" + createdDate + "]";
	}
}
