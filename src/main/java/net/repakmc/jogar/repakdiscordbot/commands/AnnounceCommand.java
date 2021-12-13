package net.repakmc.jogar.repakdiscordbot.commands;

import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.repakmc.jogar.repakdiscordbot.RepakDiscordBOT;
import net.repakmc.jogar.repakdiscordbot.util.TimeUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AnnounceCommand extends ListenerAdapter {

    private final RepakDiscordBOT plugin;

    public AnnounceCommand(RepakDiscordBOT plugin) {
        this.plugin = plugin;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel().getId().equals(plugin.getAnnounceChannelId())))
            return;

        val args = event.getMessage().getContentRaw().split(" ");

        if (args.length < 2)
            return;

        if (args[0].equalsIgnoreCase("repak.anunciar") && !(args[1].isEmpty())) {
            val channel = event.getChannel();
            val message = event.getMessage();

            val announce = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            val embedMessage = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setTitle("<:repak512:759188335603351553> Anúncio - RepakMC")
                    .setDescription(announce)
                    .setFooter("    Anuncio por " + event.getAuthor().getName() + " | " + TimeUtils.getTimeStamp())
                    .build();

            channel.sendMessageEmbeds(embedMessage).queue();

            message.delete().queue();
            channel.sendMessage("@everyone").flatMap(Message::delete).queueAfter(2, TimeUnit.SECONDS);
        }

    }

}
