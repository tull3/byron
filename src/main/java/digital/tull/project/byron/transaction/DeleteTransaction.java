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

public class DeleteTransaction implements Transaction
{
	private final String tableName;
	private final String pkColumn;
	private final String pkValue;
	
	public DeleteTransaction(String tableName, String pkColumn, String pkValue)
	{
		this.tableName = tableName;
		this.pkColumn = pkColumn;
		this.pkValue = pkValue;
	}
	
	@Override
	public String getStatement()
	{
		return "DELETE FROM " + tableName + " WHERE " + pkColumn + " = " + pkValue;
	}
}
