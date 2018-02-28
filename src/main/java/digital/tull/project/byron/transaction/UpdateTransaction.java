package digital.tull.project.byron.transaction;

import java.util.Scanner;

public class UpdateTransaction extends TransactionDecorator
{
	private final String pkColumn;
	private final String pkValue;
	
	public UpdateTransaction(TableStatement tableStatement, String pkColumn, String pkValue)
	{
		super(tableStatement);
		this.pkColumn = pkColumn;
		this.pkValue = pkValue;
	}
	
	@Override
	public String getStatement()
	{
		StringBuilder newStatement = new StringBuilder();
		
		Scanner input = new Scanner(System.in);
		
		newStatement.append("UPDATE " + super.getStatement() + " SET ");
		
		for (int i = 1; i <= 2; i++)
		{
			if (i == 1)
			{
				System.out.println("Column to update:");
				newStatement.append(input.nextLine() + " = ");
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
