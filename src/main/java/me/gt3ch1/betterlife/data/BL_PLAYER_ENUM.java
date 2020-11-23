package me.gt3ch1.betterlife.data;

public enum BL_PLAYER_ENUM {

    TRAIL_ENABLED_PER_PLAYER("trailEnabledPerPlayer"),
    TRAIL_PER_PLAYER("trailPerPlayer"),
    ROADBOOST_PER_PLAYER("roadboost");

    private String type;

    BL_PLAYER_ENUM(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
