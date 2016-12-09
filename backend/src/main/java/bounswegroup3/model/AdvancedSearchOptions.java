package bounswegroup3.model;

import java.util.List;

public class AdvancedSearchOptions {
	public String nameLike;
	public String userLike;
	public List<String> tagsList;
	public String currLocation;
	public Float maxDistance;
	
	public Long searcherId;
	public Boolean usePrefs;
	
	public AdvancedSearchOptions() {
		super();
	}

	public AdvancedSearchOptions(String nameLike, String userLike, List<String> tagsList, Float maxDistance,
			Long searcherId, Boolean usePrefs) {
		super();
		this.nameLike = nameLike;
		this.userLike = userLike;
		this.tagsList = tagsList;
		this.maxDistance = maxDistance;
		this.searcherId = searcherId;
		this.usePrefs = usePrefs;
	}

	public String getNameLike() {
		return nameLike;
	}
	
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	
	public String getUserLike() {
		return userLike;
	}
	
	public void setUserLike(String userLike) {
		this.userLike = userLike;
	}
	
	public List<String> getTagsList() {
		return tagsList;
	}
	
	public void setTagsList(List<String> tagsList) {
		this.tagsList = tagsList;
	}
	
	public Float getMaxDistance() {
		return maxDistance;
	}
	
	public void setMaxDistance(Float maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public Long getSearcherId() {
		return searcherId;
	}
	
	public void setSearcherId(Long searcherId) {
		this.searcherId = searcherId;
	}
	
	public Boolean getUsePrefs() {
		return usePrefs;
	}
	
	public void setUsePrefs(Boolean usePrefs) {
		this.usePrefs = usePrefs;
	}
}
