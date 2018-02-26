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



public class InsertTransaction extends TransactionDecorator
{
    private int numberOfValues;
    
    public InsertTransaction(TableStatement tableStatement, int numberOfValues)
    {
    	super(tableStatement);
        this.numberOfValues = numberOfValues;
    }
    
    @Override
    public String getStatement()
    {
    	StringBuilder newStatement = new StringBuilder();
    	
    	newStatement.append("INSERT INTO " + super.getStatement() + " VALUES ( ");
    	
    	for (int i = 0; i < numberOfValues; i++)
    	{
    		newStatement.append("?,");
    	}
    	
    	newStatement.deleteCharAt(newStatement.length());
    	
    	newStatement.append(")");
    	
    	return newStatement.toString();
    }
}
