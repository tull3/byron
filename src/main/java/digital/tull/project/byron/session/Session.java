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

package digital.tull.project.byron.session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import digital.tull.project.byron.engine.ConnectionManager;
import digital.tull.project.byron.engine.DataEngine;
import digital.tull.project.byron.transaction.Entity;
import digital.tull.project.byron.transaction.EntityFactory;
import digital.tull.project.byron.transaction.Table;
import digital.tull.project.byron.transaction.TableStatement;
import digital.tull.project.byron.transaction.TransactionType;



public class Session
{
    private Properties properties;
    private List<String> columnNames;
    private String tableName;
    private String pkColumn;
    private List<Entity> entityList;
    private String record;
    private Entity activeEntity;
    private TransactionType transactionType;
    
    public Session(final String tableName)
    {
    	this.tableName = tableName;
    }
    
    public Session(final String tableName, final String record, final TransactionType transactionType)
    {
    	this.transactionType = transactionType;
    	this.record = record;
    	this.tableName = tableName;
    }
    
    public Session runSession()
    {
    	loadDefaultProperties();
    	DataEngine engine = new DataEngine(properties);
    	populateData(engine);
    	
    	if (transactionType == null)
    		return this;
    	
    	final Table table = new Table(tableName, pkColumn, columnNames);
    	
    	if (transactionType == TransactionType.CREATE_ENTITY)
    	{
    		table.consumeStatement(table.withTransaction(transactionType, record), engine);
    		return this;
    	}
    	
    	System.out.println("Current Columns:");
    	
    	for (int i = 0; i < columnNames.size(); i++)
    	{
    		System.out.print(columnNames.get(i) + ":  ");
    		System.out.print(activeEntity.getProperty(columnNames.get(i)) + "  ");
    	}
    	
    	System.out.println();
    	
    	final TableStatement transaction = table.withTransaction(transactionType, record);
    	
    	table.consumeStatement(transaction, engine);
    	
    	return this;
    }
    
    private void loadDefaultProperties()
    {
        properties = new Properties();
        
        try (FileReader in = new FileReader("will.properties"))
        {
            properties.load(in);
        }
        
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
            System.out.println("User has not created default properties yet.");
            return;
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void populateData(DataEngine engine)
    {
    	//loading some potentially useful data about the current table
    	//not all of this is used right now
    	
    	String catalog = null;
        String schema = "APP";
        String table = tableName;
        String column = null;
        columnNames = new ArrayList<String>();
        Connection connection = engine.getConnection();
        
        try (
                Statement s = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet resultSet = s.executeQuery("SELECT * FROM " + tableName);
                )
        {
            DatabaseMetaData dbData = connection.getMetaData();
            
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
            //columnTypeKey = new int[numberOfColumns];
            
//            for (int i = 0; i < numberOfColumns; i++)
//            {
//            	columnTypeKey[i] = metaData.getColumnType(i+1);
//            }
            
            entityList = new ArrayList<Entity>();
            //pkValueList = new ArrayList<String>();
            
            while (resultSet.next())
            {
            	List<String[]> properties = new ArrayList<String[]>();
            	
                for (int i = 1; i <= numberOfColumns; i++)
                {
                	String[] set = new String[2];
                	
                    set[0] = metaData.getColumnName(i);
                    set[1] = resultSet.getString(metaData.getColumnName(i));
                    
                    properties.add(set);
                }
                
                Entity entity = new Entity(properties);
                
                if (resultSet.getString(pkColumn).equals(record))
               	{
                	activeEntity = entity;
               	}
                
                //pkValueList.add(resultSet.getString(pkColumn));
                entityList.add(entity);
            }
            
            resultSet.close();
            
            DataEngine.Disconnect();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void printEntityList()
    {
    	for (int i = 0; i < entityList.size(); i++)
    		System.out.println(entityList.get(i).toString());
    }
}