package tm.fantom.tmdb.repo;

import com.google.gson.annotations.SerializedName;

import tm.fantom.tmdb.api.netmodel.GuestSessionResp;

public final class AuthToken {
    @SerializedName("expires_at")
    private long expiresAt;

    @SerializedName("guest_session_id")
    private String guestSessionId;

    public long getExpiresAt() {
        return expiresAt;
    }

    public AuthToken setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public String getGuestSessionId() {
        return guestSessionId;
    }

    public AuthToken setGuestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
        return this;
    }

    public static AuthToken fromNetwork(GuestSessionResp guestSessionResp) {
        return new AuthToken()
                .setExpiresAt(guestSessionResp.getExpiresAt().getMillis())
                .setGuestSessionId(guestSessionResp.getGuestSessionId());

    }
}
