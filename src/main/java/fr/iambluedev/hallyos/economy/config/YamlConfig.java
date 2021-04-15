package fr.iambluedev.hallyos.economy.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private String fileName;
    private File file;
    private FileConfiguration fileConfig;
    private boolean created;

    public YamlConfig(String fileName, File folder) {
        this.fileName = fileName;
        this.file = new File(folder + "/" + fileName + ".yml");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!file.exists()) {
            created = false;
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            created = true;
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public boolean save() {
        try {
            fileConfig.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public FileConfiguration getFileConfig() {
        return fileConfig;
    }

    public boolean isCreated() {
        return created;
    }
}
