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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DecimalColumn extends ColumnDecorator
{
	private final List<BigDecimal> data = new ArrayList<>();

	public DecimalColumn(Column column)
	{
		super(column);
	}

	@Override
	public String getLabel()
	{
		return super.getLabel();
	}
	
	@Override
	public List<BigDecimal> getData()
	{
		return data;
	}

	@Override
    public int getNumberOfRecords() { return data.size(); }

    @Override
    public void addRecord(String record)
    {
        data.add(new BigDecimal(record));
    }

    private String getMaxValue()
    {
        Iterator it = data.iterator();
        BigDecimal line;
        int precision = 0;
        int scale = 0;

        while (it.hasNext())
        {
            line = (BigDecimal) it.next();

            if (precision < line.precision())
            {
                precision = line.precision();
            }

            if (scale < line.scale())
            {
                scale = line.scale();
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
