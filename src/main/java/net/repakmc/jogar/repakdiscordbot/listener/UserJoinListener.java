package net.repakmc.jogar.repakdiscordbot.listener;

import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.repakmc.jogar.repakdiscordbot.RepakDiscordBOT;

public class UserJoinListener extends ListenerAdapter {

    private final RepakDiscordBOT plugin;

    public UserJoinListener(RepakDiscordBOT plugin) {
        this.plugin = plugin;
    }

    public void onMessageReceived(GuildMemberJoinEvent event) {
        val channel = event.getGuild().getTextChannelById(plugin.getWelcomeChannelId());

        val embedMessage = new EmbedBuilder()
                .build();

        channel.sendMessageEmbeds(embedMessage).queue();
    }

}
