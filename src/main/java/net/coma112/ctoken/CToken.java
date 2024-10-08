package net.coma112.ctoken;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import lombok.Getter;
import net.coma112.ctoken.config.Config;
import net.coma112.ctoken.database.AbstractDatabase;
import net.coma112.ctoken.database.MySQL;
import net.coma112.ctoken.database.SQLite;
import net.coma112.ctoken.enums.DatabaseType;
import net.coma112.ctoken.enums.LanguageType;
import net.coma112.ctoken.enums.keys.ConfigKeys;
import net.coma112.ctoken.hooks.PlaceholderAPI;
import net.coma112.ctoken.language.Language;
import net.coma112.ctoken.utils.TokenLogger;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

import static net.coma112.ctoken.utils.StartingUtils.*;

public final class CToken extends JavaPlugin {
    @Getter
    private static CToken instance;
    @Getter
    private static AbstractDatabase database;
    @Getter
    private Language language;
    @Getter
    private TaskScheduler scheduler;
    private Config config;

    @Override
    public void onLoad() {
        instance = this;
        scheduler = UniversalScheduler.getScheduler(this);

        checkVersion();
    }

    @Override
    public void onEnable() {
        checkVM();
        saveDefaultConfig();
        initializeComponents();
        initializeDatabaseManager();
        loadBasicFormatOverrides();
        registerListenersAndCommands();

        new PlaceholderAPI().register();
        new Metrics(this, 23062);
    }

    @Override
    public void onDisable() {
        if (getDatabase() != null) getDatabase().disconnect();
    }

    public Config getConfiguration() {
        return config;
    }

    private void initializeComponents() {
        config = new Config();

        saveResourceIfNotExists("locales/messages_en.yml");
        saveResourceIfNotExists("locales/messages_es.yml");

        language = new Language("messages_" + LanguageType.valueOf(ConfigKeys.LANGUAGE.getString()));
    }

    private void initializeDatabaseManager() {
        try {
            switch (DatabaseType.valueOf(ConfigKeys.DATABASE.getString())) {
                case MYSQL, mysql -> {
                    TokenLogger.info("### MySQL support found! Starting to initializing it... ###");
                    database = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
                    MySQL mysql = (MySQL) database;

                    mysql.createTable();
                    TokenLogger.info("### MySQL database has been successfully initialized! ###");
                }

                case SQLITE, sqlite -> {
                    TokenLogger.info("### SQLite support found! Starting to initializing it... ###");
                    database = new SQLite();
                    SQLite sqlite = (SQLite) database;

                    sqlite.createTable();
                    TokenLogger.info("### SQLite database has been successfully initialized! ###");
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            TokenLogger.error(exception.getMessage());
        }
    }
}
