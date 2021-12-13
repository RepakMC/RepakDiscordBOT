package net.repakmc.jogar.repakdiscordbot.commands;

import lombok.val;
import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.repakmc.jogar.repakdiscordbot.RepakDiscordBOT;
import net.repakmc.jogar.repakdiscordbot.util.TimeUtils;

import java.awt.*;
import java.util.Arrays;

public class ChangelogCommand extends ListenerAdapter {

    private final RepakDiscordBOT plugin;

    public ChangelogCommand(RepakDiscordBOT plugin) {
        this.plugin = plugin;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel().getId().equals(plugin.getChangelogChannelId())))
            return;

        val args = event.getMessage().getContentRaw().split(" ");

        if (args.length < 2)
            return;

        if (args[0].equalsIgnoreCase(".changelog") && !(args[1].isEmpty())){
            val channel = event.getChannel();
            val message = event.getMessage();

            message.delete().queue();

            val user = event.getAuthor();
            val username = user.getName();

            val changelogSplit = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).split(",");
            val changelogMessage = String.join("\n", Arrays.copyOfRange(changelogSplit, 0, changelogSplit.length));

            val embedMessage = new EmbedBuilder()
                    .setTitle(":wrench: | Changelog #%id%"
                            .replace("%id%", String.valueOf(plugin.getChangelogId())))
                    .setColor(Color.GRAY)
                    .setDescription(changelogMessage)
                    .setFooter("Changelog feita por " + username + " | " + TimeUtils.getTimeStamp())
                    .build();

            channel.sendMessageEmbeds(embedMessage).queue();

            plugin.getDiscordAPI().getTextChannelById(plugin.getDiscordLogChannelId())
                    .sendMessage("O usuário " + username + " enviou uma nova changelog. (#%id%)"
                            .replace("%id%", String.valueOf(plugin.getChangelogId()))).
                    queue();

            plugin.countChangelog();
        }

    }

}
