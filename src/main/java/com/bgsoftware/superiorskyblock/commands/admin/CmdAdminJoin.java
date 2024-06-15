package com.bgsoftware.superiorskyblock.commands.admin;

import com.bgsoftware.common.annotations.Nullable;
import com.bgsoftware.common.collections.Lists;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.events.IslandJoinEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.commands.BaseCommand;
import com.bgsoftware.superiorskyblock.commands.IAdminIslandCommand;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.island.IslandUtils;
import com.bgsoftware.superiorskyblock.island.role.SPlayerRole;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CmdAdminJoin extends BaseCommand implements IAdminIslandCommand {

    @Override
    protected List<String> aliases() {
        return Lists.singleton("join");
    }

    @Override
    protected String permission() {
        return "superior.admin.join";
    }

    @Override
    protected String usage(java.util.Locale locale) {
        return "admin join <" +
                Message.COMMAND_ARGUMENT_PLAYER_NAME.getMessage(locale) + "/" +
                Message.COMMAND_ARGUMENT_ISLAND_NAME.getMessage(locale) + ">";
    }

    @Override
    protected String description(java.util.Locale locale) {
        return Message.COMMAND_DESCRIPTION_ADMIN_JOIN.getMessage(locale);
    }

    @Override
    public int getMinArgs() {
        return 3;
    }

    @Override
    public int getMaxArgs() {
        return 3;
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return false;
    }

    @Override
    public boolean supportMultipleIslands() {
        return false;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, CommandSender sender, @Nullable SuperiorPlayer targetPlayer, Island island, String[] args) {
        SuperiorPlayer superiorPlayer = plugin.getPlayers().getSuperiorPlayer(sender);

        if (superiorPlayer.getIsland() != null) {
            Message.ALREADY_IN_ISLAND.send(superiorPlayer);
            return;
        }

        if (!plugin.getEventsBus().callIslandJoinEvent(superiorPlayer, island, IslandJoinEvent.Cause.ADMIN))
            return;

        IslandUtils.sendMessage(island, Message.JOIN_ADMIN_ANNOUNCEMENT, Lists.emptyList(), superiorPlayer.getName());

        island.addMember(superiorPlayer, SPlayerRole.defaultRole());

        if (targetPlayer == null)
            Message.JOINED_ISLAND_NAME.send(superiorPlayer, island.getName());
        else
            Message.JOINED_ISLAND.send(superiorPlayer, targetPlayer.getName());

        if (plugin.getSettings().isTeleportOnJoin())
            superiorPlayer.teleport(island);
    }

}
