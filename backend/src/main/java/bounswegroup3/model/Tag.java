package bounswegroup3.model;

import bounswegroup3.constant.TagType;

/**
 * Represents a semantic tag on a resource. 
 * The resource can be a meal, a menu, a comment or a user.
 * <ul>
 * <li>relationType is defined in the TagType enum.</li>
 * <li>displayName represents the name of the tag to be displayed.</li>
 * <li>identifier is its identifier as used by the wikidata.org</li>
 * </ul>
 * <br>
 * <code>{"relationType":Integer, "relationId":Integer, "displayName":String, "identifier":String}</code>
 */
public class Tag {
	private Long relationType;
	private Long relationId;
	
	private String displayName;
	private String identifier;
	
	public Tag() {
		
	}

	public Tag(Long relationType, Long relationId, String displayName, String identifier) {
		super();
		this.relationType = relationType;
		this.relationId = relationId;
		this.displayName = displayName;
		this.identifier = identifier;
	}

	public Long getRelationType() {
		return relationType;
	}

	public void setRelationType(Long relationType) {
		this.relationType = relationType;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public boolean isOfType(TagType type) {
		return this.getRelationType().equals(new Long(type.ordinal()));
	}
}
