package digital.tull.project.byron.transaction;

public class NullColumn implements Column
{
	private final String label;
	private final ColumnType type = ColumnType.NULL_COLUMN;
	
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
		return ColumnType.NULL_COLUMN;
	}

	@Override
    public int getLength() { return 0; }

	@Override
    public void addRecord(String record) {}

    @Override
    public String toString() { return ""; }
}
