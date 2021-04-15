package fr.iambluedev.hallyos.economy;

import fr.iambluedev.hallyos.economy.command.*;
import fr.iambluedev.hallyos.economy.config.Config;
import fr.iambluedev.hallyos.economy.i18n.Lang;
import fr.iambluedev.hallyos.economy.i18n.Translator;
import fr.iambluedev.hallyos.economy.manager.CommandManager;
import fr.iambluedev.hallyos.economy.manager.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.anana.pine.core.connector.MariaDBConnector;
import xyz.anana.pine.core.connector.MySQLConnector;
import xyz.anana.pine.core.database.Database;

public class HallyosEconomy extends JavaPlugin {

    private static HallyosEconomy instance;
    private Translator translator;
    private Config customConfig;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        instance = this;
        customConfig = new Config(this);
        customConfig.setupConfig();

        translator = new Translator(Lang.FRENCH);

        Database database = new Database(customConfig.getFileConfig().getString("mysql.host"),
                customConfig.getFileConfig().getString("mysql.dbname"),
                customConfig.getFileConfig().getString("mysql.username"),
                customConfig.getFileConfig().getString("mysql.password"),
                customConfig.getFileConfig().getInt("mysql.port"),
                ((customConfig.getFileConfig().getString("mysql.type").equals("mariadb"))
                        ? new MariaDBConnector() : new MySQLConnector()));


        System.out.println("Testing connection");
        database.connect();
        database.close();

        CommandManager.get().register(new GiveMoneyCommand());
        CommandManager.get().register(new PayCommand());
        CommandManager.get().register(new MoneyCommand());
        CommandManager.get().register(new RemoveMoneyCommand());
        CommandManager.get().register(new TranslatorReloadCommand());
        playerManager = PlayerManager.get();
    }

    public static HallyosEconomy get() {
        return instance;
    }

    public Translator getTranslator() {
        return translator;
    }

    public Config getCustomConfig() {
        return customConfig;
    }
}
