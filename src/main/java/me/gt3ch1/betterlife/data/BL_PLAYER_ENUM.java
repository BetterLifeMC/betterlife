package me.gt3ch1.betterlife.data;

/**
 * Represents all the enums for betterlife.
 * TRAIL_ENABLED_PER_PLAYER - SQL column: TrailToggle, Table: BL_PLAYER
 * TRAIL_PER_PLAYER - SQL column: Trail, Table: BL_PLAYER
 * ROADBOOST_PER_PLAYER - SQL column: RoadBoostToggle, Table: BL_PLAYER
 * HOME_PER_PLAYER - SQL column: Home, Table: BL_HOME
 */
public enum BL_PLAYER_ENUM {

    TRAIL_ENABLED_PER_PLAYER("TrailToggle","BL_PLAYER","BOOL"),
    TRAIL_PER_PLAYER("Trail","BL_PLAYER","NVCHAR(30)"),
    ROADBOOST_PER_PLAYER("RoadBoostToggle","BL_PLAYER","BOOL"),
    MUTE_PER_PLAYER("Mute","BL_PLAYER","BOOL"),
    HOME_PER_PLAYER("Home","BL_HOME");

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
    public String getSqlType(){ return sqlType; }
}
