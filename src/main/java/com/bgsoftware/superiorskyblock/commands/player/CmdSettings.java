package com.bgsoftware.superiorskyblock.commands.player;

import com.bgsoftware.common.collections.Lists;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.commands.BaseCommand;
import com.bgsoftware.superiorskyblock.commands.IPermissibleCommand;
import com.bgsoftware.superiorskyblock.core.menu.view.MenuViewWrapper;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.island.privilege.IslandPrivileges;

import java.util.List;

public class CmdSettings extends BaseCommand implements IPermissibleCommand {

    @Override
    protected List<String> aliases() {
        return Lists.singleton("settings");
    }

    @Override
    protected String permission() {
        return "superior.island.settings";
    }

    @Override
    protected String usage(java.util.Locale locale) {
        return "settings";
    }

    @Override
    protected String description(java.util.Locale locale) {
        return Message.COMMAND_DESCRIPTION_SETTINGS.getMessage(locale);
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public int getMaxArgs() {
        return 1;
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return false;
    }

    @Override
    public IslandPrivilege getPrivilege() {
        return IslandPrivileges.SET_SETTINGS;
    }

    @Override
    public Message getPermissionLackMessage() {
        return Message.NO_SET_SETTINGS_PERMISSION;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, SuperiorPlayer superiorPlayer, Island island, String[] args) {
        plugin.getMenus().openSettings(superiorPlayer, MenuViewWrapper.fromView(superiorPlayer.getOpenedView()), island);
    }

}
