package digital.tull.project.byron.transaction;

import java.util.Scanner;

public class UpdateTransaction implements Transaction
{
	private final String tableName;
	private final String[] primaryKeys;
	private final String[] newValues;
	
	public UpdateTransaction(String tableName, String[] primaryKeys, String[] newValues)
	{
		this.tableName = tableName;
		this.primaryKeys = primaryKeys;
		this.newValues = newValues;
	}
	
	@Override
	public String getStatement()
	{
		return "UPDATE " + tableName + " SET " + newValues[0] + " = " + newValues[1] + " WHERE " + primaryKeys[0] + " = " + primaryKeys[1];
	}
}
