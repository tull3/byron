/*
 * Copyright 2017 William Jackson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package digital.tull.project.byron.transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import digital.tull.project.byron.engine.ConnectionManager;
import digital.tull.project.byron.engine.DataEngine;
import digital.tull.project.byron.logic.Menus;
import digital.tull.project.byron.session.Session;



public class Table
{
    private Column column;
    private String tableName;
    private String activeRecord;
    private String pkColumn;
    private List<String> columnNames;
    private List<Entity> entityList;
    private Session session;
    
    public Table(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
    }
    
    public List<Entity> getEntityList()
    {
    	return entityList;
    }

    public String getPKColumn()
    {
        return pkColumn;
    }
    
    public void setActiveRecord(String activeRecord)
    {
    	this.activeRecord = activeRecord;
    }
    
//    public String toString()
//    {
//        
//        
//        return session.getUser().getID() + " performed a " + transactionType + " transaction on table " + workingTable + " and record " + workingRecord + ".";
//    }
//    
//    public void log()
//    {
//        try (FileWriter out = new FileWriter(session.getUser().getID() + ".log", true))
//        {
//            out.write(toString());
//            //out.flush();
//        }
//        
//        catch (IOException e)
//        {
//            System.err.println(e.toString());
//        }
//        
//        pkColumn = null;
//        transactionType = null;
//        workingRecord = null;
//        workingTable = null;
//    }
    
    public void populateData()
    {
    	loadDefaultModules();
    	
    	String catalog = null;
        String schema = "APP";
        String table = tableName.substring(4);
        String column = null;
        columnNames = new ArrayList<String>();
        
        try (
                Statement s = DataEngine.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet resultSet = s.executeQuery("SELECT * FROM " + tableName);
                )
        {
            DatabaseMetaData dbData = DataEngine.GetConnection().getMetaData();
            
            ResultSet rs2 = dbData.getColumns(catalog, schema, table, column);
            
            ResultSet rs1 = dbData.getPrimaryKeys(catalog, schema, table);
            
            while (rs1.next())
            {
                pkColumn = rs1.getString(4);
            }
            
            rs1.close();
            
            while (rs2.next())
            {
            	columnNames.add(rs2.getString(4));
            }
            
            rs2.close();
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            entityList = new ArrayList<Entity>();
            
            while (resultSet.next())
            {
                Entity entity = new Entity();
                
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    entity.setProperty(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
                }
                
                entityList.add(entity);
            }
            
            DataEngine.Disconnect();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            DataEngine.Disconnect();
        }
    }
    
    public void mergeEntity(Entity entity)
    {
    	//TODO:  make diff object/string and submit to statement builder
    	//TODO:  statement builder will use stringbuilder and iterator to insert
    	//				preparedstatement segments.
    	
    	System.out.println(entity.toString());
    	
    	Connection connection = DataEngine.GetConnection();
    	
    	try
    	{
    		connection.setAutoCommit(false);
    	}
    	
    	catch (SQLException e)
    	{
    		System.out.println(e.toString());
    	}
    		
    	try (
                Statement s = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
    			PreparedStatement updateStatement = connection.prepareStatement(
    					"UPDATE ? SET ? = ? WHERE ? = ?");
                ResultSet rs = s.executeQuery("SELECT * FROM " + tableName);
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            if (entity.hasDeleteFlag())
            {
//            	rs.beforeFirst();
//                
//                while (rs.next())
//                {
//                    if (rs.getString(1).equals(activeRecord))
//                        break;
//                }
//                        
//                String record = rs.getString(1);
//                       
//                int option1 = Menus.ConfirmDelete(record);
//                        
//                if (option1 == 1)
//                {
//                    rs.deleteRow();
//                }
//                        
//                else
//                {
//                    System.out.println("Canceling.");
//                    return;
//                }
            	s.execute("DELETE FROM " + tableName + " WHERE " + pkColumn + " = " + entity.getPKValue(pkColumn));
            }
            
            else
            {
            	System.out.println("test-else");
            	System.out.println(entity.getPKValue(pkColumn));
	            while (rs.next())
	            {
	                if (rs.getString(pkColumn).equals(activeRecord))
	                    break;
	                
	            }
	            System.out.println(rs.getString(pkColumn));
	            if (rs.isAfterLast())
	            {
		            rs.moveToInsertRow();
		                    
		            for (int i = 1; i <= numberOfColumns; i++)
		            {
		                rs.updateString(metaData.getColumnName(i),
		                        entity.getProperty(metaData.getColumnName(i)));
		            }
		            System.out.println("test-if");
		            rs.insertRow();
	            }
	            
	            else
	            {
//	            	System.out.println(entity.getPKValue(pkColumn));
//	            	System.out.println(rs.getString("TITLE"));
//	            	
//	            	for (int i = 1; i <= numberOfColumns; i++)
//	                {
//	                    rs.updateString(metaData.getColumnName(i),
//	                            entity.getProperty(metaData.getColumnName(i)));
//	                    System.out.println(rs.getString(metaData.getColumnName(i)));
//	                    System.out.println(entity.getProperty(metaData.getColumnName(i)));
//	                }
//	                System.out.println(rs.getString("TITLE"));
//	                rs.updateRow();
//	                
//	                System.out.println(rs.getString("TITLE"));
	            	updateStatement.setString(1, tableName);
	            	//updateStatement.setString();
	            }
            }
            connection.commit();
            System.out.println(rs.getString("TITLE"));
            ConnectionManager.Stop();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            ConnectionManager.Stop();
//            System.exit(1);
        }
    }
    
    public Entity buildEntity(Entity entity)
    {
    	if (entity.hasDeleteFlag())
    		return entity;
    	
        Scanner input = new Scanner(System.in);
        
        Entity newEntity = new Entity();
        
        int option = 0;
        
        while (option == 0)
        {
            String[] set = new String[2];
            
            newEntity.reset();
            
            if (entity.isBlank())
            {
        
                for (int i = 0; i < entityList.get(1).getSize(); i++)
                {
                    set = entityList.get(1).getProperty(i);
            
                    System.out.println(set[0]);
                    newEntity.setProperty(set[0], input.nextLine());
                }
                
                System.out.println(newEntity.toString());
            }
            
            else
            { 
                for (int i = 0; i < entity.getSize(); i++)
                {
                    set = entity.getProperty(i);
            
                    System.out.println("Current " + set[0] + ":  " + set[1]);
            
                    System.out.println("New " + set[0] + ":  ");
                    newEntity.setProperty(set[0], input.nextLine());
                }
            
                System.out.println("Modified entity:  ");

                System.out.println(newEntity.toString());
            }
            
            int i = Menus.ConfirmChanges();
            
            if (i == 3)
                option = 3;
                
            if (i == 1)
                option = 1;
        }
        
        if (option == 3)
            newEntity.setCancelFlag(true);
        
        input.close();
        
        return newEntity;
    }
    
    public Table loadDefaultModules()
    {
    	return this;
    }
    
    public void consumeStatement(final TableStatement statement)
    {
    	
    }
    
    public int loadModule(final String moduleName)
    {
    	//TODO: write code for module loading.
    	
    	
    	
    	return 0;
    }
}
