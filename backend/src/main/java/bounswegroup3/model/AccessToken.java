package bounswegroup3.model;

import java.security.Principal;
import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an Access Token. You never need to send this object, only receive it
 * <br>
 * <code>{ "accessToken": UUID, "userId": Integer, "creationTime": Integer, "lastAccessTime": Integer}</code>
 * 
 * Note that the dates in the object are Unix timestamps.
 */
public class AccessToken implements Principal {
    private UUID accessToken;
    private Long userId;
    private DateTime creationTime;
    private DateTime lastAccessTime;

    public AccessToken(UUID accessToken, Long userId, DateTime creationTime,
            DateTime lastAccessTime) {
        super();
        this.accessToken = accessToken;
        this.userId = userId;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
    }

    @JsonGetter("accessToken")
    public UUID getAccessToken() {
        return accessToken;
    }

    @JsonGetter("userId")
    public Long getUserId() {
        return userId;
    }

    @JsonGetter("creationTime")
    public DateTime getCreationTime() {
        return creationTime;
    }

    @JsonGetter("lastAccessTime")
    public DateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(DateTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @JsonIgnore
	@Override
	public String getName() {
		return "Access Token";
	}

}
