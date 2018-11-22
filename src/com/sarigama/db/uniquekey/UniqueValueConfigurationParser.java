package com.sarigama.db.uniquekey;

import java.io.File;
import  java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.sarigama.db.uniquekey.exception.UniqueValueXMLParserException;

public class UniqueValueConfigurationParser {

	HashSet tables = null ;
    HashMap tableNameVsuniqueKeyValue = null;
    //HashMap tableNameVsuniqueKeyWhereList = null;

    // parse file with path
    public UniqueValueConfigurationParser( String parseFile ) throws Exception{
        DocumentBuilder builder = getDocumentBuilder() ;

        File file = new File(parseFile);
        URL url = file.toURL();
        String extForm = url.toExternalForm();
        Document document = builder.parse(new InputSource(extForm));
        this.tableNameVsuniqueKeyValue = new HashMap();
        this.tables = new HashSet<String>();
        //this.tableNameVsuniqueKeyWhereList = new HashMap();

        parseDocument(document);
    }

    private String getUiqueValueKey(String tableName) throws UniqueValueXMLParserException{

        String columnName = ( String) tableNameVsuniqueKeyValue.get(tableName) ;
        if( columnName != null ){
            return columnName ;
        }
        
        throw new UniqueValueXMLParserException("Table not present or Configured properly") ;
    }

    public HashMap getTableWithUniqueKeyColoumn(){
        return tableNameVsuniqueKeyValue ;
    }
    public HashSet getTables() {
		return tables;
    }
    
    /* private HashSet getWhereCondition(String tableName) throws UniqueValueXMLParserException{

        HashSet whereConditionHashSet = ( HashSet) tableNameVsuniqueKeyWhereList.get(tableName) ;
        if( whereConditionHashSet != null ){
            return whereConditionHashSet ;
        }
        
        throw new UniqueValueXMLParserException("Table not present or Configured properly") ;
    } */

    private void parseDocument(Document document) throws Exception
    {
        Element element = document.getDocumentElement();
        if ((element != null) && (element.getNodeName().equalsIgnoreCase("unique-value-configuration"))) {
            updateConfigurationsInCache(document);
        }
    }

    private void updateConfigurationsInCache(Document document) throws UniqueValueXMLParserException , Exception
    {
        try {
            // get the table element node list
            NodeList tableList = document.getElementsByTagName("table");
            //iterate the table element list
            for (int temp = 0; temp < tableList.getLength(); temp++) {
                //single element node
                Node tableNode = tableList.item(temp);

                if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) tableNode; 
                    //get the table name from table element attribute
                    String tableName = eElement.getAttribute("name") ;
                    //add table name in tables set
                    tables.add(tableName) ;
                    //unique value generation column
                    String primaryKeyGeneration = eElement.getElementsByTagName("unique-value-column").item(0).getTextContent() ;
                    //add the unique value generation column to the particular table
                    tableNameVsuniqueKeyValue.put( tableName , primaryKeyGeneration ) ;
                    //get the list for unique value generation where column 

                    /*NodeList wherePrimaryNodeList  =  eElement.getElementsByTagName("unique-value-where-column");
                    //initialize the new HashSet for where column 
                    HashSet<String> wherecolumn = new HashSet<String>();
                    for (int temp2 = 0; temp2 < wherePrimaryNodeList.getLength() ; temp2++) { 
                        //iterate the unique-value-where-column to add on the HashSet ( wherecolumn )
                        Node wherPrimaryNode = wherePrimaryNodeList.item(temp2);
                        if (wherPrimaryNode.getNodeType() == Node.ELEMENT_NODE) { 
                            Element wherePrimaryElement = (Element) wherPrimaryNode; 
                            String wherecolumnName = wherePrimaryElement.getTextContent() ;
                            wherecolumn.add(wherecolumnName) ;
                        }
                    }
                    //add in the list
                    tableNameVsuniqueKeyWhereList.put( tableName , wherecolumn ) ; */

                }
            }
        } catch (Exception e) {
            throw new UniqueValueXMLParserException( e.getMessage() + "" );
        }
       
    }

    private static DocumentBuilder getDocumentBuilder() throws Exception
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        //builder.setEntityResolver(getEntityResolver());
        return builder;
    }

    public static void main(String[] args) {
        try {
           String filePath = "E:\\Eclipse\\sarigama\\sarigama\\src\\com\\sarigama\\db\\uniquekey\\unique-value-handler.xml" ;
        	// String filePath = "\\src\\com\\sarigama\\db\\uniquekey\\unique-value-handler.xml" ;
           // String filePath = "unique-value-handler.xml" ;

            UniqueValueConfigurationParser uniqueValueConfigurationParser = new UniqueValueConfigurationParser(filePath) ;

            System.out.println( uniqueValueConfigurationParser.tableNameVsuniqueKeyValue.toString() ) ;
            System.out.println( uniqueValueConfigurationParser.tables.toString() ) ;
            //System.out.println( uniqueValueConfigurationParser.tableNameVsuniqueKeyWhereList.toString() ) ;
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
