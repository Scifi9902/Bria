package io.github.scifi9902.bria;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import io.github.scifi9902.bria.credits.CreditHandler;
import io.github.scifi9902.bria.credits.command.PayCommand;
import io.github.scifi9902.bria.credits.command.SetCreditsCommand;
import io.github.scifi9902.bria.database.MongoHandler;
import io.github.scifi9902.bria.handlers.HandlerManager;
import io.github.scifi9902.bria.handlers.IHandler;
import io.github.scifi9902.bria.kit.KitHandler;
import io.github.scifi9902.bria.kit.listener.KitListener;
import io.github.scifi9902.bria.profiles.ProfileHandler;
import io.github.scifi9902.bria.profiles.ProfileListener;
import io.github.scifi9902.bria.scoreboard.ScoreboardAdapter;
import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.Config;
import io.github.scifi9902.bria.utils.command.CommandHandler;
import io.github.thatkawaiisam.assemble.Assemble;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class BriaPlugin extends JavaPlugin {

    private Config config;

    private HandlerManager handlerManager;

    private CommandHandler commandHandler;

    private Gson gson;

    @Override
    public void onEnable() {
        this.config = new Config("config", this, this.getDataFolder().getAbsolutePath());

        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();

        this.commandHandler = new CommandHandler("bria", CC.chat("&cNo permission"));

        this.handlerManager = new HandlerManager();

        this.handlerManager.registerHandler(new MongoHandler(this.config.getString("mongo.host"), this.config.getInt("mongo.port"), this.config.getBoolean("mongo.auth"), this.config.getString("mongo.username"), this.config.getString("mongo.password"), this.config.getString("mongo.database")));
        this.handlerManager.registerHandler(new ProfileHandler(this));
        this.handlerManager.registerHandler(new CreditHandler(this));
        this.handlerManager.registerHandler(new KitHandler());

        new Assemble(this, new ScoreboardAdapter(this));

        this.registerCommands();
        this.registerListeners();
    }

    private void registerCommands() {
        Arrays.asList(new SetCreditsCommand(this), new PayCommand(this))
                .forEach(this.commandHandler::registerCommand);
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new ProfileListener(this), this);
        manager.registerEvents(new KitListener(this), this);
    }

    public void onDisable() {
        this.handlerManager.getHandlers().forEach(IHandler::unload);
    }
}
