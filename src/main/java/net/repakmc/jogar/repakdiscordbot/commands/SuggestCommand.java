package net.repakmc.jogar.repakdiscordbot.commands;

import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.repakmc.jogar.repakdiscordbot.RepakDiscordBOT;
import net.repakmc.jogar.repakdiscordbot.util.TimeUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SuggestCommand extends ListenerAdapter {

    private final RepakDiscordBOT plugin;
    private final SimpleDateFormat simpleDateFormat;

    public SuggestCommand(RepakDiscordBOT plugin) {
        this.plugin = plugin;
        this.simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel().getId().equals(plugin.getCommandsChannelId())))
            return;

        val args = event.getMessage().getContentRaw().split(" ");

        if (args.length < 2)
            return;

        if (args[0].equalsIgnoreCase(".sugerir") && args[1] != null) {
            if (args[1].replace(" ", "").isEmpty())
                return;

            val user = event.getAuthor();
            val username = user.getName();

            val message = event.getMessage();
            val channel = event.getChannel();

            if (plugin.getCooldownManager().isInCooldown(username)) {
                message.delete().queue();

                channel.sendMessage( user.getAsMention() + ", você só pode enviar uma sugestão novamente daqui " + plugin.getCooldownManager().getRemainingTime(username) + ".")
                        .delay(30, TimeUnit.SECONDS)
                        .flatMap(Message::delete)
                        .queue();

                return;
            }

            val suggestion = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            val embedMessage = new EmbedBuilder()
                    .setTitle("<:repak512:759188335603351553> | Sugestão #%id% - RepakMC"
                            .replace("%id%", String.valueOf(plugin.getSuggestionId())))
                    .setColor(Color.YELLOW)
                    .setDescription(suggestion)
                    .setFooter("Sugerido por " + username + " - " + TimeUtils.getTimeStamp())
                    .build();

            plugin.countSuggestion();

            message.delete().queue();

            plugin.getDiscordAPI().getTextChannelById(plugin.getSuggestionsChannelId()).sendMessageEmbeds(embedMessage).queue(it -> {
                it.addReaction("U+2705").queueAfter(1, TimeUnit.SECONDS);
                it.addReaction("U+274C").queueAfter(2, TimeUnit.SECONDS);
            });

            plugin.getCooldownManager().add(username);
            channel.sendMessage(user.getAsMention() + ", sugestão enviada com sucesso.")
                    .delay(30, TimeUnit.SECONDS)
                    .flatMap(Message::delete)
                    .queue();
        }

    }

}
