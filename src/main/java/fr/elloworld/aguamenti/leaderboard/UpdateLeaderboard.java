package fr.elloworld.aguamenti.leaderboard;

import fr.elloworld.aguamenti.Aguamenti;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UpdateLeaderboard implements Listener {

    private final Aguamenti main;

    public UpdateLeaderboard(Aguamenti main) {
        this.main = main;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        main.getLeaderboardManager().reloadLeaderboard();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        if (main.getLeaderboardManager().getPlayersSelfHolograms().isEmpty())
            return;

        for (Player p : main.getLeaderboardManager().getPlayersSelfHolograms().keySet()) {
            if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                main.getLeaderboardManager().getPlayersSelfHolograms().get(p).remove();
                break;
            }
        }
        main.getLeaderboardManager().reloadLeaderboard();
    }

    @EventHandler
    private void onPlayerFish(PlayerFishEvent e) {
        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            main.getLeaderboardManager().reloadLeaderboard();

        }
    }
}
