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
import digital.tull.project.byron.transaction.Table;
import digital.tull.project.byron.transaction.TransactionType;



public class Session
{
    //eventually, session objects will be used to record transaction data in log files
    //right now they are just used to conduct transactions
    private User user;
    private boolean validSession;
    private String[] tableNamesArray;
    private Properties properties;
    private Table table;
    
    private Session()
    {
    	
    }
    
    public void startSession()
    {
    	loadDefaultProperties();
    	DataEngine.Connect(properties);
    	//setTableData();
    }
    
    public void loadSessionTable(String tableName)
    {
    	table = new Table(tableName);
    }

    public User getUser()
    {
        return user;
    }

    public boolean isValidSession()
    {
        return validSession;
    }
    
//    public Session Login(User user)
//    {
//        Session session = null;
//        
//        try (Scanner input = new Scanner(Paths.get("users.txt")))
//        {
//            String text = null;
//            
//            String[] items = new String[3];
//            
//            search:
//            
//            while (input.hasNextLine())
//            {
//                text = input.nextLine();
//                
//                items = text.split(";");
//                
//                if (items[0].equals(user.getID()) && items[1].equals(user.getPasswd()))
//                {
//                    user.setGroupID(items[2]);
//                    session = new Session(user);
//                    break search;
//                }
//            }
//            
//            if (session.isValidSession())
//                System.out.println("User " + user.getID() + " is authenticated with group " + user.getGroupID() + ".");
//            
//            
//            
//        }
//        catch (IOException e)
//        {
//            System.err.println(e.toString());
//            System.exit(1);
//        }
//        
//        catch (NullPointerException e)
//        {
//            System.out.println("User not authenticated.  Exiting.");
//            System.exit(1);
//        }
//        
//        loadDefaultProperties();
//        
//        return session;
//    }
    
    private void loadDefaultProperties()
    {
        properties = new Properties();
        
        try (FileReader in = new FileReader(user.getID() + ".properties"))
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
    
    private void setTableData(String tableName)
    {
    	String catalog = null;
        String schema = "APP";
        String table = tableName;
        String column = null;
        List<String> tableNames = new ArrayList<String>();
        
        try (
                Statement s = DataEngine.GetConnection().createStatement(
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
                }
            }
            
            
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            DataEngine.Disconnect();
        }
        
        tableNamesArray = tableNames.toArray(new String[tableNames.size()]);
    }
    
    public String[] getTableNames()
    {
    	return tableNamesArray;
    }
}