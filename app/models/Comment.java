package models;

import java.util.Date;
import java.util.List;

import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import supports.ServiceType;
import supports.wiki.WikiContents;

public class Comment extends Model {
	@Id(Generator.AUTO_INCREMENT)
	private Long id;
	private ServiceType serviceType;
	private String nickname;
	private String email;
	private String contents;
	private Date createdDate;
	private String ipAddress;
	private Long commentedId;

	public Comment() {
	}

	public Comment(Long id) {
		this.id = id;
	}

	public Comment(ServiceType serviceType, Long commentedId, String nickname, String email, String contents,
			Date createdDate, String ipAddress) {
		this(null, serviceType, commentedId, nickname, email, contents, createdDate, ipAddress);
	}

	public Comment(Long id, ServiceType serviceType, Long commentedId, String nickname, String email, String contents,
			Date createdDate, String ipAddress) {
		this.id = id;
		this.serviceType = serviceType;
		this.commentedId = commentedId;
		this.nickname = nickname;
		this.email = email;
		this.contents = contents;
		this.createdDate = createdDate;
		this.ipAddress = ipAddress;
	}

	public Long getId() {
		return id;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getContents() {
		return contents;
	}

	public String getDisplayContents() {
		return WikiContents.convert(getContents());
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Long getCommentedId() {
		return commentedId;
	}
	
	public SlippUser getSlippUser() {
		return new SlippUser(getEmail(), getNickname());
	}

	static Query<Comment> all() {
		return Model.all(Comment.class);
	}

	public static List<Comment> findByServiceType(ServiceType serviceType, Long commentedId) {
		return all().filter("commentedId", commentedId).filter("serviceType", serviceType).fetch();
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", serviceType=" + serviceType + ", nickname=" + nickname + ", email=" + email
				+ ", contents=" + contents + ", createdDate=" + createdDate + ", ipAddress=" + ipAddress
				+ ", commentedId=" + commentedId + "]";
	}
}
