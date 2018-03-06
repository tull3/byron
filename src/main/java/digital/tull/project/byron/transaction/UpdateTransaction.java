package digital.tull.project.byron.transaction;

import java.util.Scanner;

public class UpdateTransaction implements Transaction
{
	private final String tableName;
	private final String pkColumn;
	private final String pkValue;
	
	public UpdateTransaction(String tableName, String pkColumn, String pkValue)
	{
		this.tableName = tableName;
		this.pkColumn = pkColumn;
		this.pkValue = pkValue;
	}
	
	@Override
	public String getStatement()
	{
		StringBuilder newStatement = new StringBuilder();
		
		Scanner input = new Scanner(System.in);
		
		newStatement.append("UPDATE " + tableName + " SET ");
		
		for (int i = 1; i <= 2; i++)
		{
			if (i == 1)
			{
				System.out.println("Column to update:");
				newStatement.append(input.nextLine().toUpperCase() + " = ");
			}
			else
			{
				System.out.println("Value to insert:");
				newStatement.append("'" + input.nextLine() + "'");
			}
		}
		
		input.close();
		
		return newStatement.toString() + " WHERE " + pkColumn + " = " + pkValue;
	}
}
