<%@ page import="com.sarigama.db.uniquekey.UniqueValueContainer" %>
<html> 
   <head><title>Unique Value Generator</title></head> 
   
   <body>
      <% 
        
      out.println("tables : " + UniqueValueContainer.tableDetails.getTables().toString() + "<br><br>");
      out.println("tableNameVsuniqueKeyValue : " + UniqueValueContainer.tableDetails.getTableWithUniqueKeyColoumn().toString() +  "<br><br>");
      out.println(UniqueValueContainer.uniqeKeys.toString() + "<br><br>"); 
      out.println( System.getProperty("catalina.base"));
      
      String tableName = "A_USER" ;
      /* out.println(UniqueValueContainer.getNextUniqueValue(tableName) + "<br><br>");
      out.println(UniqueValueContainer.getNextUniqueValue(tableName) + "<br><br>");
      out.println(UniqueValueContainer.getNextUniqueValue(tableName) + "<br><br>");
      out.println(UniqueValueContainer.getNextUniqueValue(tableName) + "<br><br>");
      out.println(UniqueValueContainer.getNextUniqueValue(tableName) + "<br><br>"); */
            
      %>
   </body> 
</html> 