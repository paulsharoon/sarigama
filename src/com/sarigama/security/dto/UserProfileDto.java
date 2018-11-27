package com.sarigama.security.dto;

import com.sarigama.db.exception.DBException;
import com.sarigama.security.authentication.AuthenticationService;
import com.sarigama.security.authentication.exception.UserServiceException;
import com.sarigama.security.db.UserProfileDB;
import com.sarigama.security.dto.exception.UserAlreadyExcistException;
import com.sarigama.security.entity.UserProfileEntity;
import com.sarigama.utils.DateUtil;

public class UserProfileDto {

    UserProfileEntity userProfileEntity  ;
    UserProfileDB userProfileDB ;
    AuthenticationService authenticationService ;

    public UserProfileDto(){
        this.userProfileEntity = new UserProfileEntity();
        this.userProfileDB = new UserProfileDB() ;
        this.authenticationService = new AuthenticationService();
    }

    public UserProfileDto(String userName ) throws Exception{
        this.getUserProfile(userName);
        this.userProfileDB = new UserProfileDB() ;
        this.authenticationService = new AuthenticationService();
    }

    public UserProfileEntity getUserProfile(String userName) throws Exception{
        this.userProfileEntity = this.userProfileDB.getUserProfile(userName);
        return this.userProfileEntity ;
    }

    public UserProfileEntity addUserProfile() throws UserAlreadyExcistException , UserServiceException , DBException
    {
        UserProfileEntity existUserProfile =  this.userProfileDB.getUserProfile(this.getEmail());
       // System.out.println( existUserProfile.toString());
        if( existUserProfile != null && existUserProfile.getUserId() > 0 ){
           throw  new UserAlreadyExcistException("User Already Excist");
        }
        this.generateUserProfile() ;
        this.setUserName(this.getEmail());
        this.setPasswordExpiry( new Long(0) );
        this.setEmailUiqueToken( "" );
        this.setIsLive(1);
        this.userProfileDB.saveUserProfile(this.userProfileEntity);
        return this.userProfileEntity ;
    }

    public UserProfileEntity updateUserProfile(){
        this.userProfileDB.updateUserProfile(this.userProfileEntity);
        return this.userProfileEntity ;
    }

    public void generateUserProfile() throws UserServiceException{
        this.authenticationService.resetSecurityCridentials(this);
    } 

    /**
     * @return Long return the userId
     */
    public Long getUserId() {
        return userProfileEntity.getUserId();
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userProfileEntity.setUserId(userId);;
    }

    /**
     * @return String return the userName
     */
    public String getUserName() {
        return userProfileEntity.getUserName();
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userProfileEntity.setUserName(userName);;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return this.userProfileEntity.getEmail();
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.userProfileEntity.setEmail(email);
    }

    /**
     * @return String return the ePassword
     */
    public String getEPassword() {
        return this.getEPassword();
    }

    /**
     * @param ePassword the ePassword to set
     */
    public void setEPassword(String ePassword) {
        this.userProfileEntity.setEPassword(ePassword);;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return this.userProfileEntity.getPassword();
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.userProfileEntity.setPassword(password);;
    }

    /**
     * @return String return the salt
     */
    public String getSalt() {
        return this.userProfileEntity.getSalt();
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.userProfileEntity.setSalt(salt);;
    }

    /**
     * @return Long return the passwordExpiry
     */
    public Long getPasswordExpiry() {
        return this.userProfileEntity.getPasswordExpiry();
    }

    /**
     * @param passwordExpiry the passwordExpiry to set
     */
    public void setPasswordExpiry(Long passwordExpiry) {
        this.userProfileEntity.setPasswordExpiry(passwordExpiry);;
    }

    /**
     * @return String return the emailUiqueToken
     */
    public String getEmailUiqueToken() {
        return this.userProfileEntity.getEmailUiqueToken();
    }

    /**
     * @param emailUiqueToken the emailUiqueToken to set
     */
    public void setEmailUiqueToken(String emailUiqueToken) {
        this.userProfileEntity.setEmailUiqueToken(emailUiqueToken);;
    }

    /**
     * @return int return the isLive
     */
    public int getIsLive() {
        return this.getIsLive();
    }

    /**
     * @param isLive the isLive to set
     */
    public void setIsLive(int isLive) {
        this.userProfileEntity.setIsLive(isLive);;
    }

    public static void main(String[] args) {
        UserProfileDto userProfileDto = new UserProfileDto(  ) ;
        userProfileDto.setEmail("sharoonpaul808@gmail.com");
        userProfileDto.setPassword( "admin");
        
        try {
            userProfileDto.addUserProfile();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}