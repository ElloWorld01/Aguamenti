package fr.elloworld.aguamenti.gson;

import fr.elloworld.aguamenti.gson.fish.Fish;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.UUID;

public class User implements Comparable<User> {
    final UUID uuid;
    LinkedList<Fish> threeBestFish;

    public User(UUID uuid, LinkedList<Fish> fiveFirstFish) {
        this.uuid = uuid;
        this.threeBestFish = fiveFirstFish;
    }

    public UUID getUUID() {
        return uuid;
    }

    public LinkedList<Fish> getThreeBestFish() {
        return threeBestFish;
    }

    public void setThreeBestFish(LinkedList<Fish> threeBestFish) {
        this.threeBestFish = threeBestFish;
    }

    public Fish getBestFish() {
        if (!getThreeBestFish().isEmpty())
            return getThreeBestFish().stream().sorted(Comparator.reverseOrder()).findFirst().get();
        return null;
    }

    @Override
    public int compareTo(@NotNull User user) {

        return (int) (this.getBestFish().getFishLength() - user.getBestFish().getFishLength());
    }
}
