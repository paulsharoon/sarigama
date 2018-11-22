package com.sarigama.db ;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/sarigama?useSSL=false";
    public static final String USER = "";
    public static final String PASS = "";

    /**
     * Get a connection to database
     * @return Connection object
     */
    protected Connection connection = null;
    public final void getConnection() throws Exception
	{
        try{
       	if (connection == null) 
		{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            connection = DriverManager.getConnection( URL , USER ,PASS);
            System.out.println("Connection find");
        }
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }

    public final void finalize()
	{
		try
		{
			if(connection != null || connection.isClosed() == false)
			{
				connection.close(); connection = null;
			}
 	    }
		catch(SQLException e)
		{
			
		}
    }
     public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory() ;
        connectionFactory.getConnection() ;

        DatabaseMetaData meta = connectionFactory.connection.getMetaData();
        ResultSet res = meta.getTables(null, null, "TABLE3",  new String[] {"TABLE"});
        if (res.next()) {
            System.out.println(
                "   "+res.getString("TABLE_CAT") 
            + ", "+res.getString("TABLE_SCHEM")
            + ", "+res.getString("TABLE_NAME")
            + ", "+res.getString("TABLE_TYPE")
            + ", "+res.getString("REMARKS")); 
        }else {
            System.out.println("Table not found" );
        }
     }
}