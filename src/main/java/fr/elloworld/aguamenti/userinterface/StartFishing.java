package fr.elloworld.aguamenti.userinterface;

import fr.elloworld.aguamenti.utils.Colors;
import fr.worsewarn.cosmox.tools.items.ItemBuilder;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class StartFishing implements Listener {

    public void startFishing(Player p, NPC npc) {
        if (p.getInventory().contains(Material.SLIME_BALL) || p.getInventory().contains(Material.FISHING_ROD))
            return;

        p.sendTitle(Colors.getTurquoise() + "Pêche", npc.getName(), 10, 70, 20);
        p.playSound(p.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
        short one = 1;
        ItemStack rod = new ItemBuilder(Material.FISHING_ROD, one)
                .setDisplayName("§6§kL §r§fTu es dans la zone §l" + npc.getName() + "§r§f §6§kL")
                .setGlow(true).setUnbreakable(true).addItemFlag(ItemFlag.HIDE_UNBREAKABLE).build();

        ItemStack axolotlBucket = new ItemBuilder(Material.AXOLOTL_BUCKET, one)
                .setDisplayName("§6§kL §r§fTu es dans la zone \"§l" + npc.getName() + "§r§f\" §6§kL")
                .setGlow(true).build();

        ItemStack slimeball = new ItemBuilder(Material.SLIME_BALL, one)
                .setDisplayName("§6§kL §r§fTu es dans la zone \"§l" + npc.getName() + "§r§f\" §6§kL")
                .setGlow(true).build();

        if (p.getInventory().getItem(2) == null) p.getInventory().setItem(2, rod);
        else p.getInventory().addItem(rod);

        if (p.getInventory().getItem(4) == null) p.getInventory().setItem(4, axolotlBucket);
        else p.getInventory().addItem(axolotlBucket);

        if (p.getInventory().getItem(6) == null) p.getInventory().setItem(6, slimeball);
        else p.getInventory().addItem(slimeball);

        p.updateInventory();
    }
}
