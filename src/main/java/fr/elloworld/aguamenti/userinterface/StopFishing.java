package fr.elloworld.aguamenti.userinterface;

import fr.elloworld.aguamenti.utils.Colors;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class StopFishing implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getItem() == null)
            return;
        if (e.getItem().getType() != Material.SLIME_BALL)
            return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Colors.getRed() + "Vous avez quitté la pêche"));
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i) != null) {
                    String itemName = p.getInventory().getItem(i).getItemMeta().getDisplayName();
                    Material type = p.getInventory().getItem(i).getType();
                    if (type.equals(Material.FISHING_ROD) && itemName.contains("Tu es dans la zone")
                            || (type.equals(Material.SLIME_BALL) && itemName.contains("Arrêter la pêche"))
                            || type.equals(Material.AXOLOTL_BUCKET) && itemName.contains("Inventaire de pêche"))
                        p.getInventory().clear(i);
                }
            }
        }
    }
}
