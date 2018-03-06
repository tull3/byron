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

package digital.tull.project.byron.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import digital.tull.project.byron.engine.DataEngine;
import digital.tull.project.byron.transaction.StringColumn;
import digital.tull.project.byron.transaction.Column;
import digital.tull.project.byron.transaction.DecimalColumn;
import digital.tull.project.byron.transaction.DeleteTransaction;
import digital.tull.project.byron.transaction.DoubleColumn;
import digital.tull.project.byron.transaction.Entity;
import digital.tull.project.byron.transaction.InsertTransaction;
import digital.tull.project.byron.transaction.IntegerColumn;
import digital.tull.project.byron.transaction.NullColumn;
import digital.tull.project.byron.transaction.Table;
import digital.tull.project.byron.transaction.Transaction;
import digital.tull.project.byron.transaction.TransactionType;
import digital.tull.project.byron.transaction.UpdateTransaction;

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
    private DataEngine engine;
    private Transaction transaction;
    
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
    
    public Session startSession()
    {
    	loadDefaultProperties();
    	engine = new DataEngine(properties);
    	
    	if (checkTableName(tableName) == 1)
    	{
    		System.out.println("Not a valid table name.");
    		System.exit(0);
    	}
    	
    	populateData();
    	
    	return this;
    }
    
    private Transaction formTransaction()
    {
    	Scanner input = new Scanner(System.in);
    	String[] primaryKeys = {pkColumn, "'" + record + "'"};
    	
    	
    	if (transactionType == TransactionType.MODIFY_ENTITY)
    	{
    		String[] newValues = new String[2];
    		
    		for (int i = 1; i <= 2; i++)
    		{
    			if (i == 1)
    			{
    				System.out.println("Column to update:");
    				newValues[0] = input.nextLine().toUpperCase();
    			}
    			else
    			{
    				System.out.println("Value to insert:");
    				newValues[1] = "'" + input.nextLine() + "'";
    			}
    		}
    		
    		input.close();
    		
    		return new UpdateTransaction(tableName, primaryKeys, newValues);
    	}
    	
    	else if (transactionType == TransactionType.CREATE_ENTITY)
    	{
    		StringBuilder newStatement = new StringBuilder("( ");
    		
    		for (int i = 0; i < columnNames.size(); i++)
        	{
        		System.out.println(columnNames.get(i));
        		
        		String inputString = input.nextLine();
        		
        		if (inputString.equals(""))
        			newStatement.append("NULL, ");
        		else
        			newStatement.append("'" + inputString + "'" + ", ");
        	}
    		
    		newStatement.deleteCharAt(newStatement.length() - 1);
        	
        	newStatement.deleteCharAt(newStatement.length() - 1);
        	
        	newStatement.append(" )");
        	
        	input.close();
    		
    		return new InsertTransaction(tableName, newStatement.toString());
    	}
    	
    	else if (transactionType == TransactionType.DELETE_ENTITY)
    	{
    		input.close();
    		
    		return new DeleteTransaction(tableName, primaryKeys);
    	}
    	
    	input.close();
    	
    	return transaction;
    }
    
    public void runSession()
    {
    	final Table table = new Table(tableName, pkColumn, columnNames);
    	
    	if (transactionType == TransactionType.CREATE_ENTITY)
    	{
    		table.consumeStatement(formTransaction(), engine);
    		return;
    	}
    	
    	System.out.println("Current Columns:");
    	
    	for (int i = 0; i < columnNames.size(); i++)
    	{
    		System.out.print(columnNames.get(i) + ":  ");
    		System.out.print(activeEntity.getColumnString(columnNames.get(i)) + "  ");
    	}
    	
    	System.out.println();
    	
    	table.consumeStatement(formTransaction(), engine);
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
    
    private void populateData()
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
            	List<Column> columns = new ArrayList<Column>();
            	
                for (int i = 1; i <= numberOfColumns; i++)
                {
                	if (metaData.getColumnType(i) == 12)
                	{
                		columns.add(new StringColumn(new NullColumn(metaData.getColumnName(i)),
                				resultSet.getString(metaData.getColumnName(i))));
                	}
                	
                	else if (metaData.getColumnType(i) == 4 || metaData.getColumnType(i) == 5 || metaData.getColumnType(i) == -5)
                	{
                		columns.add(new IntegerColumn(new NullColumn(metaData.getColumnName(i)),
                				Integer.valueOf(resultSet.getInt(i))));
                	}
                	
                	else if (metaData.getColumnType(i) == 3 || metaData.getColumnType(i) == 2)
                	{
                		columns.add(new DecimalColumn(new NullColumn(metaData.getColumnName(i)),
                				BigDecimal.valueOf(resultSet.getDouble(i))));
                	}
                	
                	else if (metaData.getColumnType(i) == 8)
                	{
                		columns.add(new DoubleColumn(new NullColumn(metaData.getColumnName(i)),
                				Double.valueOf(resultSet.getDouble(i))));
                	}
                	
                	else
                	{
                		columns.add(new StringColumn(new NullColumn(metaData.getColumnName(i)),
                				String.valueOf(resultSet.getObject(i))));
                	}
                	
                	String[] set = new String[2];
                	
                    set[0] = metaData.getColumnName(i);
                    set[1] = resultSet.getString(metaData.getColumnName(i));
                    
                    properties.add(set);
                }
                
                Entity entity = new Entity(columns);
                
                if (record != null)
                {
                	if (resultSet.getString(pkColumn).equals(record))
                	{
                		activeEntity = entity;
                	}
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
            DataEngine.Disconnect();
        }
    }
    
    public void printEntityList()
    {
//    	for (int i = 0; i < columnNames.size(); i++)
//    		System.out.printf("%12s", columnNames.get(i));
    	
    	//System.out.println();
    	
    	for (int i = 0; i < entityList.size(); i++)
    		entityList.get(i).printString();
    }
    
    private int checkTableName(String initialTableName)
    {
    	if (getTableNames().contains(initialTableName))
    		return 0;
    	
    	else
    		return 1;
    }
    
    private Set<String> getTableNames()
    {
        Set<String> tableNames = new HashSet<String>();
        Connection connection = engine.getConnection();
        
        try (
                Statement s = connection.createStatement();
                ResultSet rs = s.executeQuery("select s.schemaname || '.' || t.tablename from sys.systables t, sys.sysschemas s where t.schemaid = s.schemaid and t.tabletype = 'T' order by s.schemaname, t.tablename");
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            while (rs.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    tableNames.add(rs.getString(i).substring(4));
                }
            }
            
            DataEngine.Disconnect();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            DataEngine.Disconnect();
        }
        
        return tableNames;
    }
}