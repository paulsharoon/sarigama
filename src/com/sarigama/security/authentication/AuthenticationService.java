package com.sarigama.security.authentication;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import com.sarigama.security.authentication.exception.AuthenticationException;
import com.sarigama.security.authentication.exception.UserServiceException;
import com.sarigama.security.authentication.utill.AuthenticationUtil;
import com.sarigama.security.dto.UserProfileDto;
import com.sarigama.security.entity.UserProfileEntity;

public class AuthenticationService {

    private AuthenticationUtil authenticationUtil ;
  
    public AuthenticationService(){
        this.authenticationUtil = new AuthenticationUtil() ;
    }

    public UserProfileDto authenticate(String userName, String userPassword) throws AuthenticationException , Exception{
        UserProfileDto userProfile = new UserProfileDto();
        UserProfileEntity userEntity = userProfile.getUserProfile(userName); // User name must be unique in our system
        // Here we perform authentication business logic
        // If authentication fails, we throw new AuthenticationException
        // other wise we return UserProfile Details
        String secureUserPassword = null;
        try {
            secureUserPassword = authenticationUtil.generateSecurePassword(userPassword, userEntity.getSalt());
        } catch (InvalidKeySpecException ex) {
            // Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException(ex.getLocalizedMessage());
        }
        boolean authenticated = false;
        if (secureUserPassword != null && secureUserPassword.equalsIgnoreCase(userEntity.getEPassword())) {
            if (userName != null && userName.equalsIgnoreCase(userEntity.getUserName())) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            throw new AuthenticationException("Authentication failed");
        }
       
        return userProfile;
    }

    public UserProfileDto resetSecurityCridentials(UserProfileDto userProfile)  throws UserServiceException {
        // Generate salt
        String salt = authenticationUtil.generateSalt(AuthConstant.USER_IDENTIFICATION_SALT_LENGTH);
        // Generate secure user password 
        String secureUserPassword = null;
        try {
            secureUserPassword = authenticationUtil.generateSecurePassword(userProfile.getPassword(), salt);
        } catch (InvalidKeySpecException ex) {
            throw new UserServiceException(ex.getLocalizedMessage());
        }

        userProfile.setSalt(salt);
        userProfile.setEPassword(secureUserPassword);
        UserProfileEntity userEntity = new UserProfileEntity();
        // Update to database
        userProfile.updateUserProfile();
        return userProfile;
    }

    public String issueSecureToken(UserProfileDto userProfile) throws AuthenticationException {
        String returnValue = null;
        // Get salt but only part of it
        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;
        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = authenticationUtil.encrypt(userProfile.getEPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            // Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }
        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();
        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        // Access token added to the table

        // userProfile.setToken(tokenToSaveToDatabase);
        // storeAccessToken(userProfile);
        
        return returnValue;
    }

}