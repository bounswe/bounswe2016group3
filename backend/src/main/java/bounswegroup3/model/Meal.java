package bounswegroup3.model;

public class Meal {
	private Long id;
	private Long menuId;
	private String name;
	private String description;
	private String photoUrl;
	
	public Meal(){
		this.photoUrl = "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png";
	}
	
	public Meal(Long id, Long menuId, String name, String description, String photoUrl) {
		super();
		this.id = id;
		this.menuId = menuId;
		this.name = name;
		this.description = description;
		this.photoUrl = photoUrl;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
}
