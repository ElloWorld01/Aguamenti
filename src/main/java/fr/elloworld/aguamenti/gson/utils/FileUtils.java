package fr.elloworld.aguamenti.gson.utils;

import com.google.common.reflect.TypeToken;
import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.gson.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private final Aguamenti main;

    public FileUtils(Aguamenti main) {
        this.main = main;
    }

    public void serialize(List<User> users) {

        String usersJson = main.getGson().toJson(users);
        write(usersJson);
        if (deserialize() != null)
            main.getLeaderboardManager().updateLeaderboard(users);
        else
            main.getLeaderboardManager().createLeaderboard();
    }

    public List<User> deserialize() {
        Type usersListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        try {
            return main.getGson().fromJson(new FileReader(main.getFileLeaderboard()), usersListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String json) {
        final FileWriter fw;

        try {
            fw = new FileWriter(main.getFileLeaderboard());

            fw.write(json);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}