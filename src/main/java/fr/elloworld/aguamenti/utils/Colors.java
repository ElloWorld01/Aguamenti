package fr.elloworld.aguamenti.utils;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class Colors {

    //flatuicolors.com : Flat UI Palette v1

    @Getter
    public static final ChatColor darkRed = colorSystem(192, 57, 43);
    @Getter
    public static final ChatColor red = colorSystem(231, 76, 60);
    @Getter
    public static final ChatColor yellow = colorSystem(241, 196, 15);
    @Getter
    public static final ChatColor turquoise = colorSystem(26, 188, 156);
    @Getter
    public static final ChatColor white = colorSystem(236, 240, 241);

    private static ChatColor colorSystem(int r, int g, int b) {
        return ChatColor.of(new Color(r, g, b));
    }
}