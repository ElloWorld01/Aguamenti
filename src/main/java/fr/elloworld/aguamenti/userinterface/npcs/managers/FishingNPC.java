package fr.elloworld.aguamenti.userinterface.npcs.managers;

import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public abstract class FishingNPC {

    @Getter
    private NPC npc;
    @Getter
    @Setter
    private String name;
    @Getter
    private Location location;

    public void createNPC() {
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
    }
}
