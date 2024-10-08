package net.coma112.ctoken.database;

import net.coma112.ctoken.manager.TokenTop;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDatabase {
    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract void createTable();

    public abstract void createPlayer(@NotNull OfflinePlayer player);

    public abstract void enablePay(@NotNull OfflinePlayer player);

    public abstract void disablePay(@NotNull OfflinePlayer player);

    public abstract boolean getPayStatus(@NotNull OfflinePlayer player);

    public abstract List<String> getPlayersFromDatabase();

    public abstract void changeMinimumPay(@NotNull OfflinePlayer player, int amount);

    public abstract int getMinimumPay(@NotNull OfflinePlayer player);

    public abstract boolean exists(@NotNull OfflinePlayer player);

    public abstract int getBalance(@NotNull OfflinePlayer player);

    public abstract int getXP(@NotNull OfflinePlayer player);

    public abstract List<TokenTop> getTop(int number);

    public abstract String getTopPlayer(int top);

    public abstract int getTopBalance(int top);

    public abstract int getTopPlace(@NotNull OfflinePlayer player);

    public abstract void setBalance(@NotNull OfflinePlayer player, int newBalance);

    public abstract void addToBalance(@NotNull OfflinePlayer player, int newBalance);

    public abstract void addToEveryoneBalance(int newBalance);

    public abstract void resetBalance(@NotNull OfflinePlayer player);

    public abstract void resetEveryone();

    public abstract void takeFromBalance(@NotNull OfflinePlayer player, int newBalance);

    public abstract int calculateXPFromTokens(int tokenBalance);

    public abstract void changePayStatus(@NotNull OfflinePlayer player);
}
