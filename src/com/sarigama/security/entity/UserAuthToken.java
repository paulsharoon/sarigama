package com.sarigama.security.entity;

public class UserAuthToken {

    public static int ENABLED = 1 ;
    public static int DISABLED = 0 ;

    Long authTokenID ;
    Long userID ;
    String authToken ;
    Long expiryDate ;
    Long domainID ;
    int isLive ;
    Long createdDate ;
    Long updatedDate ;

    public Long getAuthTokenID() {
		return authTokenID;
	}
	public void setAuthTokenID(Long authTokenID) {
		this.authTokenID = authTokenID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public Long getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getDomainID() {
		return domainID;
	}
	public void setDomainID(Long domainID) {
		this.domainID = domainID;
	}
	public int getIsLive() {
		return isLive;
	}
	public void setIsLive(int isLive) {
		this.isLive = isLive;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}

}