package fr.iambluedev.hallyos.economy.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class AbstractCommand {

    public List<String> cmd;
    public String name;
    public String description;
    public String permission;

    public AbstractCommand(String name, List<String> cmd) {
        this.cmd = cmd;
        this.name = name;
        this.description = "";
    }

    public AbstractCommand(String name, List<String> cmd, String description) {
        this.cmd = cmd;
        this.name = name;
        this.description = description;
    }

    public abstract void handle(CommandSender sender, List<String> args);

    public abstract void helpInfo(CommandSender player);

    public abstract List<String> onTabComplete(CommandSender sender, List<String> args);

    public List<String> getCmd() {
        return cmd;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }
}
