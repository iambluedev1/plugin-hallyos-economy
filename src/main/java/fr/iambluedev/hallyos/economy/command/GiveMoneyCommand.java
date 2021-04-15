package fr.iambluedev.hallyos.economy.command;

import fr.iambluedev.hallyos.economy.HallyosEconomy;
import fr.iambluedev.hallyos.economy.i18n.Lang;
import fr.iambluedev.hallyos.economy.manager.PlayerManager;
import fr.iambluedev.hallyos.economy.model.ETransactionType;
import fr.iambluedev.hallyos.economy.model.Transaction;
import fr.iambluedev.hallyos.economy.model.User;
import fr.iambluedev.hallyos.economy.repository.TransactionRepository;
import fr.iambluedev.hallyos.economy.repository.UserRepository;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GiveMoneyCommand extends AbstractCommand {

    public GiveMoneyCommand() {
        super("hgive", Collections.singletonList("hgive"), "Give money to player");
        this.permission = "hallyos.economy.give";
    }

    @Override
    public void handle(CommandSender sender, List<String> args) {
        if (!sender.hasPermission("hallyos.economy.give")) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "global_not_allowed", ""));
            return;
        }

        if (args.size() != 2) {
            this.helpInfo(sender);
            return;
        }

        Player toPlayer = Bukkit.getPlayerExact(args.get(0));
        Integer amount = 0;

        try {
            amount = Integer.valueOf(args.get(1));

            if (amount < 0) {
                sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_error_amount", ""));
                return;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_error_amount", ""));
            return;
        }

        if (toPlayer == null) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "global_not_online", ""));
            return;
        }

        User user = PlayerManager.get().get(toPlayer);
        user.setMoney(user.getMoney() + amount);
        new UserRepository().save(user);

        toPlayer.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_receive_success", String.valueOf(amount)));
        sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_send_success", String.valueOf(amount), user.getLogin()));

        User senderUser = null;
        if (sender instanceof Player) {
            senderUser = PlayerManager.get().get((Player) sender);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAt(System.currentTimeMillis() / 1000);
        transaction.setReceiver(user);
        transaction.setSender(senderUser);
        transaction.setType(ETransactionType.GIVE);
        new TransactionRepository().save(transaction);

        if (HallyosEconomy.get().getCustomConfig().getFileConfig().getInt("transaction.alert") <= amount) {
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("hallyos.economy.alert")) {
                    staff.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_alert", String.valueOf(amount), sender.getName(), user.getLogin()));
                }
            }
        }
    }

    @Override
    public void helpInfo(CommandSender player) {
        player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_help_give", ""));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return (args.size() == 1) ? null : Collections.emptyList();
    }

}
