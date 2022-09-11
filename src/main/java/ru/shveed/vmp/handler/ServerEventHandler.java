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
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.shveed.vmp.CustomConfig;
import ru.shveed.vmp.VillageMusicPlugin;
import ru.shveed.vmp.model.PlayerAction;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ServerEventHandler implements Listener {

    private static final String CONFIG_URL_PARAM = "url";
    private static final String CONFIG_TOKEN_PARAM = "token";

    private String URL = "";
    private String TOKEN = "";

    private final HttpClient client;

    public ServerEventHandler(VillageMusicPlugin plugin, FileConfiguration config) {
        client = HttpClients.createDefault();

        String configUrl = CustomConfig.get().getString(CONFIG_URL_PARAM);
        String configToken = CustomConfig.get().getString(CONFIG_TOKEN_PARAM);

        if (configUrl != null) {
            URL = configUrl;
            Bukkit.getLogger().warning("ExeLS: Configuration URL loaded");
        }
        if (configToken != null) {
            TOKEN = configToken;
            Bukkit.getLogger().warning("ExeLS: Configuration TOKEN loaded");
        }

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        event.setJoinMessage(
                "Здарова, " + playerName + "! Чувствуй себя как дома, закури " +
                        "калюмбасик, покушай курочки KFC и бахни певчанского =)"
        );

        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1);
        sendPlayerAction(playerName, PlayerAction.JOIN);
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

            Bukkit.getLogger().warning("ExeLS: Sending POST request...");

            HttpResponse response = client.execute(initHttpPost(URL, body));
            HttpEntity entity = response.getEntity();

            Bukkit.getLogger().warning("ExeLS: Code: " +
                    response.getStatusLine().getStatusCode() +
                    ";\n Message:" + response.getStatusLine().getReasonPhrase());

            handleEntity(entity);
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
    }

    private void handleEntity(HttpEntity entity) {
        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                String responseBody = CharStreams.toString(new InputStreamReader(instream, Charsets.UTF_8));
                Bukkit.getLogger().warning("ExeLS: Response: " + responseBody);
            } catch (Exception e) {
                Bukkit.getLogger().warning(e.getMessage());
            }
        } else {
            Bukkit.getLogger().warning("ExeLS: Response entity is null");
        }
    }

    private HttpPost initHttpPost(String url, StringEntity body) throws URISyntaxException {
        HttpPost post = new HttpPost(url);
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
