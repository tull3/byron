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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Entity
{
    private List<String[]> properties = new ArrayList<>();
    //private List<String> map = new ArrayList();
    private String pkColumn;
    private String pkValue;
    private String tableName;
    private boolean cancelFlag;
    
    //TODO: add foreign key fields that can't be changed at runtime, like Author's Name or pkColumn
    
    public Entity()
    {
        
    }
    
    public Entity(String pkColumn, String tableName)
    {
        this.pkColumn = pkColumn;
        this.tableName = tableName;
    }

    public boolean isCanceled()
    {
        return cancelFlag;
    }

    public void setCancelFlag(boolean cancelFlag)
    {
        this.cancelFlag = cancelFlag;
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
    
    public String getTableName()
    {
        return tableName;
    }
    
    public Entity setTableName(String tableName)
    {
        this.tableName = tableName;
        return this;
    }
    
    public Entity setPKColumn(String pkColumn)
    {
        this.pkColumn = pkColumn;
        return this;
    }
    
    public String getPKColumn()
    {
        return pkColumn;
    }
    
    public String getPKValue()
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
    
    public void setProperties(List<String[]> properties)
    {
        this.properties = properties;
    }
    
    public String[] getProperty(int key)
    {
//        String[] mapping = new String[properties.size()];
//        
//        for (String s: mapping)
//            properties.
//        
//        String index = mapping[key];
        
        String[] set = properties.get(key);
        
        return set;
    }
    
    public void setProperty(String key, String value)
    {
        String[] set = {key, value};
        properties.add(set);
    }
    
    public void setProperties(String[] set)
    {
        properties.add(set);
    }
    
//    public void setPropertyList(List<String> value)
//    {
//        for (int i = 1; i <= value.size(); i++)
//            propertyList.add(value.get(i - 1));
//    }
    
    public int getSize()
    {
        return properties.size();
    }
    
    public void reset()
    {
    	properties = new ArrayList<>();
    }
    
    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();
        
        for (int i = 0; i < properties.size(); i++)
        {
            String[] set = properties.get(i);
            String s = set[0] + ":" + set[1] + "  ";
            string.append(s);
        }
        return string.toString();
    }
}
