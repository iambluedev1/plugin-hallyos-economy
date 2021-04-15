package fr.iambluedev.hallyos.economy.manager;

import fr.iambluedev.hallyos.economy.HallyosEconomy;
import fr.iambluedev.hallyos.economy.command.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private static final CommandManager instance;
    private static final Constructor<PluginCommand> PLUGIN_COMMAND_CONSTRUCTOR;
    private static final CommandMap COMMAND_MAP;

    private final List<AbstractCommand> commands;

    static {
        instance = new CommandManager();

        try {
            PLUGIN_COMMAND_CONSTRUCTOR = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            PLUGIN_COMMAND_CONSTRUCTOR.setAccessible(true);
            Field commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            COMMAND_MAP = (CommandMap) commandMap.get(Bukkit.getPluginManager());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private CommandManager() {
        this.commands = new ArrayList<AbstractCommand>();
    }

    public static CommandManager get() {
        return instance;
    }

    public void register(AbstractCommand toRegister) {
        try {
            PluginCommand command = PLUGIN_COMMAND_CONSTRUCTOR.newInstance(toRegister.getName(), HallyosEconomy.get());

            command.getAliases().addAll(toRegister.getCmd());
            command.setExecutor(new CommandExecutor() {
                public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
                    return CommandManager.get().onCommand(arg0, arg1, arg2, arg3);
                }
            });
            command.setTabCompleter(new TabCompleter() {
                public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
                    return CommandManager.get().onTabComplete(arg0, arg1, arg2, arg3);
                }
            });

            if (toRegister.getPermission() != null) {
                command.setPermission(toRegister.getPermission());
            }

            COMMAND_MAP.register(toRegister.getDescription(), command);

            this.commands.add(toRegister);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        for (AbstractCommand abstractCommand : commands) {
            if (!abstractCommand.cmd.contains(string.toLowerCase())) continue;
            if (arrstring.length == 0) {
                abstractCommand.handle(commandSender, Arrays.asList(arrstring));
                continue;
            }
            List<String> list = Arrays.asList(arrstring);
            abstractCommand.handle(commandSender, list);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        for (AbstractCommand abstractCommand : commands) {
            if (!abstractCommand.cmd.contains(alias.toLowerCase())) continue;
            if (args.length == 0) {
                return abstractCommand.onTabComplete(sender, Arrays.asList(args));
            }
            List<String> list = Arrays.asList(args);
            return abstractCommand.onTabComplete(sender, list);
        }
        return null;
    }
}
