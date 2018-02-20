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

package digital.tull.project.byron.engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import digital.tull.project.byron.builder.Entity;
import digital.tull.project.byron.logic.Menus;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.TransactionData;



public class DerbyEngine
{
    //private Connection conn;
    private Session session;
    //private TransactionData transaction;
    private Properties defaultProperties;
    
    protected DerbyEngine()
    {
        
        //this.transaction = transaction;
    }
    
    
    
//    public void connect()
//    {
//        
//        
//        try
//        {
//            loadDefaultProperties(session.getUser());
//
//            //defaultProperties.put("jdbc.drivers", "org.apache.derby.jdbc.EmbeddedDriver");
//            defaultProperties.setProperty("user", "APP");
//            defaultProperties.setProperty("password", "APP");
//            //final String URL = "jdbc:derby:/home/will/.netbeans-derby/rome;";
//            
//            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//            
//            conn = DriverManager.getConnection("jdbc:derby:" + defaultProperties.getProperty("derby.system.home") + defaultProperties.getProperty("derby.db.name"), defaultProperties);
//            
//            conn.setAutoCommit(false);
//        }
//        
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        
//        
//    }
    
//    public void disconnect()
//    {
//        try
//        {
//            DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            
//        }
//        
//        catch (SQLException se)
//        {
//            if (( (se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState()) )))
//            {
//                // we got the expected exception
//                System.out.println("Derby shut down normally");
//                // Note that for single database shutdown, the expected
//                // SQL state is "08006", and the error code is 45000.
//            }
//            
//            else
//            {
//                // if the error code or SQLState is different, we have
//                // an unexpected exception (shutdown failed)
//                System.err.println("Derby did not shut down normally");
//                System.out.println(se.toString());
//            }
//        }
//        
//        finally
//        {
//            try
//            {
//                conn.close();
//            }
//            
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
    
    private String buildQuery(String tableName)
    {
        final String query = "SELECT * FROM " + tableName;
        
        return query;
    }
    
