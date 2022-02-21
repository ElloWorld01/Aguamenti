package fr.elloworld.aguamenti.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FishingTabCompletion implements TabCompleter {

    //Thanks to the great Lumin0u - https://github.com/lumin0u
    public static boolean startsLikely(String uncertain, String toFind) {
        if (uncertain == null || toFind == null || uncertain.length() > toFind.length())
            return false;

        uncertain = uncertain.toLowerCase(Locale.ROOT);
        toFind = toFind.toLowerCase(Locale.ROOT);

        for (int i = 0; i < uncertain.length(); i++) {
            if (uncertain.charAt(i) != toFind.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static List<String> filterSimilars(String uncertain, List<String> toFind) {
        return toFind.stream().filter(s -> startsLikely(uncertain, s)).collect(Collectors.toList());
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        switch (args.length) {
            case 1 -> {
                List<String> lead = new ArrayList<>();
                lead.add("leaderboard");
                lead.add("inventory");
                return filterSimilars(args[args.length - 1], lead);
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("leaderboard")) {

                    List<String> lead = new ArrayList<>();
                    lead.add("remove");
                    lead.add("create");
                    lead.add("reload");
                    lead.add("reset");
                    return filterSimilars(args[args.length - 1], lead);
                }
            }
        }
        return null;
    }
}