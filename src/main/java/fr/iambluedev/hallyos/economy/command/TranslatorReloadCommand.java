package fr.iambluedev.hallyos.economy.command;

import fr.iambluedev.hallyos.economy.HallyosEconomy;
import fr.iambluedev.hallyos.economy.i18n.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TranslatorReloadCommand extends AbstractCommand {

    public TranslatorReloadCommand() {
        super("reloadi18n", Collections.singletonList("reloadi18n"), "Reload translation's cache");
    }

    @Override
    public void handle(CommandSender sender, List<String> args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            if ((player.isOp()) || (player.hasPermission("hallyos.i18n.reload"))) {
                HallyosEconomy.get().getTranslator().clearCache();
                player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "i18n_reload_success", ""));
            } else {
                player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "i18n_cant_reload", ""));
            }
        }
    }

    @Override
    public void helpInfo(CommandSender player) {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return null;
    }

}
