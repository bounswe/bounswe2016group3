package bounswegroup3.model;

import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonGetter;

public class AccessToken {
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

}
