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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.cli.Options;
import org.apache.derby.jdbc.EmbeddedDataSource;

import digital.tull.project.byron.engine.DataEngine;
import digital.tull.project.byron.engine.ConnectionManager;
import digital.tull.project.byron.session.Authenticator;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.InsertTransaction;
import digital.tull.project.byron.transaction.Entity;
import digital.tull.project.byron.transaction.EntityFactory;
import digital.tull.project.byron.transaction.Table;
import digital.tull.project.byron.transaction.TransactionType;



public class Logic
{
    private Session session;
    private Scanner input = new Scanner(System.in);
    private ConnectionManager engine;
    //private TransactionData transaction;
    private List<Entity> entityList = new ArrayList<>();
    Options options = new Options();
    
    public Logic()
    {
        options.addOption("cr", "create-record", false, "Create new record.");
        options.addOption("mr", "modify-record", false, "Modify existing record.");
        options.addOption("dr", "delete-record", false, "Delete existing record.");
        options.addOption("lr", "list-records", false, "List existing records.");
    }
    
    public void run()
    {
        session();
        
        session.setTableNames(new ConnectionManager().getConnection(session));
        
        int option = 3;
        
        while (option < 4 && option >= 0)
        {
        	option = Menus.TransactionMenu();
        	engine(option);
        };
        
        
        
        
        
        //ConnectionManager.Stop();
    }
    
    public void runWithArgs(String[] args)
    {
    	session();
    	
    	session.setTableNames(new ConnectionManager().getConnection(session));
    	
    	if (args[0].equals("create"))
    		engine(0);
    	
    	else if (args[0].equals("modify"))
    		engine(1);
    	
    	else if (args[0].equals("delete"))
    		engine(3);
    }
    
    private void session()
    {
        System.out.println("Please identify yourself.");
        
        do
        { 
            User user = EntityFactory.BuildUser();
        
            System.out.println("Authenticating...");
            session =  Session.Login(user);
        }
        
        while (!session.isValidSession());
        
        System.out.println("Loading...");
        
        
    }
    
    private void engine(int option)
    {
    	if (option == 4)
    		return;
    	
        TransactionType[] transactions = TransactionType.values();
        TransactionType transactionType = transactions[option];
            
        System.out.println("Which table would you like to work with?");
    
        String[] tableNames = session.getTableNames();
    
        for (int i = 0; i < tableNames.length; i++)
            System.out.println(i + ":  " + tableNames[i].substring(4));
    
        //Entity entity;
        
        int tableOption = -1;
        
        while (tableOption == -1)
        {
            try
            {
                tableOption = input.nextInt();
            }
            
            catch (NullPointerException e)
            {
                System.out.println("Please try again.");
            }
            
            
        }
        
        Table table = new Table(tableNames[tableOption], session);
        
        table.populateData();
        
        Entity entity = new Entity();
    
        entityList = table.getEntityList();
        
        if (transactionType != null)
        switch (transactionType)
        {
                
            case DELETE_ENTITY:
            	
            	entity.setDeleteFlag(true);
            	
            case MODIFY_ENTITY:
                
                System.out.println("Which record would you like to work with?");
                
                for (int i = 0; i < entityList.size(); i++)
                {
                    System.out.println(i + ":  " + entityList.get(i).getPKValue(table.getPKColumn()));
                }
                
                int record = -1;
                
                try
                {
                	record = input.nextInt();
                }
                
                catch (NullPointerException e)
                {
                	e.printStackTrace();
                }
                
                entity.setProperties(entityList.get(record).getProperties());
                
                table.setActiveRecord(entityList.get(record).getPKValue(table.getPKColumn()));
                
            case CREATE_ENTITY:
                
                entity = table.buildEntity(entity);
                
                if (entity.isCanceled())
                    break;
                
                table.mergeEntity(entity);
                
                break;
                
            case VIEW_ENTITY:
                    
//                    for (int i = 0; i < entityList.size(); i++)
//                    {
//                        System.out.println(i + ":  " + entityList.get(i));
//                    }
                    
                for (Entity e : entityList)
                    EntityFactory.PrintEntity(e);
                
                break;
                
            default:
                    
                break;
        }
    }
}
