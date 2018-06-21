package digital.tull.project.byron.transaction;

public enum ColumnType
{
    NULL_COLUMN(0),
    STRING_COLUMN(1),
    INTEGER_COLUMN(2),
    DECIMAL_COLUMN(3),
    BOOLEAN_COLUMN(4),
    DOUBLE_COLUMN(5);

    private final int option;

    private ColumnType(int option)
    {
        this.option = option;
    }
}
