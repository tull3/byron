package digital.tull.project.byron.transaction;

import java.util.ArrayList;
import java.util.List;

public class BooleanColumn extends ColumnDecorator
{
    private final List<Boolean> data = new ArrayList<>();
    private final ColumnType type = ColumnType.BOOLEAN_COLUMN;

    public BooleanColumn(Column column)
    {
        super(column);
    }

    @Override
    public String getLabel()
    {
        return super.getLabel();
    }

    @Override
    public List<Boolean> getData()
    {
        return data;
    }

    @Override
    public int getLength() { return data.size(); }

    @Override
    public void addRecord(String record)
    {
        data.add(Boolean.getBoolean(record));
    }

    @Override
    public String getDatabaseType()
    {
        return "TINYINT(1)";
    }
}
