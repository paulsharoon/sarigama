package com.sarigama.security.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sarigama.db.ConnectionFactory;
import com.sarigama.db.exception.DBException;
import com.sarigama.db.uniquekey.UniqueValueContainer;
import com.sarigama.security.entity.UserProfileEntity;
import com.sarigama.utils.DateUtil;

public class UserProfileDB extends ConnectionFactory {
   
    public void openConnection() throws Exception {
       this.getConnection();
    }

    public void closeConnection() {
        this.finalize();
    }

    public synchronized UserProfileEntity saveUserProfile(UserProfileEntity userProfile) throws DBException {
        PreparedStatement preparedStatement = null  ;
        try {
           
            String tableName = "A_USER" ;
            String sqlstring = "INSERT INTO " + tableName + " ( USER_ID , USER_NAME  , EMAIL  , IS_LIVE  , CREATED_DATE , UPDATE_DATE ) " ;
            sqlstring += " VALUES ( ? , ? , ? , ? , ? , ?)" ;
            Long USER_ID = UniqueValueContainer.getNextUniqueValue(tableName);
            userProfile.setUserId(USER_ID);
            this.openConnection();
            Long currentTime = DateUtil.getCurrentTime() ;
            preparedStatement =  this.connection.prepareStatement( sqlstring );
            preparedStatement.setLong(1, userProfile.getUserId());
            preparedStatement.setString(2, userProfile.getUserName());
            preparedStatement.setString(3, userProfile.getEmail());
            preparedStatement.setInt(4, userProfile.getIsLive());
            preparedStatement.setLong(5, currentTime );
            preparedStatement.setLong(6, currentTime );

            try {
                preparedStatement.execute();
                Long AUTH_ID = saveUserSecurity(userProfile) ;
                Long IDENTIFICATION_ID = saveUserIdentification(userProfile) ;
                Long PROFILE_ID = saveUserProfileInfo(userProfile) ;
            } catch (Exception e) {
               
            }
        } catch (Exception e) {
            throw new DBException("" + e.getMessage());
        }finally{
            try {
                if (null != preparedStatement) { preparedStatement.close() ; } 
            } catch (Exception e) {
               e.printStackTrace();
            }
            try {
                this.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userProfile;
    }
   
    public synchronized Long saveUserSecurity(UserProfileEntity userProfile) throws Exception{
        Long AUTH_ID = null ;
        PreparedStatement preparedStatement = null  ;
        try {
           
            String tableName = "A_SECURITY" ;
            String sqlstring = "INSERT INTO " + tableName + " (  AUTH_ID  ,USER_ID  ,AUTH_SALT  ,USER_SIGNATURE  ,USER_EMAIL_UNIQUE_TOKEN  ,CREATED_DATE  ,UPDATED_DATE   ) " ;
            sqlstring += " VALUES ( ? , ? , ? , ? , ? , ? , ?)" ;
            AUTH_ID = UniqueValueContainer.getNextUniqueValue(tableName);
            //this.openConnection();
            Long currentTime = DateUtil.getCurrentTime() ;
            preparedStatement =  this.connection.prepareStatement( sqlstring );
            preparedStatement.setLong(1, AUTH_ID );
            preparedStatement.setLong(2, userProfile.getUserId());
            preparedStatement.setString(3, userProfile.getSalt());
            preparedStatement.setString(4, "" );
            preparedStatement.setString(5, "" );
            preparedStatement.setLong(6, currentTime );
            preparedStatement.setLong(7, currentTime );

            preparedStatement.execute();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            try {
                if (null != preparedStatement) { preparedStatement.close() ; } 
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        return AUTH_ID ;
    }

    public synchronized Long saveUserIdentification(UserProfileEntity userProfile) throws Exception{
        Long  IDENTIFICATION_ID = null ;
        PreparedStatement preparedStatement = null  ;
        try {
           
            String tableName = "A_IDENTIFICATION" ;
            String sqlstring = "INSERT INTO " + tableName + " ( IDENTIFICATION_ID  , USER_ID  ,IDENTIFICATION_KEY  ,IS_LIVE  ,CREATED_DATE     ) " ;
            sqlstring += " VALUES ( ? , ? , ? , ? , ? )" ;
            IDENTIFICATION_ID = UniqueValueContainer.getNextUniqueValue(tableName);
            //this.openConnection();
            Long currentTime = DateUtil.getCurrentTime() ;
            preparedStatement =  this.connection.prepareStatement( sqlstring );
            preparedStatement.setLong(1, IDENTIFICATION_ID );
            preparedStatement.setLong(2, userProfile.getUserId());
            preparedStatement.setString(3, userProfile.getEPassword());
            preparedStatement.setInt(4, 1 );
            preparedStatement.setLong(5, currentTime );

            preparedStatement.execute();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            try {
                if (null != preparedStatement) { preparedStatement.close() ; } 
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        return IDENTIFICATION_ID ;
    }

    public synchronized Long saveUserProfileInfo(UserProfileEntity userProfile) throws Exception{
        Long PROFILE_ID = null ;
        PreparedStatement preparedStatement = null  ;
        try {
           
            String tableName = "A_USER_PROFILE" ;
            String sqlstring = "INSERT INTO " + tableName + " ( PROFILE_ID , USER_ID , CREATED_DATE , UPDATED_DATE ) " ;
            sqlstring += " VALUES ( ? , ? , ? , ? )" ;
            PROFILE_ID = UniqueValueContainer.getNextUniqueValue(tableName);
            //this.openConnection();
            Long currentTime = DateUtil.getCurrentTime() ;
            preparedStatement =  this.connection.prepareStatement( sqlstring );
            preparedStatement.setLong(1, PROFILE_ID );
            preparedStatement.setLong(2, userProfile.getUserId());
            preparedStatement.setLong(3, currentTime );
            preparedStatement.setLong(4, currentTime );

            preparedStatement.execute();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            try {
                if (null != preparedStatement) { preparedStatement.close() ; } 
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        return PROFILE_ID ;
    }



    public UserProfileEntity updateUserProfile(UserProfileEntity userProfile) {
       
        return userProfile;
    }
   
    
    public UserProfileEntity getUserProfile(String userName) throws DBException{
        UserProfileEntity userProfileEntity = null ;
        PreparedStatement preparedStatement = null  ;
        ResultSet resultSet = null ;
        try {
            String sqlString = "SELECT A_USER.USER_ID , A_USER.USER_NAME , A_USER.EMAIL , A_USER.IS_LIVE , " ;
            sqlString += " A_SECURITY.AUTH_SALT , A_SECURITY.USER_EMAIL_UNIQUE_TOKEN , A_IDENTIFICATION.IDENTIFICATION_KEY " ;
            sqlString += " FROM A_USER LEFT JOIN A_SECURITY ON A_USER.USER_ID = A_SECURITY.USER_ID " ;
            sqlString += " LEFT JOIN A_IDENTIFICATION ON A_USER.USER_ID = A_IDENTIFICATION.USER_ID " ;
            sqlString += " WHERE A_USER.USER_NAME = ? AND A_IDENTIFICATION.IS_LIVE = 1 ;" ;
            this.openConnection();
            preparedStatement = this.connection.prepareStatement( sqlString );
            preparedStatement.setString(1 , userName );
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {  
                userProfileEntity = new UserProfileEntity() ;
                userProfileEntity.setUserId( resultSet.getLong("A_USER.USER_ID")) ;
                userProfileEntity.setUserName( resultSet.getString("A_USER.USER_NAME")) ;
                userProfileEntity.setEmail( resultSet.getString("A_USER.EMAIL")) ;
                userProfileEntity.setIsLive( resultSet.getInt("A_USER.IS_LIVE")) ;
                userProfileEntity.setSalt( resultSet.getString("A_SECURITY.AUTH_SALT")) ;
                userProfileEntity.setEmailUiqueToken( resultSet.getString("A_SECURITY.USER_EMAIL_UNIQUE_TOKEN")) ;
                userProfileEntity.setEPassword( resultSet.getString("A_IDENTIFICATION.IDENTIFICATION_KEY")) ;

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        }finally{
            try { 
            if (null != preparedStatement) { preparedStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.closeConnection();
            }catch(Exception e){

             }
        }

        return userProfileEntity;
    }

    public static void main(String[] args) {
        UserProfileDB userProfileDB = new  UserProfileDB();
        try {
         System.out.println(  userProfileDB.getUserProfile("sharoonpaul808@gmail.com").toString() ) ;
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}