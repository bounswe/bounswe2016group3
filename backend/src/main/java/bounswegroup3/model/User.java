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

public class User {
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String passwordHash;

    @NotNull
    private String passwordSalt;
    
    private String bio;

    private String fullName;
    
    private UserType userType;

	private DietType dietType;

    public User() {
        this.id = -1l;
    }

    public User(Long id, String email, String passwordHash, String passwordSalt, String fullName,
            String bio, UserType userType, DietType dietType) {
        super();
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.fullName = fullName;
        this.bio = bio;
        this.userType = userType;
        this.dietType = dietType;
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

    public boolean checkPassword(String password)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (password == null)
            return false;

        byte[] salt = Base64.decodeBase64(this.passwordSalt);

        System.out.println(this.passwordSalt);
        System.out.println(this.passwordHash);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = fac.generateSecret(spec).getEncoded();

        System.out.println(Base64.encodeBase64String(hash));

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
    public UserType getUserType() {
		return userType;
	}
    
    @JsonSetter("userType")
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@JsonGetter("dietType")
	public DietType getDietType() {
		return dietType;
	}

	@JsonSetter("dietType")
	public void setDietType(DietType dietType) {
		this.dietType = dietType;
	}
	
}