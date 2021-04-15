package fr.iambluedev.hallyos.economy.config;

import fr.iambluedev.hallyos.economy.HallyosEconomy;

public class Config extends YamlConfig {

    public Config(HallyosEconomy api) {
        super("economy", api.getDataFolder());
    }

    public void setupConfig() {
        if(!this.isCreated()){
            this.getFileConfig().set("mysql.type", "mariadb");
            this.getFileConfig().set("mysql.port", 3306);
            this.getFileConfig().set("mysql.dbname", "hallyos");
            this.getFileConfig().set("mysql.username", "root");
            this.getFileConfig().set("mysql.password", "");
            this.getFileConfig().set("mysql.host", "localhost");

            this.getFileConfig().set("starter.amount", 0);
            this.getFileConfig().set("transaction.alert", 100);

            this.save();
        }
    }
}
