package fr.elloworld.aguamenti.userinterface;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.gson.User;
import fr.elloworld.aguamenti.gson.fish.Fish;
import fr.elloworld.aguamenti.utils.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FisherInventory implements Listener {
    private static final Inventory inv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Inventaire de pêche");
    private final Aguamenti main;

    public FisherInventory(Aguamenti main) {
        this.main = main;
    }

    public static Inventory getInv() {
        return inv;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null)
            return;
        if (e.getItem().getType() != Material.AXOLOTL_BUCKET)
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
            openInventory(e.getPlayer());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player p && e.getInventory().equals(inv))
            p.playSound(e.getPlayer().getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0F, 1.0F);
    }

    public void openInventory(Player p) {
        List<User> userList = main.getFileUtils().deserialize();

        if (userList == null) {
            p.sendMessage(main.getPrefix() + Colors.getRed() + "Tu n'as pas encore pêché, tu ne peux pas accéder à cette commande." +
                    "\n" + main.getPrefix() + "§oEssaye d'aller près d'un lac pour pêcher.");
            return;
        }

        p.playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0F, 1.0F);
        int i = 0;
        while (!userList.get(i).getUUID().equals(p.getUniqueId()) && i < userList.size())
            i++;

        if (i >= userList.size()) {
            p.sendMessage(main.getPrefix() + Colors.getRed() + "Tu n'as pas encore pêché, tu ne peux pas accéder à cette commande." +
                    "\n" + main.getPrefix() + "§oEssaye d'aller près d'un lac pour pêcher.");
        } else {
            User user = userList.get(i);
            Fish bestFish = user.getBestFish();
            ItemStack bFish = bestFish.getFishItemStack();

            ItemMeta f = bFish.getItemMeta();

            if (f == null)
                return;

            f.setDisplayName(ChatColor.GOLD + "§lMeilleur poisson");
            f.setLore(List.of(Colors.getWhite() + "Longueur : " + Colors.getTurquoise() + bestFish.getFishLength() + Colors.getWhite() + " cm"));
            bFish.setItemMeta(f);

            getInv().clear();
            getInv().setItem(4, bFish);
            LinkedList<Fish> threeBestFishList = user.getThreeBestFish();
            threeBestFishList.sort(Collections.reverseOrder());

            for (i = threeBestFishList.size() - 1; i >= 0; i--) {

                Fish current = user.getThreeBestFish().get(i);
                ItemStack currentIS = current.getFishItemStack();

                ItemMeta cf = currentIS.getItemMeta();

                if (cf != null) {
                    cf.setDisplayName(ChatColor.GOLD + "Meilleur poisson n°" + (i + 1));
                    cf.setLore(List.of(Colors.getWhite() + "Longueur : " + Colors.getTurquoise() + current.getFishLength() + Colors.getWhite() + " cm"));
                }
                currentIS.setItemMeta(cf);

                getInv().setItem(21 + i, currentIS);
            }
            p.openInventory(getInv());
        }
    }
}