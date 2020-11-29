package me.gt3ch1.betterlife.data;

/**
 * Represents all the enums for betterlife.
 * <p>
 * MUTE_PER_PLAYER  -   SQL column: MuteToggle, Table: BL_PLAYER, sqlType: BOOL
 * ROADBOOST_PER_PLAYER - SQL column: RoadBoostToggle, Table: BL_PLAYER, sqlType: BOOL
 * TRAIL_ENABLED_PER_PLAYER - SQL column: TrailToggle, Table: BL_PLAYER, sqlType: BOOL
 * <p>
 * TRAIL_PER_PLAYER - SQL column: Trail, Table: BL_PLAYER, sqlType: NVCHAR
 * <p>
 * HOME_PER_PLAYER - SQL column: Home, Table: BL_HOME, sqlType: none
 */
public enum BL_PLAYER_ENUM {

    // BL_PLAYER toggles
    MUTE_PER_PLAYER("MuteToggle", "BL_PLAYER", "BOOL"),
    ROADBOOST_PER_PLAYER("RoadBoostToggle", "BL_PLAYER", "BOOL"),
    TRAIL_ENABLED_PER_PLAYER("TrailToggle", "BL_PLAYER", "BOOL"),

    // BL_PLAYER Strings
    TRAIL_PER_PLAYER("Trail", "BL_PLAYER", "NVCHAR(30)"),

    // BL_HOME
    HOME_PER_PLAYER("Home", "BL_HOME");

    private String column;
    private String table;
    private String sqlType;

    BL_PLAYER_ENUM(String column, String table, String sqlType){
        this.column = column;
        this.table = table;
        this.sqlType = sqlType;
    }

    BL_PLAYER_ENUM(String column, String table){
        this.column = column;
        this.table = table;
    }

    /**
     * Gets the column that corresponds to the SQL table.
     * @return SQL Column.
     */
    public String getColumn(){
        return column;
    }

    /**
     * Gets the table name.
     * @return  SQL table name.
     */
    public String getTable(){
        return table;
    }

    /**
     * Gets the SQL data type.
     * @return SQL data type.
     */
    public String getSqlType() {
        return sqlType;
    }

    /**
     * Gets the default SQL value for the enum.
     * @return The default SQL value.
     */
    public String getDefault() {
        switch (getSqlType()) {
            case "BOOL":
                return "false";
            default:
                return null;
        }
    }
}
