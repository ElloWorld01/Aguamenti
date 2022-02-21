package fr.elloworld.aguamenti.command;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandFishing implements CommandExecutor {

    private final Aguamenti main;

    public CommandFishing(Aguamenti main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("fishing"))
            return false;
        if (sender instanceof Player p) {
            if (args.length >= 2 && args[0].equalsIgnoreCase("leaderboard")) {
                if (!p.isOp()) {
                    p.sendMessage(main.getPrefix() + "§cTu n'es pas OP, tu n'as pas accès à cette commande.");
                    return false;
                } else {
                    switch (args[1]) {
                        case "remove" -> {
                            main.getLeaderboardManager().removeLeaderboard();
                            p.sendMessage(main.getPrefix() + Colors.getRed() + "Leaderboard supprimé !");
                        }
                        case "create" -> {
                            main.getLeaderboardManager().createLeaderboard();
                            p.sendMessage(main.getPrefix() + Colors.getRed() + "Leaderboard créé !");
                        }
                        case "reload" -> {
                            main.getLeaderboardManager().reloadLeaderboard();
                            p.sendMessage(main.getPrefix() + Colors.getRed() + "Leaderboard mis à jour !");
                        }
                        case "reset" -> {
                            main.resetFileLeaderboard();
                            main.getLeaderboardManager().reloadLeaderboard();
                            p.sendMessage(main.getPrefix() + Colors.getRed() + "Leaderboard réinitialisé !");
                        }
                        default -> p.sendMessage(main.getPrefix() + "§cErreur, essayez /help fishing.");
                    }
                }
            } else if (args[0].equalsIgnoreCase("inventory")) {
                main.getFisherInv().openInventory(p);
            } else {
                p.sendMessage(main.getPrefix() + "§cErreur, essayez /help fishing.");
            }
            return true;
        }
        return false;
    }
}
