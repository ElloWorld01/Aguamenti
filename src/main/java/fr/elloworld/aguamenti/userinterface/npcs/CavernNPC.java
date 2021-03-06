package fr.elloworld.aguamenti.userinterface.npcs;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.userinterface.npcs.managers.FishingNPC;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Objects;

public class CavernNPC extends FishingNPC {
    private NPC npc;
    private String name;
    private final Aguamenti main;

    public CavernNPC(Aguamenti main) {
        this.main = main;
    }

    public void createNPC() {
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "┬ždCaverne");
    }

    public NPC getNpc() {
        return npc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(Objects.requireNonNull(
                main.getConfig().getString("world"))),
                main.getConfig().getDoubleList("cavern").get(0),
                main.getConfig().getDoubleList("cavern").get(1),
                main.getConfig().getDoubleList("cavern").get(2));
    }
}
