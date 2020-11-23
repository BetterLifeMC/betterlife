package me.gt3ch1.betterlife.data;

public enum BL_PLAYER_ENUM {

    TRAIL_ENABLED_PER_PLAYER("TrailToggle"),
    TRAIL_PER_PLAYER("Trail"),
    ROADBOOST_PER_PLAYER("RoadBoostToggle");

    private String type;

    BL_PLAYER_ENUM(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
