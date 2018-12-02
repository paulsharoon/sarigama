
package com.sarigama.security.entity;

public class UserProfileEntity {

	@Override
	public String toString() {
		return "UserProfileEntity [userId=" + userId + ", userName=" + userName + ", email=" + email + ", ePassword="
				+ ePassword + ", password=" + password + ", salt=" + salt + ", passwordExpiry=" + passwordExpiry
				+ ", emailUiqueToken=" + emailUiqueToken + ", isLive=" + isLive + "]";
	}

	private Long userId;
	private String userName;
    private String email;
    private String ePassword ;
	private String password ;
    private String salt ;
    private Long passwordExpiry ;
    private String userSignature ;
    private String emailUiqueToken ;
    private int isLive ; 

    /**
     * @return Long return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return String return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the ePassword
     */
    public String getEPassword() {
        return ePassword;
    }

    /**
     * @param ePassword the ePassword to set
     */
    public void setEPassword(String ePassword) {
        this.ePassword = ePassword;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return Long return the passwordExpiry
     */
    public Long getPasswordExpiry() {
        return passwordExpiry;
    }

    /**
     * @param passwordExpiry the passwordExpiry to set
     */
    public void setPasswordExpiry(Long passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

    /**
     * @return String return the emailUiqueToken
     */
    public String getEmailUiqueToken() {
        return emailUiqueToken;
    }

    /**
     * @param emailUiqueToken the emailUiqueToken to set
     */
    public void setEmailUiqueToken(String emailUiqueToken) {
        this.emailUiqueToken = emailUiqueToken;
    }

    /**
     * @return int return the isLive
     */
    public int getIsLive() {
        return isLive;
    }

    /**
     * @param isLive the isLive to set
     */
    public void setIsLive(int isLive) {
        this.isLive = isLive;
    }


    /**
     * @return String return the userSignature
     */
    public String getUserSignature() {
        return userSignature;
    }

    /**
     * @param userSignature the userSignature to set
     */
    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

}