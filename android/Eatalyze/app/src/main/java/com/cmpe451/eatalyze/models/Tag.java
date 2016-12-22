package com.cmpe451.eatalyze.models;

/**
 * Created by ekrem on 22/11/2016.
 */

public class Tag {
    private Long relationType;
    private Long relationId;

    private String displayName;
    private String identifier;

    public Tag() {
    }

    public Tag(Long relationType, Long relationId, String displayName, String identifier) {
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
}
