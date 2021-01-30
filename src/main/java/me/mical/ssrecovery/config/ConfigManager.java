package me.mical.ssrecovery.config;

import cc.summermc.bukkitYaml.file.YamlConfiguration;
import me.mical.ssrecovery.SSRecovery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private final SSRecovery plugin;

    private YamlConfiguration config;
    private static final HashMap<String, Boolean> serverStatus = new HashMap<>();

    public ConfigManager(SSRecovery plugin) {
        this.plugin = plugin;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void initialize() {
        final File dataFile = new File("./plugins/SSRecovery/", "config.yml");

        if (!dataFile.exists()) {
            try {
                if (dataFile.createNewFile()) {
                    config = YamlConfiguration.loadConfiguration(dataFile);
                    config.set("Message", "服务器正在维护中, 请耐心等待.");
                    plugin.getProxy().getServers().forEach((s, serverInfo) -> {
                        config.set("Servers." + s, false);
                        getServerStatus().put(s, false);
                    });
                    config.save(dataFile);
                    plugin.getLogger().info("未找到 主配置文件/config, 已自动生成.");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("加载 配置文件 时遇到错误(java.lang.IOException).");
                e.printStackTrace();
            }
        } else {
            config = YamlConfiguration.loadConfiguration(dataFile);
            plugin.getProxy().getServers().forEach((s, serverInfo) -> {
                final boolean status = config.getBoolean("Servers." + s);
                getServerStatus().put(s, status);
            });
            plugin.getLogger().info("已加载 主配置文件/config.");
        }
    }

    public void setStatus(String serverName, boolean status) {
        final File dataFile = new File("./plugins/SSRecovery/", "config.yml");
        getServerStatus().put(serverName, status);
        config.set("Servers." + serverName, status);
        try {
            config.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("加载 配置文件 时遇到错误(java.lang.IOException).");
            e.printStackTrace();
        }
    }

    public static HashMap<String, Boolean> getServerStatus() {
        return serverStatus;
    }
}
