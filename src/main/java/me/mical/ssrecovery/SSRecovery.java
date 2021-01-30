package me.mical.ssrecovery;

import me.mical.ssrecovery.command.CommandHandler;
import me.mical.ssrecovery.config.ConfigManager;
import me.mical.ssrecovery.listener.PreLoginListener;
import net.md_5.bungee.api.plugin.Plugin;

public final class SSRecovery extends Plugin {

    private static SSRecovery instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getLogger().info("正在加载 SSRecovery, 版本 " + getDescription().getVersion() + ".");
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                getLogger().info("未找到 数据文件夹/SSRecovery, 已自动生成.");
            }
        }
        configManager = new ConfigManager(this);
        configManager.initialize();
        getProxy().getPluginManager().registerListener(this, new PreLoginListener());
        getProxy().getPluginManager().registerCommand(this, new CommandHandler("ssrecovery"));
        getLogger().info("加载完成, 作者 Mical.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已卸载.");
    }

    public static SSRecovery getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
