package fr.iambluedev.hallyos.economy.manager;

import fr.iambluedev.hallyos.economy.HallyosEconomy;
import fr.iambluedev.hallyos.economy.model.User;
import fr.iambluedev.hallyos.economy.repository.UserRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {

    private static final PlayerManager instance;
    private Map<String, User> users;
    private UserRepository repository;

    static {
        instance = new PlayerManager();
    }

    private PlayerManager() {
        this.users = new HashMap<String, User>();
        this.repository = new UserRepository();
        Bukkit.getServer().getPluginManager().registerEvents(this, HallyosEconomy.get());
    }

    public static PlayerManager get() {
        return instance;
    }

    public void init(Player player) {
        if (users.get(player.getName()) == null) {
            users.put(player.getName(), repository.get(player.getName()));
        }
    }

    public void init(String playerName) {
        if (users.get(playerName) == null) {
            users.put(playerName, repository.get(playerName));
        }
    }


    public User get(Player player) {
        return users.get(player.getName());
    }

    public User get(String name) {
        if (!users.containsKey(name)) {
            return null;
        }
        return users.get(name);
    }

    public void remove(User user) {
        users.remove(user.getLogin());
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!repository.exist(event.getPlayer().getName())) {
            User user = new User();
            user.setLogin(event.getPlayer().getName());
            user.setMoney(HallyosEconomy.get().getCustomConfig().getFileConfig().getInt("starter.amount"));
            user.setAt(System.currentTimeMillis() / 1000);
            repository.save(user);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        init(event.getPlayer());

        User user = get(event.getPlayer());

        if (user == null) {
            event.getPlayer().kickPlayer("Error when connecting...");
            return;
        }

        if (user.getMoney() < 0) {
            user.setMoney(0);
            repository.save(user);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        User user = get(event.getPlayer());
        if (user == null) return;

        remove(user);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = get(event.getPlayer());
        if (user == null) return;

        remove(user);
    }

}
