package me.mical.ssrecovery.command;

import me.mical.ssrecovery.SSRecovery;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.LinkedList;

public class CommandHandler extends Command implements TabExecutor {
    /**
     * Construct a new command with no permissions or aliases.
     *
     * @param name the name of this command
     */
    public CommandHandler(String name) {
        super(name);
    }

    /**
     * Execute this command with the specified sender and arguments.
     *
     * @param sender the executor of this command
     * @param args   arguments used to invoke this command
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        final boolean adminPerm = sender.hasPermission("SSRecovery.admin");
        switch (args[0].toLowerCase()) {
            case "reload":
                if (adminPerm) {
                    SSRecovery.getInstance().getConfigManager().initialize();
                    sender.sendMessage(new TextComponent(ChatColor.GREEN + "重载插件配置文件成功."));
                    break;
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "权限不足."));
                }
            case "set":
                if (adminPerm) {
                    try {
                        String serverName = args[1];
                        boolean status = Boolean.parseBoolean(args[2]);
                        SSRecovery.getInstance().getConfigManager().setStatus(serverName, status);
                        sender.sendMessage(new TextComponent(ChatColor.GREEN + "修改成功."));
                    } catch (NumberFormatException exception) {
                        sender.sendMessage(new TextComponent(ChatColor.RED + "非法参数."));
                    }
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "权限不足."));
                }
                break;
            default:
                sender.sendMessage(new TextComponent(ChatColor.RED + "未知命令."));
                break;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        final LinkedList<String> dataMap = new LinkedList<>();
        if (args.length == 0) {
            dataMap.add(getName());
        } else {
            dataMap.add("reload");
            dataMap.add("set");
        }
        return dataMap;
    }
}
