package com.sarigama.security.authentication;

import java.security.spec.InvalidKeySpecException;

import com.sarigama.db.exception.DBException;
import com.sarigama.security.authentication.data.AuthenticationToken;
import com.sarigama.security.authentication.exception.AuthenticationException;
import com.sarigama.security.authentication.exception.UserServiceException;
import com.sarigama.security.authentication.utill.AuthenticationUtil;
import com.sarigama.security.dto.UserProfileDto;
import com.sarigama.security.entity.UserAuthToken;
import com.sarigama.security.entity.UserProfileEntity;
import com.sarigama.utils.DateUtil;

public class AuthenticationService {

    private AuthenticationUtil authenticationUtil ;
  
    public AuthenticationService(){
        this.authenticationUtil = new AuthenticationUtil() ;
    }

    public AuthenticationToken authenticate(String userName, String userPassword) throws AuthenticationException , DBException , UserServiceException
    {
        UserProfileDto userProfile = new UserProfileDto();
        userProfile.getUserProfile(userName); 
        String secureUserPassword = null;
        try {
            secureUserPassword = authenticationUtil.generateSecurePassword(userPassword, userProfile.getSalt());
        } catch (InvalidKeySpecException ex) {
            throw new AuthenticationException(ex.getLocalizedMessage());
        }
        boolean authenticated = false;
        if (secureUserPassword != null && secureUserPassword.equalsIgnoreCase(userProfile.getEPassword())) {
            if (userName != null && userName.equalsIgnoreCase(userProfile.getUserName())) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            throw new AuthenticationException("Authentication failed");
        }

        return this.issueNewAuthorisedTokenForUser(userProfile);
    }

    public UserProfileDto resetSecurityCridentials(UserProfileDto userProfile)  throws UserServiceException {
        String salt = authenticationUtil.generateSalt(AuthConstant.USER_IDENTIFICATION_SALT_LENGTH);
        String secureUserPassword = null;
        try {
            secureUserPassword = authenticationUtil.generateSecurePassword(userProfile.getPassword(), salt);
        } catch (InvalidKeySpecException ex) {
            throw new UserServiceException(ex.getLocalizedMessage());
        }

        userProfile.setSalt(salt);
        userProfile.setEPassword(secureUserPassword);
        //userProfile.updateUserProfile();
        return userProfile;
    }

    public synchronized UserProfileEntity setUserSignature(UserProfileEntity userProfileEntity) throws UserServiceException {
        String salt = authenticationUtil.generateSalt(AuthConstant.USER_SIGNATURE_SALT_LENGTH);
        System.out.println("salt    : " + salt);
        String userSignature ; 
        try {
            userSignature = salt.substring(0 , ( AuthConstant.USER_SIGNATURE_SALT_LENGTH / 2 )) 
            + authenticationUtil.SEPARTOR + userProfileEntity.getUserId() + "" + authenticationUtil.SEPARTOR 
            + salt.substring( ( AuthConstant.USER_SIGNATURE_SALT_LENGTH / 2 ) , AuthConstant.USER_SIGNATURE_SALT_LENGTH ) ;
        } catch (Exception e) {
            throw new UserServiceException(e.getLocalizedMessage());
        }
        System.out.println("userSignature    : " + userSignature);
        userProfileEntity.setUserSignature(userSignature);

        return userProfileEntity ;
    }

    public synchronized UserProfileEntity setUserEmailToken(UserProfileEntity userProfileEntity) throws UserServiceException {
        String salt = authenticationUtil.generateSalt(AuthConstant.USER_EMAIL_SALT_LENGTH);
        System.out.println("salt    : " + salt);
        String emailToken ; 
        try {
            emailToken = salt.substring(0 , ( AuthConstant.USER_EMAIL_SALT_LENGTH / 2 )) 
            + authenticationUtil.SEPARTOR + userProfileEntity.getUserId() + "" + authenticationUtil.SEPARTOR 
            + salt.substring( ( AuthConstant.USER_EMAIL_SALT_LENGTH / 2 ) , AuthConstant.USER_EMAIL_SALT_LENGTH ) ;
        } catch (Exception e) {
            throw new UserServiceException(e.getLocalizedMessage());
        }
        System.out.println("EmailToken    : " + emailToken);
        userProfileEntity.setEmailUiqueToken(emailToken);

        return userProfileEntity ;
    }

    public synchronized AuthenticationToken issueNewAuthorisedTokenForUser( UserProfileDto userProfileDto ) throws UserServiceException {

        AuthenticationToken authenticationToken = new AuthenticationToken();
        UserAuthToken userAuthToken = new UserAuthToken();
        String salt = authenticationUtil.generateSalt(AuthConstant.USER_AUTH_TOKEN_SLAT_LENGTH);
        System.out.println("salt    : " + salt);
        Long cTime = DateUtil.getCurrentTime() ;
        Long expiry = DateUtil.calculateDate( AuthConstant.AUTH_TOKEN_EXPIRY );
        String authToken ;

        try {
            authToken = salt.substring(0 , ( AuthConstant.USER_AUTH_TOKEN_SLAT_LENGTH / 2 )) 
            + authenticationUtil.SEPARTOR + cTime  + "" + authenticationUtil.SEPARTOR 
            + salt.substring( ( AuthConstant.USER_AUTH_TOKEN_SLAT_LENGTH / 2 ) , AuthConstant.USER_AUTH_TOKEN_SLAT_LENGTH ) ;
        } catch (Exception e) {
            throw new UserServiceException(e.getLocalizedMessage());
        }

        userAuthToken.setUserID( userProfileDto.getUserId() );
        userAuthToken.setAuthToken(authToken);
        userAuthToken.setExpiryDate( expiry );
        userAuthToken.setDomainID(new Long(0));
        userAuthToken.setIsLive( userAuthToken.ENABLED );
        userAuthToken.setCreatedDate(cTime);
        userAuthToken.setUpdatedDate(cTime);
        try{
            userProfileDto.saveUserAuthToken(userAuthToken);
        }catch(Exception e){
        	e.printStackTrace();
            throw new UserServiceException(e.getLocalizedMessage());
        }

        authenticationToken.setUserSignature(userProfileDto.getUserSignature());
        authenticationToken.setAuthToken(authToken);
        return authenticationToken ;
    }

    public static void main(String[] args) {

        try {
            UserProfileDto userProfileDto = new UserProfileDto("sharoonpaul808@gmail.com");
            AuthenticationService authuS = new AuthenticationService();
            authuS.setUserSignature(userProfileDto.getUserProfileEntity());
        } catch (Exception e) {
           e.printStackTrace();
        }
        
    }

}