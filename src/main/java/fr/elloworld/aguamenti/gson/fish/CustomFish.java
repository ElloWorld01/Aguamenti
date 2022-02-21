package fr.elloworld.aguamenti.gson.fish;

import fr.elloworld.aguamenti.gson.User;
import fr.elloworld.aguamenti.utils.Colors;
import fr.worsewarn.cosmox.tools.items.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class CustomFish {

    public ItemStack generateFish(double d) {
        short one = 1;
        // --- increasing values --- //
        if (d > 115 && d <= 130)
            return new ItemBuilder(Material.COD, one).setDisplayName(ChatColor.GREEN + "Colin commun").build();
        else if (d > 130 && d <= 145)
            return new ItemBuilder(Material.SALMON, one).setDisplayName(ChatColor.DARK_GREEN + "Saumon rare").build();
        else if (d > 145 && d <= 160)
            return new ItemBuilder(Material.COD, one).setDisplayName(ChatColor.DARK_GREEN + "Esturgeon rare").build();
        else if (d > 160 && d <= 175)
            return new ItemBuilder(Material.COD, one).setDisplayName(ChatColor.GOLD + "Silure épique").setGlow(true).build();
        else if (d > 175 && d <= 190) // σ + 3 = 180
            return new ItemBuilder(Material.TROPICAL_FISH, one).setDisplayName("§r§kL§r" + Colors.getRed() + "Darwin Watterson unique§r§kL§r").setGlow(true).build();
        else if (d > 190)
            return new ItemBuilder(Material.COD, one).setDisplayName("§r§kL§r" + Colors.getDarkRed() + "§lNapoléon légendaire§r§kL").setGlow(true).build();

            // --- Mean value --- //
        else if (d >= 100) // σ = 107.5
            return new ItemBuilder(Material.COD, one).setDisplayName(Colors.getTurquoise() + "Bar commun").build();

            // --- decreasing values ---//
        else if (d >= 85)
            return new ItemBuilder(Material.TROPICAL_FISH, one).setDisplayName(ChatColor.DARK_AQUA + "Poisson-clown commun").build();
        else if (d >= 70)
            return new ItemBuilder(Material.PUFFERFISH, one).setDisplayName(ChatColor.DARK_BLUE + "Poisson-globe rare").build();
        else if (d >= 55)
            return new ItemBuilder(Material.TROPICAL_FISH, one).setDisplayName(ChatColor.DARK_AQUA + "Magicarpe Rare").build();
        else if (d >= 40)
            return new ItemBuilder(Material.PUFFERFISH, one).setDisplayName(ChatColor.BLUE + "Magicarpe Shiny épique").setGlow(true).build();
        else if (d >= 25)  // σ - 3 = 35
            return new ItemBuilder(Material.SALMON, one).setDisplayName("§r§kL§r" + ChatColor.LIGHT_PURPLE + "Sardine de Patrick unique§r§kL").setGlow(true).build();
        else if (d < 25)
            return new ItemBuilder(Material.TROPICAL_FISH, one).setDisplayName("§r§kL§r" + ChatColor.DARK_PURPLE + "§lCrevette de Xelouille légendaire§r§kL").setGlow(true).build();
        else
            return new ItemBuilder(Material.COD, one).setDisplayName(Colors.getTurquoise() + "Bar commun").build();

    }

    public LinkedList<Fish> threeBestFishManager(User user, Fish newFish, Fish bestFish) {

        LinkedList<Fish> threeBestFishList = user.getThreeBestFish();

        switch (threeBestFishList.size()) {
            case 1 -> {
                if (newFish.getFishLength() >= threeBestFishList.getFirst().getFishLength()) {
                    threeBestFishList.addLast(newFish);
                } else {
                    threeBestFishList.addFirst(newFish);
                }
                return threeBestFishList;
            }
            default -> {
                threeBestFishList.addLast(bestFish);
                return threeBestFishList;
            }
            case 2 -> {
                threeBestFishList.addLast(null);
                if (newFish.getFishLength() >= threeBestFishList.getFirst().getFishLength() && newFish.getFishLength() <= threeBestFishList.get(1).getFishLength()) {
                    threeBestFishList.set(2, threeBestFishList.get(1));
                    threeBestFishList.set(1, newFish);
                    return threeBestFishList;

                } else if (newFish.getFishLength() >= threeBestFishList.get(1).getFishLength()) {
                    threeBestFishList.set(2, newFish);
                    return threeBestFishList;
                } else {
                    threeBestFishList.set(2, threeBestFishList.get(1));
                    threeBestFishList.set(1, threeBestFishList.get(0));
                    threeBestFishList.set(0, newFish);
                }
            }
            case 3 -> {
                if (newFish.getFishLength() >= threeBestFishList.getFirst().getFishLength() && newFish.getFishLength() <= threeBestFishList.get(1).getFishLength()) {
                    threeBestFishList.removeFirst();
                    threeBestFishList.addFirst(newFish);
                    return threeBestFishList;

                } else if (newFish.getFishLength() >= threeBestFishList.get(1).getFishLength() && newFish.getFishLength() <= threeBestFishList.get(2).getFishLength()) {
                    threeBestFishList.set(0, threeBestFishList.get(1));
                    threeBestFishList.set(1, newFish);
                    return threeBestFishList;

                } else if (newFish.getFishLength() >= threeBestFishList.get(2).getFishLength()) {
                    threeBestFishList.set(0, threeBestFishList.get(1));
                    threeBestFishList.set(1, threeBestFishList.get(2));
                    threeBestFishList.set(2, newFish);
                    return threeBestFishList;
                }
            }
        }
        return threeBestFishList;
    }
}