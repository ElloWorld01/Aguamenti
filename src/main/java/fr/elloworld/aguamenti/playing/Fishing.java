package fr.elloworld.aguamenti.playing;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.gson.User;
import fr.elloworld.aguamenti.gson.fish.Fish;
import fr.elloworld.aguamenti.utils.Colors;
import fr.elloworld.aguamenti.utils.Maths;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Fishing implements Listener {

    private final Aguamenti main;

    public Fishing(Aguamenti main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player p = event.getPlayer();
        PlayerFishEvent.State fishingState = event.getState();

        if (fishingState.equals(PlayerFishEvent.State.CAUGHT_FISH)) {

            Item itemCaught = (Item) event.getCaught();
            double fishLength = (double) Math.round(Maths.normalDistribution() * 100) / 100;
            ItemStack customFishIS = main.getCustomFish().generateFish(fishLength);

            if (itemCaught == null)
                return;
            if (customFishIS.getItemMeta() == null)
                return;

            itemCaught.remove();

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    main.getPrefix() + "§rTu as attrapé un " +
                            customFishIS.getItemMeta().getDisplayName() + "§r de " + Colors.getYellow() + fishLength + "§r cm !"));

            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.getInventory().remove(customFishIS);
                    p.updateInventory();
                }
            }, 10000);

            // GSON

            final List<User> userList = main.getFileUtils().deserialize();
            boolean userInFile = false;
            String tip = main.getPrefix() + "§oTu peux regarder tes §bplus gros poissons §ren §r§e§ocliquant§r§o sur ton §e§oinventaire de pêche§r§o !";

            if (userList != null) {
                for (User user : userList) {
                    if (user.getUUID().equals(p.getUniqueId())) {
                        userInFile = true;

                        Fish newFish = new Fish(fishLength, customFishIS);
                        if (user.getBestFish().getFishLength() < fishLength) {
                            p.sendMessage(main.getPrefix() + "§6Tu bats ton record de "
                                    + Colors.getYellow() + user.getBestFish().getFishLength()
                                    + "§6 cm par " + Colors.getYellow() + fishLength + "§6 cm !");
                            if (new Random().nextInt(5) == 0) {
                                p.sendMessage(main.getPrefix() + "§oMontre leur que c'est toi qui a la plus grosse... prise !");
                                p.playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_FLOP, 1.0F, 1.0F);
                            }
                        }
                        user.setThreeBestFish(main.getCustomFish().threeBestFishManager(user, newFish, user.getBestFish()));
                    }
                }
                if (!userInFile) {
                    p.sendMessage(main.getPrefix() + "§6Tu obtiens ton premier poisson de la taille de "
                            + Colors.getYellow() + fishLength + "§6 cm !");
                    p.sendMessage(tip);
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);

                    LinkedList<Fish> threeBestFish = new LinkedList<>();
                    Fish firstFish = new Fish(fishLength, customFishIS);

                    threeBestFish.add(firstFish);

                    User newUser = new User(p.getUniqueId(), threeBestFish);
                    userList.add(newUser);
                }
                if (new Random().nextInt(45) == 28) {
                    p.sendMessage(tip);
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);
                }
                if (new Random().nextInt(40) == 28) {
                    p.sendMessage(main.getPrefix() + "Un §ebug§r ? Une §esuggestion §r? §d/suggestion <ta suggestion>§r !");
                }
                main.getFileUtils().serialize(userList);
            } else {
                p.sendMessage(main.getPrefix() + "§6Tu obtiens ton premier poisson de la taille de §e"
                        + fishLength + "§6 cm !");
                p.sendMessage(tip);
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);

                List<User> newUser = new ArrayList<>();
                LinkedList<Fish> threeBestFish = new LinkedList<>();
                threeBestFish.add(new Fish(fishLength, customFishIS));
                newUser.add(new User(p.getUniqueId(), threeBestFish));
                main.getFileUtils().serialize(newUser);
            }
            main.getLeaderboardManager().reloadLeaderboard();
        }
    }
}