package net.repakmc.jogar.repakdiscordbot;

import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.repakmc.jogar.repakdiscordbot.commands.AnnounceCommand;
import net.repakmc.jogar.repakdiscordbot.commands.ChangelogCommand;
import net.repakmc.jogar.repakdiscordbot.commands.SuggestCommand;
import net.repakmc.jogar.repakdiscordbot.manager.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/*
* Created by mattnicee7
* github.com/mattnicee7
* for RepakMC server.
* */
public final class RepakDiscordBOT extends JavaPlugin {

    @Getter private JDA discordAPI;
    @Getter private CooldownManager cooldownManager;

    @Getter private String changelogChannelId;
    @Getter private String announceChannelId;
    @Getter private String commandsChannelId;
    @Getter private String suggestionsChannelId;

    @Getter private int suggestionId;

    @Override
    public void onEnable() {
        loadModules();
        initJDA(getConfig().getString("token"));
    }

    @Override
    public void onDisable() {
    }

    private void loadModules() {
        saveDefaultConfig();
        cooldownManager = new CooldownManager();
        changelogChannelId = getConfig().getString("changelogChannelId");
        announceChannelId = getConfig().getString("announceChannelId");
        commandsChannelId = getConfig().getString("commandsChannelId");
        suggestionsChannelId = getConfig().getString("suggestionsChannelId");
        suggestionId = getConfig().getInt("suggestionId");
    }

    @SneakyThrows
    private void initJDA(String token) {
        discordAPI = JDABuilder.createDefault(token)
                .disableCache(Arrays.asList(CacheFlag.values()))
                .setChunkingFilter(ChunkingFilter.ALL)
                .disableIntents(Arrays.asList(GatewayIntent.values()))
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new AnnounceCommand(this))
                .addEventListeners(new SuggestCommand(this))
                .addEventListeners(new ChangelogCommand(this))
                .build();
    }

    public void countSuggestion() {
        getConfig().set("suggestionId", getSuggestionId() + 1);
        saveConfig();
        suggestionId = getConfig().getInt("suggestionId");
    }
}
