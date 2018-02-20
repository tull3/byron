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

import java.util.InputMismatchException;
import java.util.Scanner;



public class Menus
{
    public static void MainMenu()
    {
        
    }
    
    public static int SchemaMenu()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Alter an existing table or create a new one?");
        System.out.println("1. Existing table.");
        System.out.println("2. New table.");
        int option = in.nextInt();
        
        System.out.println("Please enter an option.");
        System.out.println("1. Create table.");
        System.out.println("2. Edit table.");
        System.out.println("3. Delete record.");
        System.out.println("4. View record.");
        System.out.println("0. Exit.");
        option = in.nextInt();
        
        return option;
    }
    
    public static int EntityMenu()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Please enter an option.");
        System.out.println("0. Create record.");
        System.out.println("1. Modify record.");
        System.out.println("2. Delete record.");
        System.out.println("3. View record.");
        System.out.println("4. Exit.");
        
        int option = -1;
        
        while (option < 0 || option > 4)
        {
            try
            {
                option = in.nextInt();
            }
        
            catch (InputMismatchException e)
            {
                System.out.println(e.toString());
                option = -1;
                System.out.println("Please try again.");
            }
        }
        
        return option;
    }
    
    public static int TransactionMenu()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Please enter an option.");
        System.out.println("0. Create record.");
        System.out.println("1. Modify record.");
        System.out.println("2. Delete record.");
        System.out.println("3. View record.");
        System.out.println("4. Exit.");
        
        int option = -1;
        
        while (option < 0 || option > 4)
        {
            try
            {
                option = in.nextInt();
            }
        
            catch (InputMismatchException e)
            {
                System.out.println(e.toString());
                option = -1;
                System.out.println("Please try again.");
            }
        }
        
        return option;
    }
    
    public static int ConfirmDelete(String record)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Confirm deletion of record " + record + ".");
                    
        System.out.println("1.  Yes.");
        System.out.println("2.  No.");
        
        int option = -1;
        
        while (option < 1 || option > 2)
        {
            try
            {
                option = in.nextInt();
            }
        
            catch (InputMismatchException e)
            {
                System.out.println(e.toString());
                option = -1;
                System.out.println("Please try again.");
            }
        }
        
        return option;
    }
    
    public static int ConfirmChanges()
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Confirm changes?");
        System.out.println("1.  Yes.");
        System.out.println("2.  No.");
        System.out.println("3.  Cancel.");
        
        int option = -1;
        
        while (option < 1 || option > 3)
        {
            try
            {
                option = in.nextInt();
            }
        
            catch (InputMismatchException e)
            {
                System.out.println(e.toString());
                option = -1;
                System.out.println("Please try again.");
            }
        }
        
        return option;
    }
}
