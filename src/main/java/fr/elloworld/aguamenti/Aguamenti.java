package fr.elloworld.aguamenti;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.elloworld.aguamenti.command.CommandFishing;
import fr.elloworld.aguamenti.command.FishingTabCompletion;
import fr.elloworld.aguamenti.gson.fish.CustomFish;
import fr.elloworld.aguamenti.gson.utils.ConfigurationSerializableAdapter;
import fr.elloworld.aguamenti.gson.utils.FileUtils;
import fr.elloworld.aguamenti.leaderboard.LeaderboardManager;
import fr.elloworld.aguamenti.leaderboard.UpdateLeaderboard;
import fr.elloworld.aguamenti.playing.Fishing;
import fr.elloworld.aguamenti.userinterface.CancelledEvents;
import fr.elloworld.aguamenti.userinterface.FisherInventory;
import fr.elloworld.aguamenti.userinterface.StartFishing;
import fr.elloworld.aguamenti.userinterface.StopFishing;
import fr.elloworld.aguamenti.userinterface.npcs.CavernNPC;
import fr.elloworld.aguamenti.userinterface.npcs.ForgottenSourceNPC;
import fr.elloworld.aguamenti.userinterface.npcs.MainLakeNPC;
import fr.elloworld.aguamenti.userinterface.npcs.managers.FishingNPC;
import fr.elloworld.aguamenti.userinterface.npcs.managers.FishingNPCManager;
import fr.worsewarn.cosmox.API;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Aguamenti extends JavaPlugin {

    @Getter
    private final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(
            ConfigurationSerializable.class, new ConfigurationSerializableAdapter()
    ).setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
    @Getter
    private final String prefix = "§r§l[" + ChatColor.AQUA + "Fishing§r§l] §r";
    private final FileConfiguration config = this.getConfig();
    @Getter
    private final File fileLeaderboard = new File(getDataFolder(), "leaderboard.json");
    @Getter
    final List<FishingNPC> fishingNPCs = new ArrayList<>();
    @Getter
    private API api;
    @Getter
    private LeaderboardManager leaderboardManager;
    @Getter
    private FileUtils fileUtils;
    @Getter
    private CustomFish customFish;
    @Getter
    private StartFishing startFishing;
    @Getter
    private FishingNPCManager fishingNPCManager;
    @Getter
    private CavernNPC cavernNPC;
    @Getter
    private ForgottenSourceNPC forgottenSourceNPC;
    @Getter
    private MainLakeNPC mainLakeNPC;
    @Getter
    private FisherInventory fisherInv;

    @Override
    public void onDisable() {
        leaderboardManager.removeLeaderboard();
        fishingNPCManager.removeAllNPCs();
    }

    @Override
    public void onEnable() {
        saveConfig();
        api = API.instance();
        leaderboardManager = new LeaderboardManager(this);
        fileUtils = new FileUtils(this);
        customFish = new CustomFish();
        startFishing = new StartFishing();
        fishingNPCManager = new FishingNPCManager(this);
        fisherInv = new FisherInventory(this);

        cavernNPC = new CavernNPC(this);
        forgottenSourceNPC = new ForgottenSourceNPC(this);
        mainLakeNPC = new MainLakeNPC(this);

        createFileLeaderboard();
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Fishing(this), this);
        pm.registerEvents(new CancelledEvents(), this);
        pm.registerEvents(new FisherInventory(this), this);
        pm.registerEvents(new StopFishing(), this);
        pm.registerEvents(new FishingNPCManager(this), this);
        pm.registerEvents(new UpdateLeaderboard(this), this);

        Objects.requireNonNull(getCommand("fishing")).setExecutor(new CommandFishing(this));
        Objects.requireNonNull(getCommand("fishing")).setTabCompleter(new FishingTabCompletion());

        getFishingNPCManager().createNPCs();
        getLeaderboardManager().createLeaderboard();
    }

    private void createFileLeaderboard() {
        if (!getFileLeaderboard().exists()) {
            try {
                getFileLeaderboard().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeFileLeaderboard() {
        if (getFileLeaderboard().exists()) {
            getFileLeaderboard().delete();
        }
    }

    public void resetFileLeaderboard() {
        removeFileLeaderboard();
        createFileLeaderboard();
    }
}