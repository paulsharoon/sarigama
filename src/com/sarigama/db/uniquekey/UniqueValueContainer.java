package com.sarigama.db.uniquekey;


import java.util.HashMap;

import com.sarigama.db.uniquekey.UniqueValueConfigurationParser;
import com.sarigama.db.uniquekey.exception.UniqueKeyException;

public class UniqueValueContainer {

    public static String serverPath = "" ;
    public static String configPath = "" ;
    public static String filePath = "E:\\Eclipse\\sarigama\\sarigama\\src\\com\\sarigama\\db\\uniquekey\\" ;
    public static String fileName = "unique-value-handler.xml" ;
    public static UniqueValueConfigurationParser tableDetails ; 

    //public static HashMap tablePresent = new HashMap() ;
    public static HashMap uniqeKeys ;

    static {
        try {
            tableDetails = new UniqueValueConfigurationParser( filePath + fileName ) ;
            uniqeKeys = new HashMap();
            generateUniqueIdentifier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateUniqueIdentifier() throws Exception , UniqueKeyException {
        UniqueValueGenerator uniqueValueGenerator = new UniqueValueGenerator() ;
        uniqueValueGenerator.checkTablePresent( uniqeKeys  , tableDetails.getTables() );
        uniqueValueGenerator.generateUniqueKey( uniqeKeys , tableDetails.getTableWithUniqueKeyColoumn() );
    }

    public static synchronized Long getNextUniqueValue(String tableName) throws UniqueKeyException {
        Long toRet  = null;
        tableName = tableName.toUpperCase();
        HashMap<String , Long> uniqueColoumnValues = (HashMap<String, Long>) uniqeKeys.get(tableName);
        if(  uniqueColoumnValues == null  ){
            throw new UniqueKeyException("Table " + tableName + " not present");
        }
        
        tableDetails.getTableWithUniqueKeyColoumn();
        String primaryColumn = (String) tableDetails.getTableWithUniqueKeyColoumn().get( tableName );
        try{
            toRet = (Long) uniqueColoumnValues.get( primaryColumn ) ;
        }catch(Exception e){
            throw new UniqueKeyException( e.getMessage() ) ;
        }
        
        if( toRet == null){
            throw new UniqueKeyException( "Generation Exception , Please check the primary column for " + tableName + " correctly configured" ) ;
        }

        toRet = toRet + new Long(1) ;
        uniqueColoumnValues.put( primaryColumn , toRet ) ;
        uniqeKeys.put( tableName , uniqueColoumnValues) ;

        
        return toRet ;
    }

    public static synchronized Long getUniqueValue(String tableName) throws UniqueKeyException {
        Long toRet  = null;
        tableName = tableName.toUpperCase();
        HashMap<String , Long> uniqueColoumnValues = (HashMap<String, Long>) uniqeKeys.get(tableName);
        if(  uniqueColoumnValues == null  ){
            throw new UniqueKeyException("Table " + tableName + " not present");
        }

        tableDetails.getTableWithUniqueKeyColoumn();
        String primaryColumn = (String) tableDetails.getTableWithUniqueKeyColoumn().get( tableName );

        try{
            toRet = (Long) uniqueColoumnValues.get( primaryColumn ) ;
        }catch(Exception e){
            throw new UniqueKeyException( e.getMessage() ) ;
        }
        
        if( toRet == null){
            throw new UniqueKeyException( "Generation Exception , Please check the primary column for " + tableName + " correctly configured" ) ;
        }
        
        return toRet ;
    }


    public static void main(String[] args) {
        
        System.out.println(UniqueValueContainer.tableDetails.tableNameVsuniqueKeyValue.toString());
        // try {
        //     generateUniqueIdentifier();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        System.out.println("after Generation");
        System.out.println(UniqueValueContainer.uniqeKeys.toString());

        try {
            System.out.println( UniqueValueContainer.getUniqueValue("TEST1") );
            System.out.println( UniqueValueContainer.getNextUniqueValue("Test1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