    public String getPrimaryKeyColumn(String tableName)
    {
        String denied = null;
        
//        if (!session.isValidSession())
//        {
//            System.out.println("Denied.");
//            return denied;
//        }
        
        String catalog = null;
        String schema = "APP";
        String table = tableName.substring(4);
        
        String pkColumn = null;
        
        try
        {
            DatabaseMetaData dbData = EngineManager.GetConnection().getMetaData();
            
            
            
            ResultSet rs = dbData.getPrimaryKeys(catalog, schema, table);
            
            while (rs.next())
            {
                pkColumn = rs.getString(4);
            }
            
            rs.close();
            
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        
        return pkColumn;
    }
    
    public String[] getTableNames()
    {
        List<String> tableNames = new ArrayList<String>();
        String[] denied = null;
        
//        if (!session.isValidSession())
//        {
//            System.out.println("Denied.");
//            return denied;
//        }
        
        //connect();
        
        try (
                Statement s = EngineManager.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet rs = s.executeQuery("select s.schemaname || '.' || t.tablename from sys.systables t, sys.sysschemas s where t.schemaid = s.schemaid and t.tabletype = 'T' order by s.schemaname, t.tablename");
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            while (rs.next())
            {
                
                
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    tableNames.add(rs.getString(i));
                    //System.out.println(i + ".  " + rs.getString(i));
                }
                
                
            }
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        //disconnect();
        
        return tableNames.toArray(new String[tableNames.size()]);
    }
    
    public List<Entity> getEntityList(String tableName)
    {
        List<Entity> entityList = new ArrayList<>();
        List<Entity> deniedList = null;
        
//        if (!session.isValidSession())
//        {
//            System.out.println("Denied.");
//            return deniedList;
//        }
        
        //connect();
        
        try (
                Statement s = EngineManager.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet rs = s.executeQuery(buildQuery(tableName));
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            while (rs.next())
            {
                Entity entity = new Entity();
                
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    entity.setProperty(metaData.getColumnName(i), rs.getString(metaData.getColumnName(i)));
                }
                
                entity.setTableName(tableName);
                
                entity.setPKColumn(getPrimaryKeyColumn(tableName));
                
                entityList.add(entity);
            }
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            EngineManager.Stop();
            System.exit(1);
        }
        
        //disconnect();
        
        return entityList;
    }
    
//    public void doWork(Entity entity)
//    {
//        
//        try (
//                Statement s = EngineManager.GetConnection().createStatement(
//                        ResultSet.TYPE_SCROLL_INSENSITIVE,
//                        ResultSet.CONCUR_UPDATABLE
//                        );
//                ResultSet rs = s.executeQuery(buildQuery());
//                )
//        {
//            ResultSetMetaData metaData = rs.getMetaData();
//            int numberOfColumns = metaData.getColumnCount();
//            
//            if (transaction.getTransactionType() != null)
//            switch (transaction.getTransactionType())
//            {
//                case CREATE_ENTITY:
//                    
//                    rs.moveToInsertRow();
//                    
//                    for (int i = 1; i <= numberOfColumns; i++)
//                    {
//                        rs.updateString(metaData.getColumnName(i),
//                                entity.getProperty(metaData.getColumnName(i)));
//                    }
//                    
//                    rs.insertRow();
//                    
//                    
//                    break;
//                    
//                case MODIFY_ENTITY:
//                    
//                    rs.beforeFirst();
//                    
//                    while (rs.next())
//                    {
//                        if (rs.getString(1).equals(transaction.getWorkingRecord()))
//                            break;
//                    }
//                    
//                    for (int i = 1; i <= numberOfColumns; i++)
//                    {
//                        rs.updateString(metaData.getColumnName(i),
//                                entity.getProperty(metaData.getColumnName(i)));
//                    }
//                    
//                    rs.updateRow();
//                    
//                    
//                    break;
//                    
//                case DELETE_ENTITY:
//                    
//                    rs.beforeFirst();
//                    
//                    
//                    
//                    while (rs.next())
//                    {
//                        if (rs.getString(1).equals(transaction.getWorkingRecord()))
//                            break;
//                    }
//                    
//                    String record = rs.getString(1);
//                    
//                    int option1 = Menus.ConfirmDelete(record);
//                    
//                    if (option1 == 1)
//                    {
//                        rs.deleteRow();
//                    }
//                    
//                    else
//                    {
//                        System.out.println("Canceling.");
//                        break;
//                    }
//                    
//                    
//                    break;
//                    
//                default:
//                    break;
//            }
//        }
//        
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//            EngineManager.Stop();
//            System.exit(1);
//        }
//        
//    }
    
    protected void createEntity(Entity entity)
    {
        try (
                Statement s = EngineManager.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet rs = s.executeQuery(buildQuery(entity.getTableName()));
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            rs.moveToInsertRow();
                    
            for (int i = 1; i <= numberOfColumns; i++)
            {
                rs.updateString(metaData.getColumnName(i),
                        entity.getProperty(metaData.getColumnName(i)));
            }
                    
            rs.insertRow();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            EngineManager.Stop();
            System.exit(1);
        }
    }
    
    protected void modifyEntity(Entity entity)
    {
        try (
                Statement s = EngineManager.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet rs = s.executeQuery(buildQuery(entity.getTableName()));
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            rs.beforeFirst();
                    
            while (rs.next())
            {
                if (rs.getString(1).equals(entity.getPKValue()))
                    break;
            }
            
            for (int i = 1; i <= numberOfColumns; i++)
            {
                rs.updateString(metaData.getColumnName(i),
                        entity.getProperty(metaData.getColumnName(i)));
            }
                    
            rs.updateRow();
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            EngineManager.Stop();
            System.exit(1);
        }
    }
    
    protected void deleteEntity(Entity entity)
    {
        try (
                Statement s = EngineManager.GetConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                        );
                ResultSet rs = s.executeQuery(buildQuery(entity.getTableName()));
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            
            rs.beforeFirst();
                    
            while (rs.next())
            {
                if (rs.getString(1).equals(entity.getPKValue()))
                    break;
            }
                    
            String record = rs.getString(1);
                   
            int option1 = Menus.ConfirmDelete(record);
                    
            if (option1 == 1)
            {
                rs.deleteRow();
            }
                    
            else
            {
                System.out.println("Canceling.");
                return;
            }
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            EngineManager.Stop();
            System.exit(1);
        }
    }
}
