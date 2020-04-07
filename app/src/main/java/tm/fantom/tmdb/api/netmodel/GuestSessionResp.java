/*
 * API
 * ## Welcome  This is a place to put general notes and extra information, for internal use.  To get started designing/documenting this API, select a version on the left. # Title No Description
 *
 * OpenAPI spec version: 3
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package tm.fantom.tmdb.api.netmodel;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Objects;

import kotlin.jvm.Transient;

/**
 * GuestSessionResp
 */

public class GuestSessionResp {
    @SerializedName("expires_at")
    private DateTime expiresAt;

    @SerializedName("guest_session_id")
    private String guestSessionId = null;

    @SerializedName("success")
    @Transient
    private boolean success;

    public GuestSessionResp expiresAt(DateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    /**
     * Get expiresAt
     *
     * @return expiresAt
     **/

    public DateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(DateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public GuestSessionResp guestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
        return this;
    }

    /**
     * Get guestSessionId
     *
     * @return guestSessionId
     **/

    public String getGuestSessionId() {
        return guestSessionId;
    }

    public void setGuestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
    }

    public GuestSessionResp success(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * Get success
     *
     * @return success
     **/

    public boolean success() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuestSessionResp guestSessionResp = (GuestSessionResp) o;
        return Objects.equals(this.expiresAt, guestSessionResp.expiresAt) &&
                Objects.equals(this.guestSessionId, guestSessionResp.guestSessionId) &&
                Objects.equals(this.success, guestSessionResp.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expiresAt, guestSessionId, success);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GuestSessionResp {\n");

        sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
        sb.append("    guestSessionId: ").append(toIndentedString(guestSessionId)).append("\n");
        sb.append("    success: ").append(toIndentedString(success)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

