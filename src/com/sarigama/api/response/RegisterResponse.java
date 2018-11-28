package com.sarigama.api.response;

public class RegisterResponse extends ResponseFormat{
    String Status ;
    String registerMessage ;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getRegisterMessage() {
		return registerMessage;
	}

	public void setRegisterMessage(String registerMessage) {
		this.registerMessage = registerMessage;
	}

}