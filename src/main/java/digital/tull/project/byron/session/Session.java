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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import digital.tull.project.byron.builder.Entity;
import digital.tull.project.byron.transaction.TransactionType;



public class Session
{
    //eventually, session objects will be used to record transaction data in log files
    //right now they are just used to conduct transactions
    private User user;
    private boolean validSession;
    
    private Session(User user)
    {
        if (user != null)
            validSession = true;
        
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    //no setter for user or userGroup
    //can only be set with authenticated user object in constructor

    //validSession can only be set to true in the same way

    public boolean isValidSession()
    {
        return validSession;
    }
    
    public static Session Login(User user)
    {
        Session session = null;
        
        try (Scanner input = new Scanner(Paths.get("users.txt")))
        {
            String text = null;
            
            String[] items = new String[3];
            
            search:
            
            while (input.hasNextLine())
            {
                text = input.nextLine();
                
                items = text.split(";");
                
                if (items[0].equals(user.getID()) && items[1].equals(user.getPasswd()))
                {
                    user.setGroupID(items[2]);
                    session = new Session(user);
                    break search;
                }
            }
            
            if (session.isValidSession())
                System.out.println("User " + user.getID() + " is authenticated with group " + user.getGroupID() + ".");
            
            
            
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        
        catch (NullPointerException e)
        {
            System.out.println("User not authenticated.  Exiting.");
            System.exit(1);
        }
        
        return session;
    }
    
//    public List<Entity> getEntityList()
//    {
//        
//    }
    
    public void updateEntityList()
    {
        
    }
    
    public void loadSessionData()
    {
        
    }
}
