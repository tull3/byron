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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.DDLTransaction;
import digital.tull.project.byron.transaction.InsertTransaction;
import digital.tull.project.byron.transaction.Entity;
import digital.tull.project.byron.transaction.Table;
import digital.tull.project.byron.transaction.TransactionType;



public class ConnectionManager
{
    private static Connection conn;
    //private Session session;
    private Table transaction;
    private static Properties defaultProps;
    private DataEngine entityEngine;
    
    public ConnectionManager()
    {
    	
    }
    
    public Connection getConnection()
    {
    	Connection connection = null;
    	
    	try
        {
            //loadDefaultProperties(session.getUser());

            //defaultProperties.put("jdbc.drivers", "org.apache.derby.jdbc.EmbeddedDriver");
            defaultProps.setProperty("user", "APP");
            defaultProps.setProperty("password", "APP");
            //final String URL = "jdbc:derby:/home/will/.netbeans-derby/rome;";
            
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            
            connection = DriverManager.getConnection("jdbc:derby:" + defaultProps.getProperty("derby.system.home")
                    + defaultProps.getProperty("derby.db.name"), defaultProps);
            
            //conn.setAutoCommit(false);
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
            
        }
    	
    	return connection;
    }
    
//    public static ConnectionManager Start(Session session)
//    {
//        ConnectionManager em = null;
//        
//        if (!session.isValidSession())
//        {
//            return em;
//        }
//        
//        em = new ConnectionManager(session);
//        
//        try
//        {
//            loadDefaultProperties(session.getUser());
//
//            //defaultProperties.put("jdbc.drivers", "org.apache.derby.jdbc.EmbeddedDriver");
//            defaultProps.setProperty("user", "APP");
//            defaultProps.setProperty("password", "APP");
//            //final String URL = "jdbc:derby:/home/will/.netbeans-derby/rome;";
//            
//            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//            
//            conn = DriverManager.getConnection("jdbc:derby:" + defaultProps.getProperty("derby.system.home")
//                    + defaultProps.getProperty("derby.db.name"), defaultProps);
//            
//            //conn.setAutoCommit(false);
//        }
//        
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//            
//        }
//        
//        
//        
//        return em;
//    }
    
    public static void Stop()
    {
        try
        {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            
        }
        
        catch (SQLException se)
        {
            if (( (se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState()) )))
            {
                // we got the expected exception
                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            }
            
            else
            {
                //if the error code or SQLState is different, we have
                //an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");
                System.out.println(se.toString());
            }
        }
        
        finally
        {
//            try
//            {
//                conn.close();
//            }
//            
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
        }
    }
    
    private static void loadDefaultProperties(User user)
    {
        defaultProps = new Properties();
        
        try (FileReader in = new FileReader(user.getID() + ".properties"))
        {
            defaultProps.load(in);
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
    
    public static Connection GetConnection()
    {
        return conn;
    }
    
//    public DerbyEngine getEntityEngine()
//    {
//        return entityEngine;
//    }
    
//    public SchemaEngine getSchemaEngine()
//    {
//        return schemaEngine;
//    }
    
//    public void commitTransactions()
//    {
//        ExecutorService executor = Executors.newCachedThreadPool();
//        
//        List<Object> transactions = TransactionQueue.Get();
//        
//        for (int i = 0; i < transactions.size(); i++)
//        {
//            
//            
//            if (transactions.get(i) instanceof Schema)
//            {
//                Schema transaction = (Schema) transactions.get(i);
//                
//                schemaEngine = new SchemaEngine(transaction);
//                
//                executor.execute(schemaEngine);
//            }
//            
//            else if (transactions.get(i) instanceof Entity)
//            {
//                Entity transaction = (Entity) transactions.get(i);
//                
//                entityEngine = new EntityEngine(transaction);
//                
//                executor.execute(entityEngine);
//            }
//        }
//    }
    
//    public void doWork(Transaction trans)
//    {
//        List<Object> transactionList = new ArrayList<>();
//        
//        transactionList = trans.build();
//        
//        if (trans instanceof InsertTransaction)
//        {
//            TransactionType transType = (TransactionType) transactionList.get(0);
//            Entity entity = (Entity) transactionList.get(1);
//            
//            switch (transType)
//            {
//                case CREATE_ENTITY:
//                    entityEngine.createEntity(entity);
//                    break;
//                    
//                case MODIFY_ENTITY:
//                    entityEngine.modifyEntity(entity);
//                    break;
//                    
//                case DELETE_ENTITY:
//                    entityEngine.deleteEntity(entity);
//                    break;
//                    
//                default:
//                    System.out.println("Nothing happened.");
//                    break;
//            }
//        }
//        
//        else if (trans instanceof DDLTransaction)
//        {
//            
//        }
//    }
}
