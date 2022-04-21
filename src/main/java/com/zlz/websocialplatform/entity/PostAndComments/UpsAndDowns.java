package com.zlz.websocialplatform.entity.PostAndComments;

public class UpsAndDowns {
    int ups;
    int downs;
    String activated;

    public UpsAndDowns(int ups, int downs, String activated) {
        this.ups = ups;
        this.downs = downs;
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "{" +
                "\"ups\":" + ups +
                ", \"downs\":" + downs +
                ", \"activated\": \"" + activated + '\"' +
                "}";
    }
}
