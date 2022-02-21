package fr.elloworld.aguamenti.userinterface;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class CancelledEvents implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(FisherInventory.getInv())) {
            e.setCancelled(true);
        }
    }
}
