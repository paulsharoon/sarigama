package com.sarigama.db ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/sarigama?useSSL=false";
    public static final String USER = "sharoon";
    public static final String PASS = "sharoon";

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
     }
}