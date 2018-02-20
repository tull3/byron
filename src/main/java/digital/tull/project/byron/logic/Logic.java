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

import digital.tull.project.byron.builder.Entity;
import digital.tull.project.byron.builder.EntityFactory;
import digital.tull.project.byron.engine.DerbyEngine;
import digital.tull.project.byron.engine.EngineManager;
import digital.tull.project.byron.session.Authenticator;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.DMLTransaction;
import digital.tull.project.byron.transaction.Transaction;
import digital.tull.project.byron.transaction.TransactionData;
import digital.tull.project.byron.transaction.TransactionType;



public class Logic
{
    private Session session;
    private Scanner input = new Scanner(System.in);
    private EngineManager engine;
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
    
    public void run(String[] args)
    {
        session();
        
        //engine.connect();
        
        looper();
        
        EngineManager.Stop();
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
        
        //transaction = new TransactionData(session);
        engine = EngineManager.Start(session);
        
    }
    
    private void engine(int option)
    {
//        if (option == 4)
//            return;
        
        TransactionType[] transactions = TransactionType.values();
        TransactionType transactionType = transactions[option];
        //entityList = engine.getEntityList(tableName);
        
        //DerbyEngine engine = new DerbyEngine(session);
        
        //engine.connect();
        
//        int option = Menus.TransactionMenu();
//        
//        while (option < 4 && option >= 0)
//        {
            //Entity entity = new Entity();
            
            //transaction.setTransactionType(transactions[option]);
            
            System.out.println("Which table would you like to work with?");
        
            String[] tableNames = engine.getTableNames();
        
            for (int i = 0; i < tableNames.length; i++)
                System.out.println(i + ":  " + tableNames[i]);
        
            Entity entity;
            
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
                
            entity = new Entity().setTableName(tableNames[tableOption]).setPKColumn(engine.getPrimaryKeyColumn(tableNames[tableOption]));
        
            entityList = engine.getEntityList(tableNames[tableOption]);
            
            Entity entityScaffold = entityList.get(0);
            
            if (transactionType != null)
            switch (transactionType)
            {
                    
                case DELETE_ENTITY:
                case MODIFY_ENTITY:
                    
                    System.out.println("Which record would you like to work with?");
                    
                    for (int i = 0; i < entityList.size(); i++)
                    {
                        System.out.println(i + ":  " + entityList.get(i).getPKValue());
                    }
                    
                    int record = input.nextInt();
                    //String[] set = new String[2];
                    
                    entity.setProperties(entityList.get(record).getProperties());
                    //EntityBuilder.PrintEntity(entity);
                    
                    //set = entity.getProperties().toArray(new String[2]);
                    
                    //transaction.setWorkingRecord(entity.getPrimaryKey());
                    
//                    if (transactionType.equals(TransactionType.MODIFY_ENTITY))
//                        entity = EntityFactory.ModifyEntity(entity);
                    
                case CREATE_ENTITY:
                    
                    Entity newEntity = new EntityFactory(entity, transactionType, entityScaffold).produceEntity();
                    
                    if (newEntity.isCanceled())
                        break;
                    
                    engine.doWork(new DMLTransaction(transactionType, newEntity));
                    
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
                    
//                    session.setTransactionType(null);
//                    session.setWorkingRecord(null);
//                    session.setWorkingTable(null);
//                    session.setPrimaryKeyColumn(null);
                    
                    
                    break;
            }
        
//            if (option == 4)
//                System.exit(1);
            
            //option = Menus.EntityMenu();
        //}
        
        //transaction.log();
        
        //entity = null;
        
        //looper();
        
        //engine.disconnect();
    }
    
    private void looper()
    {
        int option = Menus.TransactionMenu();
        
        while (option < 4 && option >= 0)
        {
            engine(option);
            
            option = Menus.TransactionMenu();
        }
    }
}
