package me.yoshiro09.acidislands.api.purifier;

public class PurifyingConduitLevel {
    private final int level;
    private final int range;
    private final int price;

    public PurifyingConduitLevel(int level, int range, int price) {
        this.level = level;
        this.range = range;
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public int getRange() {
        return range;
    }

    public int getPrice() {
        return price;
    }
}
