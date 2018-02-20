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

package digital.tull.project.byron.builder;

import java.util.List;
import java.util.Scanner;

import digital.tull.project.byron.builder.Entity;
import digital.tull.project.byron.logic.Menus;
import digital.tull.project.byron.session.Session;
import digital.tull.project.byron.session.User;
import digital.tull.project.byron.transaction.TransactionType;



public class EntityFactory
{
    private final Entity entity;
    private final TransactionType transactionType;
    private Entity entityScaffold;
    
    public EntityFactory(Entity entity, TransactionType transactionType, Entity entityScaffold)
    {
        this.entity = entity;
        this.transactionType = transactionType;
        this.entityScaffold = entityScaffold;
    }
    
    public Entity produceEntity()
    {
        if (transactionType.equals(TransactionType.DELETE_ENTITY))
            return entity;

        return buildEntity();
        
        //return newEntity;
    }
    
//    private void buildEntity()
//    {
//        newEntity = new Entity(entity.getPKColumn(), entity.getTableName());
//        
//        Scanner input = new Scanner(System.in);
//        
//        int option = 1;
//        
//        while (option == 1)
//        {
//            Entity entityScaffold = entity;
//        
//            for (int i = 0; i < entityScaffold.getSize(); i++)
//            {
//                String[] set = entityScaffold.getProperty(i);
//            
//                System.out.println(set[0]);
//                newEntity.setProperty(set[0], input.nextLine());
//            }
//        }
//        entity = null;
//    }
    
    private Entity buildEntity()
    {
        Scanner input = new Scanner(System.in);
        
        Entity newEntity = new Entity(entity.getPKColumn(), entity.getTableName());
        
        int option = 0;
        
        while (option == 0)
        {
            String[] set = new String[2];
            
            newEntity.reset();
            
            if (transactionType.equals(TransactionType.CREATE_ENTITY))
            {
        
                for (int i = 0; i < entityScaffold.getSize(); i++)
                {
                    set = entityScaffold.getProperty(i);
            
                    System.out.println(set[0]);
                    newEntity.setProperty(set[0], input.nextLine());
                }
                
                PrintEntity(newEntity);
            }
            
            else if (transactionType.equals(TransactionType.MODIFY_ENTITY))
            { 
                
                for (int i = 0; i < entity.getSize(); i++)
                {
                    set = entity.getProperty(i);
            
                    System.out.println("Current " + set[0] + ":  " + set[1]);
            
                    System.out.println("New " + set[0] + ":  ");
                    newEntity.setProperty(set[0], input.nextLine());
                }
            
                System.out.println("Modified entity:  ");
            
//            for (int i = 0; i < newEntity.getSize(); i++)
//            {
//                set = newEntity.getProperty(i);
//                
//                System.out.println(set[0] + ":  " + set[1]);
//            }

                PrintEntity(newEntity);
            }
            
            int i = Menus.ConfirmChanges();
            
            if (i == 3)
                option = 3;
                
            if (i == 1)
                option = 1;
        }
        
        if (option == 3)
            newEntity.setCancelFlag(true);
        
        return newEntity;
    }
    
    public static User BuildUser()
    {
        User user = new User();
        Scanner input = new Scanner(System.in);
        
        System.out.println("Username:");
        user.setID(input.nextLine());
    
        System.out.println("Password:");
        user.setPasswd(input.nextLine());
        
        return user;
    }
    
    public static Entity BuildEntity(List<Entity> entityList, String pkColumn, String tableName)
    {
        Entity entity = new Entity(pkColumn, tableName);
        
        Scanner input = new Scanner(System.in);
        
        Entity entityScaffold = entityList.get(0);
        
        for (int i = 0; i < entityScaffold.getSize(); i++)
        {
            String[] set = entityScaffold.getProperty(i);
            
            System.out.println(set[0]);
            entity.setProperty(set[0], input.nextLine());
        }
        
        return entity;
    }
    
    public static Entity ModifyEntity(Entity entity)
    {
        Scanner input = new Scanner(System.in);
        System.out.println(entity.getPKColumn() + entity.getTableName());
        Entity newEntity = new Entity(entity.getPKColumn(), entity.getTableName());
        
        String[] set = new String[2];
        
        int option = 1;
        
        while (option > 0)
        {
            for (int i = 0; i < entity.getSize(); i++)
            {
                set = entity.getProperty(i);
            
                System.out.println("Current " + set[0] + ":  " + set[1]);
            
                System.out.println("New " + set[0] + ":  ");
                newEntity.setProperty(set[0], input.nextLine());
            }
            
            System.out.println("Modified entity:  ");
            
            for (int i = 0; i < newEntity.getSize(); i++)
            {
                set = newEntity.getProperty(i);
                
                System.out.println(set[0] + ":  " + set[1]);
            }
            
            System.out.println("Confirm changes?");
            System.out.println("1.  Yes.");
            System.out.println("2.  No.");
            System.out.println("3.  Cancel.");
            
            int i = input.nextInt();
            
            if (i == 1)
                option = 0;
            
            if (i == 3)
                break;
        }
        
        if (option == 0)
            return newEntity;
        
        else
            return entity;
    }
    
    public static void PrintEntity(Entity entity)
    {
        System.out.println(entity.toString());
    }
}
