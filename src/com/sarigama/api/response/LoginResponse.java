package com.sarigama.api.response;

import com.sarigama.security.authentication.data.AuthenticationToken;

public class LoginResponse extends ResponseFormat {
   
	int loginCode ;
    String status ;
    AuthenticationToken auth = null ;

    public LoginResponse(){
        auth = new AuthenticationToken();
    }
    
    public int getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(int loginCode) {
		this.loginCode = loginCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AuthenticationToken getAuth() {
		return auth;
	}

	public void setAuth(AuthenticationToken auth) {
		this.auth = auth;
	}
}