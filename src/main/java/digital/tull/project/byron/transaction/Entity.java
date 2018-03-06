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

import java.util.ArrayList;
import java.util.List;

public class Entity
{
	private final List<Column> columns;
    private List<String[]> properties;
    
    public Entity(List<Column> columns)
    {
        this.columns = columns;
    }
    
    public String getProperty(String key)
    {
        String value = null;
        String[] set = new String[2];
        
        for (int i = 0; i < properties.size(); i++)
        {
            set = properties.get(i);
            
            if (set[0].equals(key))
                value = set[1];
        }
        
        return value;
    }
    
    public String getPKValue(String pkColumn)
    {
        String[] set = new String[2];
        
        for (int i = 0; i < properties.size(); i++)
        {
            set = properties.get(i);
            if (set[0].equals(pkColumn))
                break;
        }
        
        return set[1];
    }
    
    public List<String[]> getProperties()
    {
        return properties;
    }
    
    public String getProperty(int key)
    {
//        String[] mapping = new String[properties.size()];
//        
//        for (String s: mapping)
//            properties.
//        
//        String index = mapping[key];
        
        String[] set = properties.get(key);
        
        return set[1];
    }
    
    public String getColumnString(String key)
    {
    	Column column = null;
    	
    	for (int i = 0; i < columns.size(); i++)
    	{
    		if (columns.get(i).getLabel().equals(key))
    			column = columns.get(i);
    	}
    	
    	if (column == null)
    	{
    		System.out.println("Column not found.");
    		return null;
    	}
    	
    	return column.toString();
    }
    
    public Object getColumnData(String key)
    {
    	Column column = null;
    	
    	for (int i = 0; i < columns.size(); i++)
    	{
    		if (columns.get(i).getLabel().equals(key))
    			column = columns.get(i);
    	}
    	
    	if (column == null)
    	{
    		System.out.println("Column not found.");
    		return null;
    	}
    	
    	return column.getData();
    }
    
    public void printString()
    {
        //StringBuilder string = new StringBuilder();
        
        for (int i = 0; i < columns.size(); i++)
        {
            System.out.println(columns.get(i).getLabel() + ":  " + columns.get(i).toString());
        }
    }
}
