package me.gt3ch1.betterlife.data;

/**
 * Represents all the enums for betterlife.
 * TRAIL_ENABLED_PER_PLAYER - SQL column: TrailToggle, Table: BL_PLAYER
 * TRAIL_PER_PLAYER - SQL column: Trail, Table: BL_PLAYER
 * ROADBOOST_PER_PLAYER - SQL column: RoadBoostToggle, Table: BL_PLAYER
 * HOME_PER_PLAYER - SQL column: Home, Table: BL_HOME
 */
public enum BL_PLAYER_ENUM {

    TRAIL_ENABLED_PER_PLAYER("TrailToggle","BL_PLAYER"),
    TRAIL_PER_PLAYER("Trail","BL_PLAYER"),
    ROADBOOST_PER_PLAYER("RoadBoostToggle","BL_PLAYER"),
    HOME_PER_PLAYER("Home","BL_HOME");

    private String column;
    private String table;

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
}
