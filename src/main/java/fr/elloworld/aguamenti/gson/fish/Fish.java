package fr.elloworld.aguamenti.gson.fish;

import org.bukkit.inventory.ItemStack;

public class Fish implements Comparable<Fish> {

    final double fishLength;
    final ItemStack fishItemStack;

    public Fish(double fishLength, ItemStack fishItemStack) {
        this.fishLength = fishLength;
        this.fishItemStack = fishItemStack;
    }

    public double getFishLength() {
        return fishLength;
    }

    public ItemStack getFishItemStack() {
        return fishItemStack;
    }

    @Override
    public int compareTo(Fish fish) {
        if (this.fishLength > fish.getFishLength())
            return 1;
        else if (this.fishLength < fish.getFishLength())
            return -1;
        return 0;
    }
}