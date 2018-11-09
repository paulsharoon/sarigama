package com.sarigama.db ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/testdb";
    public static final String USER = "testuser";
    public static final String PASS = "testpass";

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
            Class.forName("com.mysql.jdbc.Driver");  
            connection = DriverManager.getConnection( URL , USER ,PASS);
        }
        }catch(Exception e)
        {
           
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
}