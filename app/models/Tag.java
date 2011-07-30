package models;

import java.util.ArrayList;
import java.util.List;

import play.Logger;

import siena.Column;
import siena.Generator;
import siena.Id;
import siena.Index;
import siena.Model;
import siena.NotNull;
import siena.Query;
import supports.DateTimeUtils;
import supports.Pager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;

public class Tag extends Model {
	@Id(Generator.AUTO_INCREMENT)
	public Long id;
	
	@NotNull
	@Index("tag_name")
	public String name;

	@Column("parent_id")
	@Index("parent_tag_index")
	public Tag parent;

	public Integer taggedCount = new Integer(0);
	
	public Tag() {}
	
	public Tag(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Tag(String name) {
		this.name = name;
	}
	
	public Tag(String name, Tag parent) {
		this.name = name;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Tag getParent() {
		return parent;
	}
	
	void updateTaggedCount() {
		taggedCount = ThreadTag.countByTag(this);
		update();
	}

	void updateTaggedCount(int taggedCount) {
		Logger.debug("tagged count : " + taggedCount);
		this.taggedCount = taggedCount;
		update();
	}
	
	public Integer getTaggedCount() {
		return taggedCount;
	}
	
	Tag getParentTag() {
		if (parent == null) {
			return this;
		}
		parent.get();
		return parent;
	}

	static Query<Tag> all() {
		return Model.all(Tag.class);
	}
	
	public static Tag findByName(String name) {
		return all().filter("name", name).get();
	}
	
	public static List<Tag> findByTaggedCount() {
		List<Tag> tags = all().filter("parent", null).order("-taggedCount").fetch();
		List<Tag> newTags = new ArrayList<Tag>();
		for (Tag tag : tags) {
			if (tag.getTaggedCount() > 0) {
				newTags.add(tag);
			}
		}
		return newTags;
	}
	
	public static List<Tag> findsByName(String name) {
		return all().filter("name>=", name).filter("name<", name+"\uFFFD").fetch();
//		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
//		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Tag");
//		q.addFilter("name", com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL, name);
//		q.addFilter("name", com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN, name + "\uFFFD");
//		
//		PreparedQuery pq = dataStore.prepare(q);
//		List<Tag> tags = new ArrayList<Tag>();
//		for(Entity result : pq.asIterable()){
//			Tag tag = new Tag((Long)result.getProperty("id"), (String)result.getProperty("name"));
//			tags.add(tag);
//		}
//		
//		return tags;
	}
	
	public static List<Tag> findParents() {
		return all().filter("parent", null).fetch();
	}
	
	public List<Thread> getThreads(Pager pager) {
		return ThreadTag.findThreadsByTag(this, pager.getOffset(), pager.getCountPerPage()); 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", parent=" + parent + ", taggedCount=" + taggedCount + "]";
	}
}
