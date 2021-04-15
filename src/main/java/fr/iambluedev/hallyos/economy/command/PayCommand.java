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

public class PayCommand extends AbstractCommand {

    public PayCommand() {
        super("hpay", Collections.singletonList("hpay"), "Pay player");
        this.permission = "hallyos.economy.pay";
    }

    @Override
    public void handle(CommandSender sender, List<String> args) {
        if (!sender.hasPermission("hallyos.economy.pay") && !(sender instanceof Player)) {
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

        User toPlayerUser = PlayerManager.get().get(toPlayer);
        User senderUser = PlayerManager.get().get((Player) sender);

        if (toPlayerUser.getLogin().equals(senderUser.getLogin())) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_error_pay_yourself", ""));
            return;
        }

        if (senderUser.getMoney() <= amount) {
            sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_error_hasnt_money", ""));
            return;
        }

        UserRepository userRepository = new UserRepository();
        toPlayerUser.setMoney(toPlayerUser.getMoney() + amount);
        senderUser.setMoney(senderUser.getMoney() - amount);
        userRepository.save(toPlayerUser);
        userRepository.save(senderUser);

        toPlayer.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_receive_from_success", String.valueOf(amount), senderUser.getLogin()));
        sender.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_send_to_success", String.valueOf(amount), toPlayerUser.getLogin()));

        Transaction transaction = new Transaction();
        transaction.setSender(senderUser);
        transaction.setReceiver(toPlayerUser);
        transaction.setAt(System.currentTimeMillis() / 1000);
        transaction.setAmount(amount);
        transaction.setType(ETransactionType.PAY);
        new TransactionRepository().save(transaction);

        if (HallyosEconomy.get().getCustomConfig().getFileConfig().getInt("transaction.alert") <= amount) {
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("hallyos.economy.alert")) {
                    staff.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_alert", String.valueOf(amount), senderUser.getLogin(), toPlayerUser.getLogin()));
                }
            }
        }
    }

    @Override
    public void helpInfo(CommandSender player) {
        player.sendMessage(HallyosEconomy.get().getTranslator().translate(Lang.FRENCH, "economy_help_pay", ""));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return (args.size() == 1) ? null : Collections.emptyList();
    }

}
