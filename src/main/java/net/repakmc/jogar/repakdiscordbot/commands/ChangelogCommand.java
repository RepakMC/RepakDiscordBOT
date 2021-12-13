package net.repakmc.jogar.repakdiscordbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.repakmc.jogar.repakdiscordbot.RepakDiscordBOT;

public class ChangelogCommand extends ListenerAdapter {

    private final RepakDiscordBOT plugin;

    public ChangelogCommand(RepakDiscordBOT plugin) {
        this.plugin = plugin;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        // Make
    }

}
