package fr.elloworld.aguamenti.leaderboard;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.gson.User;
import fr.elloworld.aguamenti.gson.fish.Fish;
import fr.worsewarn.cosmox.tools.world.Hologram;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class LeaderboardManager {

    private static final int LEADERBOARD_SIZE = 10;
    private static final double LEADERBOARD_X = 22.5;
    private static final double LEADERBOARD_MIN_Y = 78;
    private static final double LEADERBOARD_Z = 34.5;
    @Getter
    private final List<Hologram> fullLeaderboardHolograms = new ArrayList<>();
    @Getter
    private final HashMap<Player, Hologram> playersSelfHolograms = new HashMap<>();
    @Getter
    private final List<Hologram> usersHologramsList = new ArrayList<>();
    private final Aguamenti main;

    public LeaderboardManager(Aguamenti main) {
        this.main = main;
    }

    /**
     * Create the leaderboard
     */

    public void createLeaderboard() {
        createHologram("...", new Location(Bukkit.getWorld(Objects.requireNonNull(
                main.getConfig().getString("world"))), LEADERBOARD_X, LEADERBOARD_MIN_Y + 0.6, LEADERBOARD_Z));
        createHologram("§6Classement de pêche", new Location(Bukkit.getWorld(Objects.requireNonNull(
                main.getConfig().getString("world"))), LEADERBOARD_X, LEADERBOARD_MIN_Y, LEADERBOARD_Z));
        createPlayersHolograms();
        for (Player p : Bukkit.getOnlinePlayers())
            createSelfHologram(p);
        spawnAllHolograms();
    }

    /**
     * Update the leaderboard
     *
     * @param list List of top users
     */

    public void updateLeaderboard(List<User> list) {

        if (list.isEmpty())
            return;

        list.sort(Collections.reverseOrder());
        for (int i = 0; i < getUsersHologramsList().size(); i++) {
            Hologram hologram = getUsersHologramsList().get(i);
            User u = list.get(i);
            Fish bf = u.getBestFish();

            String customName = "§6§l" + (i + 1) + ". §r" + Bukkit.getOfflinePlayer(u.getUUID()).getName()
                    + " - §6" + bf.getFishLength() + " cm";

            hologram.getLines().set(0, customName);
        }
    }

    /**
     * Reload the leaderboard
     */

    public void reloadLeaderboard() {
        removeLeaderboard();
        createLeaderboard();
    }

    /**
     * Delete the leaderboard
     */

    public void removeLeaderboard() {
        if (!getFullLeaderboardHolograms().isEmpty())
            for (Hologram h : getFullLeaderboardHolograms())
                h.remove();
        if (!getPlayersSelfHolograms().isEmpty())
            for (Hologram h : getPlayersSelfHolograms().values())
                h.remove();
        if (!getUsersHologramsList().isEmpty())
            for (Hologram h : getUsersHologramsList())
                h.remove();

        getPlayersSelfHolograms().clear();
        getUsersHologramsList().clear();
        getFullLeaderboardHolograms().clear();
    }

    /**
     * Create a hologram
     *
     * @param location The position where the hologram will be created
     * @param name     The name of the hologram
     * @return The hologram that has just been created thanks to the two parameters
     */

    private Hologram createHologram(String name, Location location) {
        Hologram hologram = new Hologram(location, name);
        getFullLeaderboardHolograms().add(hologram);
        return hologram;
    }

    /**
     * Displays all holograms created for the leaderboard
     */

    private void spawnAllHolograms() {
        for (Hologram h : getFullLeaderboardHolograms())
            h.display();
    }

    /**
     * Create holograms for the top 10 players in the leaderboard
     */

    private void createPlayersHolograms() {
        List<User> userList = main.getFileUtils().deserialize();

        if (userList == null)
            return;

        if (userList.size() > LEADERBOARD_SIZE)
            userList = userList.subList(0, LEADERBOARD_SIZE);

        getUsersHologramsList().clear();
        for (int i = userList.size() - 1; i >= 0; i--) {
            Hologram h = createHologram("", new Location(Bukkit.getWorld(Objects.requireNonNull(
                    main.getConfig().getString("world"))), LEADERBOARD_X, LEADERBOARD_MIN_Y + 0.8 + (i / 3f), LEADERBOARD_Z));
            getUsersHologramsList().add(h);
            getFullLeaderboardHolograms().add(h);
            updateLeaderboard(userList);
        }
    }

    /**
     * Create a hologram showing the score of the targeted user
     *
     * @param player The targeted user
     */

    private void createSelfHologram(Player player) {

        List<User> userList = main.getFileUtils().deserialize();
        String defaultName = "§7? §r" + player.getName() + " - §7Pas de meilleur score";
        Hologram playerHologram = new Hologram(new Location(Bukkit.getWorld(Objects.requireNonNull(
                main.getConfig().getString("world"))), LEADERBOARD_X, LEADERBOARD_MIN_Y + 0.3, LEADERBOARD_Z));

        if (userList == null || userList.isEmpty()) {
            playerHologram.addLine(defaultName);
            playerHologram.display(player);
            getPlayersSelfHolograms().put(player, playerHologram);
            return;
        }

        int i = 0;
        while (i < userList.size() && !(player.getUniqueId().equals(userList.get(i).getUUID())))
            i++;
        if (!(player.getUniqueId().equals(userList.get(i).getUUID()))) {
            playerHologram.addLine(defaultName);
            playerHologram.display(player);
            getPlayersSelfHolograms().put(player, playerHologram);
            return;
        }
        userList.sort(Collections.reverseOrder());
        User user = userList.get(i);

        playerHologram.addLine("§7§l" + (i + 1) + ". §rVous - §7" + user.getBestFish().getFishLength() + " cm");
        playerHologram.display(player);
        getPlayersSelfHolograms().put(player, playerHologram);
    }
}