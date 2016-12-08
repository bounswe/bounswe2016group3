package bounswegroup3.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import bounswegroup3.constant.DietType;
import bounswegroup3.constant.UserType;

/**
 * Represents a user object. Each other piece of data
 * in the system ultimately belongs to a user. You need to create one and login
 * before being able to do anything else on the system. The password and secretAnswer
 * fields are updated via sending some plain text, but before being stored
 * in the database, they are hashed. Also, the avatar url field
 * cannot be modified directly, there is a separate API call for uploading a file 
 * as your avatar image. Location column is a latitude-longitude pair
 * (preferrably supplied via a map input)
 * <br>
 * <code>{"id": Integer, "email": String, "password": String, "bio": String,
 *  "fullName": String, "userType": Integer, "dietType": Integer, 
 *  "secretQuestion": String, "secretAnswer": String,
 *  "avatarUrl": String, "location":String}</code>
 */
public class User {
    private Long id;

    @NotNull
    private String email;

    private String passwordHash;

    private String passwordSalt;
    
    private String bio;

    private String fullName;
    
    private UserType userType;

	private DietType dietType;

	@NotNull
	private String secretQuestion;
	
	private String secretAnswerHash;
	
	private String secretAnswerSalt;
	
	private String avatarUrl;
	
	private String location;
	
	private Boolean isBanned;

	public User() {
        this.id = -1l;
        
        this.bio = "";
        this.fullName = "";
        this.userType = UserType.REGULAR;
        this.dietType = DietType.OMNIVORE;
        this.avatarUrl = "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png";
        this.isBanned = false;
    }

    public User(Long id, String email, String passwordHash, String passwordSalt, String fullName,
            String bio, UserType userType, DietType dietType, String secretQuestion, String secretAnswerHash, 
            String secretAnswerSalt, String avatarUrl, String location, Boolean isBanned) {
        super();
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.fullName = fullName;
        this.bio = bio;
        this.userType = userType;
        this.dietType = dietType;
        this.secretQuestion = secretQuestion;
        this.secretAnswerHash = secretAnswerHash;
        this.secretAnswerSalt = secretAnswerSalt;
        this.avatarUrl = avatarUrl;
        this.location = location;
        this.isBanned = isBanned;
    }

    @JsonSetter("password")
    public void setPassword(String password)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = fac.generateSecret(spec).getEncoded();

        this.passwordSalt = Base64.encodeBase64String(salt);
        this.passwordHash = Base64.encodeBase64String(hash);
    }
    
    @JsonSetter("secretAnswer")
    public void setSecretAnswer(String answer)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(answer.toCharArray(), salt, 65536, 128);

        SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = fac.generateSecret(spec).getEncoded();

        this.secretAnswerSalt = Base64.encodeBase64String(salt);
        this.secretAnswerHash = Base64.encodeBase64String(hash);
    }
    
    public boolean checkSecretAnswer(String answer)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (answer == null)
            return false;

        byte[] salt = Base64.decodeBase64(this.secretAnswerSalt);

        KeySpec spec = new PBEKeySpec(answer.toCharArray(), salt, 65536, 128);

        SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = fac.generateSecret(spec).getEncoded();
        
        return Base64.encodeBase64String(hash).equals(this.secretAnswerHash);
    }
    
    public boolean checkPassword(String password)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (password == null)
            return false;

        byte[] salt = Base64.decodeBase64(this.passwordSalt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = fac.generateSecret(spec).getEncoded();

        
        return Base64.encodeBase64String(hash).equals(this.passwordHash);
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonGetter("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonSetter("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    @JsonIgnore
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @JsonGetter("id")
    public Long getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }
    
    @JsonGetter("bio")
    public String getBio(){
    	return bio;
    }

	@JsonSetter("bio")
	public void setBio(String bio) {
		this.bio = bio;
	}
	
    @JsonGetter("userType")
    public int getUserType() {
		return userType.ordinal();
	}
    
    @JsonSetter("userType")
	public void setUserType(int userType) {
		this.userType = UserType.values()[userType];
	}

	@JsonGetter("dietType")
	public int getDietType() {
		return dietType.ordinal();
	}

	@JsonSetter("dietType")
	public void setDietType(int dietType) {
		this.dietType = DietType.values()[dietType];
	}

	@JsonGetter("secretQuestion")
	public String getSecretQuestion() {
		return secretQuestion;
	}

	@JsonSetter("secretQuestion")
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	@JsonIgnore
	public String getSecretAnswerHash() {
		return secretAnswerHash;
	}

	@JsonIgnore
	public String getSecretAnswerSalt() {
		return secretAnswerSalt;
	}

	@JsonGetter("avatarUrl")
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	@JsonSetter("avatarUrl")
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	@JsonGetter("location")
	public String getLocation() {
		return location;
	}

	@JsonSetter("location")
	public void setLocation(String location) {
		this.location = location;
	}

	@JsonGetter("isBanned")
    public Boolean getIsBanned() {
		return isBanned;
	}

	@JsonSetter("isBanned")
	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}
	
	public static String generatePassword(){
		final Random r = new SecureRandom();
        byte[] res = new byte[16];
        r.nextBytes(res);
        return new Base64().encodeAsString(res);
	}
	
}