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

import digital.tull.project.byron.logic.Menus;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.Entity;
import digital.tull.project.byron.transaction.Table;



public class DataEngine
{
    private static Connection conn;
    
    protected DataEngine()
    {
        
        //this.transaction = transaction;
    }
    
    public static Connection GetConnection()
    {
    	return conn;
    }
    
    public static void Connect(Properties properties)
    {    	
        try
        {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            
            conn = DriverManager.getConnection("jdbc:derby:" + properties.getProperty("derby.system.home") + properties.getProperty("derby.db.name"), properties);
            
            //conn.setAutoCommit(false);
        }
        
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void Disconnect()
    {
        try
        {
        	conn.close();
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
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");
                System.out.println(se.toString());
            }
        }
        
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
    }
}
