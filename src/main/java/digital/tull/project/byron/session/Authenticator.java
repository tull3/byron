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

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;



public class Authenticator
{
    private Session session;
    
    private Authenticator(Session session)
    {
        this.session = session;
    }
    
//    public static Session Login(User user)
//    {
//        Session session = null;
//        
//        try (Scanner input = new Scanner(Paths.get("users.txt")))
//        {
//            String text = null;
//            
//            String[] items = new String[3];
//            
//            while (input.hasNextLine())
//            {
//                text = input.nextLine();
//                
//                items = text.split("\\");
//                
//                if (items[0].equals(user.getID()) && items[1].equals(user.getPasswd()))
//                {
//                    user.setGroupID(items[2]);
//                    session = new Session(user);
//                    break;
//                }
//            }
//            
//            if (session.isValidSession())
//            {
//                
//                System.out.println("User " + user.getID() + " is authenticated with group " + user.getGroupID() + ".");
//            }
//            
//            else
//                System.out.println("User not authenticated.");
//            
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        
//        return session;
//    }
}
