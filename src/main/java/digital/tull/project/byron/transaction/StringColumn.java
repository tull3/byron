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
import java.util.Iterator;
import java.util.List;

public class StringColumn extends ColumnDecorator
{
	private final List<String> data = new ArrayList<>();
	private final ColumnType type = ColumnType.STRING_COLUMN;

	public StringColumn(Column column)
	{
		super(column);
	}

	@Override
	public String getLabel()
	{
		return super.getLabel();
	}
	
	@Override
	public List<String> getData()
	{
		return data;
	}

    @Override
    public int getLength() { return data.size(); }

    @Override
    public void addRecord(String record)
    {
        data.add(record);
    }

    private int getMaxValue()
    {
        Iterator it = data.iterator();
        int size = 0;

        while (it.hasNext())
        {
            String line = (String) it.next();

            if (size < line.length())
            {
                size = line.length();
            }
        }

        return size + 15;
    }
	
	@Override
	public String getDatabaseType()
	{
		return "VARCHAR(" + getMaxValue() + ")";
	}
}
