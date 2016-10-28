package bounswegroup3.model;

public class FacebookUser {
	private Long id;
	private String email;
	private String name;
	private String about;
	private String picture;
	
	public FacebookUser(Long id, String email, String name, String about, String picture) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.about = about;
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}