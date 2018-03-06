package digital.tull.project.byron.transaction;

public class NullColumn implements Column
{
	private final String label;
	
	public NullColumn(String label)
	{
		this.label = label;
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	@Override
	public Object getData()
	{
		return null;
	}

}
