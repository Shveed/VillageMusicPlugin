package ru.shveed.vmp.handler;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.shveed.vmp.VillageMusicPlugin;
import ru.shveed.vmp.model.PlayerAction;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ServerEventHandler implements Listener {

    private static final String URL = "https://exideprod.com/api/bot_exide/telegram/message/markdown";

    private String TOKEN = "3c4fdae9-c588-494b-91cb-3441d2241195";

    private final HttpClient client;

    public ServerEventHandler(VillageMusicPlugin plugin) {
        client = HttpClients.createDefault();

        String configToken = plugin.getConfig().getString("discord-token");
        if (configToken != null) {
            TOKEN = configToken;
        }

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent event) {
        event.setJoinMessage(
                "Здарова, " + event.getPlayer().getName() +
                        "! Чувствуй себя как дома, закури калюмбасик, " +
                        "покушай курочки KFC и бахни певчанского =)"
        );

        sendPlayerAction(event.getPlayer().getName(), PlayerAction.JOIN);
    }

    @EventHandler
    public void onPlayerDisconnected(PlayerQuitEvent event) {
        sendPlayerAction(event.getPlayer().getName(), PlayerAction.DISCONNECT);
    }

    private void sendPlayerAction(String playerName, PlayerAction action) {
        try {
            StringEntity body;
            switch (action) {
                case JOIN:
                    body = new StringEntity("\"Minecraft: " + playerName + " врывается в коммунизм" + "\"", StandardCharsets.UTF_8);
                    break;
                case DISCONNECT:
                    body = new StringEntity("\"Minecraft: " + playerName + " ливает из коммунизма" + "\"", StandardCharsets.UTF_8);
                    break;
                default:
                    return;
            }

            HttpResponse response = client.execute(initHttpPost(body));
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    String responseBody = CharStreams.toString(
                            new InputStreamReader(instream, Charsets.UTF_8)
                    );
                    Bukkit.getLogger().warning("Done: " + responseBody);
                }
            }

            Bukkit.getLogger().warning("Welcome message: Successfully send (" + playerName + ")");
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
    }

    private HttpPost initHttpPost(StringEntity body) throws URISyntaxException {
        HttpPost post = new HttpPost(URL);
        post.setEntity(body);
        post.setURI(
                new URIBuilder(post.getURI())
                        .addParameter("token", TOKEN)
                        .build()
        );
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Content-Encoding", "UTF-8");
        return post;
    }
}
