package org.crypticcosmos.crypticbot;

import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onReady(ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final long guildId = event.getGuild().getIdLong();
        String prefix = Config.PREFIX;
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.REALZ_ID)) {
            event.getChannel().sendMessage("I am now shutting down.").queue();
            LOGGER.info("Shutting down");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());
            return;
        }

        String msg = event.getMessage().getContentRaw().toLowerCase();

        if (msg.equals("<@!863541557189541899>")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(0xd01212)
            .setTitle("CrypticBot Info!")
            .setFooter("Requested by: " + event.getMessage().getAuthor().getAsTag())
            .setTimestamp(Instant.now())
            .addField("Version", "1.1A", true)
            .addField("License", "[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)", true)
            .addField("Current Prefix: ", "`" + Config.PREFIX + "`", true)
            .addField("API", "[JDA](https://github.com/DV8FromTheWorld/JDA)", true)
            .addField("Code", "[Github](https://github.com/Cryptic-Cosmos/CrypticBot)", true);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }


}
