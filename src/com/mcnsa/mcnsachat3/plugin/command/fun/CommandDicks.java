package com.mcnsa.mcnsachat3.plugin.command.fun;

import org.bukkit.entity.Player;

import com.mcnsa.mcnsachat3.chat.ChatChannel;
import com.mcnsa.mcnsachat3.chat.ChatPlayer;
import com.mcnsa.mcnsachat3.managers.ChannelManager;
import com.mcnsa.mcnsachat3.managers.PlayerManager;
import com.mcnsa.mcnsachat3.packets.PlayerChatPacket;
import com.mcnsa.mcnsachat3.plugin.MCNSAChat3;
import com.mcnsa.mcnsachat3.plugin.PluginUtil;
import com.mcnsa.mcnsachat3.plugin.command.Command;

@Command.CommandInfo(alias = "dicks", permission = "fun", usage = "", description = "")
public class CommandDicks implements Command {
	public static MCNSAChat3 plugin = null;

	public CommandDicks(MCNSAChat3 plugin) {
		CommandDicks.plugin = plugin;
	}

	public Boolean handle(Player player, String sArgs) {
		ChatPlayer p = PlayerManager.getPlayer(player.getName(), plugin.name);
		String write_perm = ChannelManager.getChannel(p.channel).write_permission;
		if (!write_perm.equals("") && !MCNSAChat3.permissions.has(player, "mcnsachat3.write." + write_perm)) {
			plugin.getLogger().info(player.getName() + " attempted to write to channel " + p.channel + " without permission!");
			PluginUtil.send(player.getName(), "&cYou don't have permission to do that!");
			return true;
		}
		if (p.modes.contains(ChatPlayer.Mode.MUTE) || ChannelManager.getChannel(p.channel).modes.contains(ChatChannel.Mode.MUTE)) {
			PluginUtil.send(p.name, "You are not allowed to speak right now.");
			return true;
		}
		
		String message = plugin.getConfig().getString("command-dicks");
		message = message.replace("%player%", p.name);
		if (MCNSAChat3.thread != null)
			MCNSAChat3.thread.write(new PlayerChatPacket(p, message, null, PlayerChatPacket.Type.CHAT));

		plugin.chat.chat(p, message, null);
		return true;
	}
}
