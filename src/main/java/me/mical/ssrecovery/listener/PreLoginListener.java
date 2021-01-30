package me.mical.ssrecovery.listener;

import me.mical.ssrecovery.SSRecovery;
import me.mical.ssrecovery.config.ConfigManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {

    @EventHandler
    public void onConnect(ServerConnectedEvent event) {
        ProxiedPlayer user = event.getPlayer();
        String serverName = event.getServer().getInfo().getName();
        if (ConfigManager.getServerStatus().get(serverName) && !user.hasPermission("SSRecovery.bypass")) {
            user.disconnect(new TextComponent(SSRecovery.getInstance().getConfigManager().getConfig().getString("Message")));
        }
    }
}
