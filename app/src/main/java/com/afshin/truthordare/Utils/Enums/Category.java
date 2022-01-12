package com.afshin.truthordare.Utils.Enums;

public enum Category {
    Personal(0), Challenge(1), Entertainement(2), Physical(3), Disgusting(4);

    private int type;
    Category(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
