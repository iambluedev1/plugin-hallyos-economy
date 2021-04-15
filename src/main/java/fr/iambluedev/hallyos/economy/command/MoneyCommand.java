package fr.iambluedev.hallyos.economy.command;

import fr.iambluedev.hallyos.economy.HallyosEconomy;
import fr.iambluedev.hallyos.economy.i18n.Lang;
import fr.iambluedev.hallyos.economy.manager.PlayerManager;
import fr.iambluedev.hallyos.economy.model.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MoneyCommand extends AbstractCommand {

    public MoneyCommand() {
        super("hmoney", Collections.singletonList("hmoney"), "View player's balance");
        this.permission = "hallyos.economy.money";
    }

    @Override
    public void handle(CommandSender sender, List<String> args) {
        if (!sender.hasPermission("hallyos.economy.money") && !(sender instanceof Player)) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "global_not_allowed", ""));
            return;
        }

        if (sender.hasPermission("hallyos.economy.money") && args.size() == 0) {
            User user = PlayerManager.get().get((Player) sender);
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_view_balance", String.valueOf(user.getMoney())));
        } else if (sender.hasPermission("hallyos.economy.money.other") && args.size() == 1) {
            Player toPlayer = Bukkit.getPlayerExact(args.get(0));

            if (toPlayer == null) {
                sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "global_not_online", ""));
                return;
            }

            User user = PlayerManager.get().get(toPlayer);
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_view_balance_of", String.valueOf(user.getMoney()), user.getLogin()));
        } else {
            this.helpInfo(sender);
            return;
        }
    }

    @Override
    public void helpInfo(CommandSender player) {
        if (player.hasPermission("hallyos.economy.money.other")) {
            player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_help_money_2", ""));
        } else {
            player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_help_money_1", ""));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return null;
    }
}
