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

public class DoubleColumn extends ColumnDecorator
{
	private final List<Double> data = new ArrayList<>();

	public DoubleColumn(Column column)
	{
		super(column);
	}

	@Override
	public String getLabel()
	{
		return super.getLabel();
	}

	@Override
	public List<Double> getData()
	{
		return data;
	}

	@Override
	public int getLength() { return data.size(); }

	@Override
	public void addRecord(String record)
	{
		data.add(Double.valueOf(record));
	}

	private String getMaxValue()
	{
		Iterator it = data.iterator();
		String line;
		int precision = 0;
		int scale = 0;

		while (it.hasNext())
		{
			line = (String) it.next();

			String[] values = line.split(".");

			if (precision < line.length())
			{
				precision = line.length();
			}

			if (scale < Integer.parseInt(values[1]))
			{
				scale = Integer.parseInt(values[1]);
			}
		}

		return String.valueOf(precision + 2) + "," + String.valueOf(scale);
	}

	@Override
	public String getDatabaseType()
	{
		return "DECIMAL(" + getMaxValue() + ")";
	}
}
