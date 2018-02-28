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
    //private Column column;
    private final String tableName;
    private Entity activeEntity;
    private List<Entity> entityList;
    private List<String> pkValueList;
    private int[] columnTypeKey;
    private final TableStatement transaction;
    private final String pkColumn;
    private final List<String> columnNames;
    
    public Table(final String tableName, final String pkColumn, List<String> columnNames)
    {
    	this.columnNames = columnNames;
    	this.pkColumn = pkColumn;
        this.tableName = tableName;
        transaction = new BasicTransaction(tableName);
    }

    public String getTableName()
    {
        return tableName;
    }
    
    public List<Entity> getEntityList()
    {
    	return entityList;
    }
    
    public List<String> getPKValueList()
    {
    	return pkValueList;
    }
    
    public Entity getActiveEntity()
    {
    	return activeEntity;
    }
    
    public TableStatement withTransaction(final TransactionType type, final String activeRecord)
    {
    	if (type == TransactionType.MODIFY_ENTITY)
    		return new UpdateTransaction(transaction, pkColumn, "'" + activeRecord + "'");
    	
    	else if (type == TransactionType.CREATE_ENTITY)
    		return new InsertTransaction(transaction, columnNames);
    	
    	else if (type == TransactionType.DELETE_ENTITY)
    		return new DeleteTransaction(transaction, pkColumn, "'" + activeRecord + "'");
    	
    	return transaction;
    }    
    
    public void consumeStatement(final TableStatement statement, final DataEngine engine)
    {
    	String sql = statement.getStatement();
    	
    	//System.out.println(sql);
    	
    	try (Statement s = engine.getConnection()
    					.createStatement();
    			)
    	{
    		s.execute(sql);
    		
    		DataEngine.Disconnect();
    	}
    	
    	catch (SQLException e)
    	{
    		e.printStackTrace();;
    	}
    }
    
    public Table loadDefaultModules()
    {
    	return this;
    }
    
    public int loadModule(final String moduleName)
    {
    	//TODO: write code for module loading.
    	//modules will be used to switch between file and database sources, etc.
    	
    	
    	return 0;
    }
}
