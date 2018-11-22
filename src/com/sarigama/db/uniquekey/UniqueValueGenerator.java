package com.sarigama.db.uniquekey;

import java.security.KeyStore.Entry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import com.sarigama.db.ConnectionFactory;
import com.sarigama.db.uniquekey.exception.UniqueKeyException;

public class UniqueValueGenerator extends ConnectionFactory  {

    public void checkTablePresent(HashMap result , HashSet<String> tables ) throws UniqueKeyException 
    {
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {
            String sqlString = "SELECT * FROM TABLE_NAME ;" ;
            this.getConnection();
            for( String tableName : tables ){
                //System.out.println("tableName : " +  tableName ) ;
                String excecuteQuery = sqlString.replace("TABLE_NAME" , tableName);
                prepStatement = this.connection.prepareStatement( excecuteQuery );
                //prepStatement.setString(1, tableName );
                //System.out.println(prepStatement);
                try {
                    //System.out.println("try "  ) ;
                    resultSet = prepStatement.executeQuery();
                    result.put( tableName , new HashMap<String,Long>() );

                    //System.out.println("result : " +  result.toString() ) ;
                } catch (Exception e) {

                   // e.printStackTrace() ;
                    //TODO: handle exception
                }
            }

        } catch (Exception e) {
            throw new UniqueKeyException( e.getMessage()) ;
        }finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ 

            }
        }
    }

    public void generateUniqueKey(HashMap<String,HashMap<String,Long>> newUniqueColumn , HashMap<String , String > columnMap  )  throws UniqueKeyException 
    { 
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        Long uniqueID = new Long(1);
        try {
            String sqlString = "SELECT MAX( PRIMARY_COLUMN ) UNIQUE_ID FROM TABLE_NAME ;" ;
            this.getConnection();
            for (String tableName : newUniqueColumn.keySet() )  {

                //System.out.println("tableName : " +  tableName ) ;
                uniqueID = new Long(1);
                HashMap<String,Long> uniqueValueColoumn = newUniqueColumn.get( tableName );
                String primaryKeyColumn = columnMap.get(tableName);
                //System.out.println("primaryKeyColumn : " +  primaryKeyColumn ) ;
                
                String excecuteQuery = sqlString.replace("TABLE_NAME" , tableName);
                excecuteQuery = excecuteQuery.replace("PRIMARY_COLUMN", primaryKeyColumn);
                //prepStatement.setString(1, primaryKeyColumn );
                //prepStatement.setString(2, tableName );
                prepStatement = this.connection.prepareStatement( excecuteQuery );
                resultSet = prepStatement.executeQuery();
                if( resultSet.next() ){
                    uniqueID = resultSet.getLong("UNIQUE_ID") ;
                }

                // Adding the unique coloumn and present Maximum key
                uniqueValueColoumn.put( primaryKeyColumn , uniqueID );
                newUniqueColumn.put(tableName , uniqueValueColoumn);
                //System.out.println("newUniqueColumn : " +  newUniqueColumn.toString() ) ;
            }
        } catch (Exception e) {
            throw new UniqueKeyException( e.getMessage()) ;
        } finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ 

            }
        }
    }

}