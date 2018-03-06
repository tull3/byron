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

package digital.tull.project.byron.transaction;

import java.util.List;
import java.util.Scanner;

public class InsertTransaction implements Transaction
{
	private final String tableName;
    private final List<String> columnNames;
    
    public InsertTransaction(String tableName, List<String> columnNames)
    {
    	this.tableName = tableName;
        this.columnNames = columnNames;
    }
    
    @Override
    public String getStatement()
    {
    	StringBuilder newStatement = new StringBuilder();
    	Scanner input = new Scanner(System.in);
    	
    	newStatement.append("INSERT INTO " + tableName + " VALUES ( ");
    	
    	for (int i = 0; i < columnNames.size(); i++)
    	{
    		System.out.println(columnNames.get(i));
    		
    		String inputString = input.nextLine();
    		
    		if (inputString.equals(""))
    			newStatement.append("NULL, ");
    		else
    			newStatement.append("'" + inputString + "'" + ", ");
    	}
    	
    	newStatement.deleteCharAt(newStatement.length() - 1);
    	
    	newStatement.deleteCharAt(newStatement.length() - 1);
    	
    	newStatement.append(" )");
    	
    	input.close();
    	
    	return newStatement.toString();
    }
}
