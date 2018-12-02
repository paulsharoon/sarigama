package com.sarigama.security.authentication.data;

/**
 * AuthenticationToken
 */
public class AuthenticationToken {

	String authToken  ;
	String userSignature  ;
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	
	
}