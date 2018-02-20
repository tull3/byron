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

package digital.tull.project.byron;
 
import digital.tull.project.byron.logic.Logic;


 
public class App 
{
    public static void main (String[] args)
    {
        System.out.println("--Welcome to Athens--");
        
        for (int counter = 0; counter <= 11; counter++)
        {
            int number = fibonacci(counter);
            
            for (int i = 1; i <= number; i++)
            {
                System.out.print("*");
            }
            System.out.println();
            
        }
        
        System.out.println();
        
        System.out.println("--The Tull Project--");
        
        System.out.println();
        
        new Logic().run(args);
        
        
//    while (option != 0)
//    {
//        if (option == 1)
//        {
//            Article article = new Article();
//            Scanner input = new Scanner(System.in);
//            
//            System.out.println("Title:  ");
//            article.setTitle(input.nextLine());
//
//            System.out.println("Text One:  ");
//            article.setTextOne(input.nextLine());
//
//            System.out.println("Author Name:  ");
//            article.setAuthorName(input.nextLine());
//            
//            persist.create(article);
//            
//            persist.setNull();
//        }
//
//        else if (option == 3)
//          {
//              Article article = new Article();
//              Scanner input = new Scanner(System.in);
//  
//              System.out.println("Title:  ");
//
//              article.setTitle(input.nextLine());
//              persist.deleteArticle(article);
//              
//              persist.setNull();
//          }
//
//        else if (option == 2)
//        {
//            Article article = new Article();
//            
//            Scanner input = new Scanner(System.in);
//
//            System.out.println("Title:  ");
//            article.setTitle(input.nextLine());
//            
//            System.out.println("New Text One:  ");
//            article.setTextOne(input.nextLine());
//            
//            System.out.println("Caption:  ");
//            article.setCaption(input.nextLine());
//            
//            persist.updateArticle(article);
//            
//            persist.setNull();
//        }
//        
//        else if (option == 4)
//        {
//        try
//        {
//            String title = null;
//            
//            Scanner input = new Scanner(System.in);
//            
//            System.out.println("Title:  ");
//            System.out.println("Type 'ALL' for all entries.");
//            title = input.nextLine();
//            
//            persist.setKey(title);
//            persist.setRS();
//            ResultSet rs = persist.getRS();
//            Persistence.viewRS(rs);
//            
//            ResultSetMetaData metaData = rs.getMetaData();
//            int numberOfColumns = metaData.getColumnCount();
//            
//            while (rs.next())
//            {
//                for (int i = 1; i <= numberOfColumns; i++)
//                    System.out.println(metaData.getColumnName(i) + ":  " + rs.getString(i));
//                    //System.out.printf("%-8s\t", rs.getObject(i));
//                System.out.println();
//            }
//            
//            rs.close();
//        }
//        catch (SQLException sqlException)
//        {
//            System.out.println(sqlException.toString());
//        }
//        
//        }
//        
//        else
//        {
//            System.out.println("Title not found.");
//        }
//        
//
//        option = menu();
//        
//    }
//    
//    persist.disconnect();
//    
    }

    
    public static int fibonacci(int number)
    {
        if (number == 0 || number == 1)
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }
}
