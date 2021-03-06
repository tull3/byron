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

abstract class ColumnDecorator implements Column
{
	private final Column column;
	
	public ColumnDecorator(Column column)
	{
		this.column = column;
	}

	@Override
	public String getLabel()
	{
		return column.getLabel();
	}
	
	@Override
	public Object getData()
	{
		return column.getData();
	}
}
