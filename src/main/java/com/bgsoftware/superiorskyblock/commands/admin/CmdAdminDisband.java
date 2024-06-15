package com.bgsoftware.superiorskyblock.commands.admin;

import com.bgsoftware.common.annotations.Nullable;
import com.bgsoftware.common.collections.Lists;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.commands.BaseCommand;
import com.bgsoftware.superiorskyblock.commands.IAdminIslandCommand;
import com.bgsoftware.superiorskyblock.core.formatting.Formatters;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.island.IslandUtils;
import com.bgsoftware.superiorskyblock.module.BuiltinModules;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.util.List;

public class CmdAdminDisband extends BaseCommand implements IAdminIslandCommand {

    @Override
    protected List<String> aliases() {
        return Lists.singleton("disband");
    }

    @Override
    protected String permission() {
        return "superior.admin.disband";
    }

    @Override
    protected String usage(java.util.Locale locale) {
        return "admin disband <" +
                Message.COMMAND_ARGUMENT_PLAYER_NAME.getMessage(locale) + "/" +
                Message.COMMAND_ARGUMENT_ISLAND_NAME.getMessage(locale) + ">";
    }

    @Override
    protected String description(java.util.Locale locale) {
        return Message.COMMAND_DESCRIPTION_ADMIN_DISBAND.getMessage(locale);
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
        return true;
    }

    @Override
    public boolean supportMultipleIslands() {
        return false;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, CommandSender sender, @Nullable SuperiorPlayer targetPlayer, Island island, String[] args) {
        if (plugin.getEventsBus().callIslandDisbandEvent(targetPlayer, island)) {
            IslandUtils.sendMessage(island, Message.DISBAND_ANNOUNCEMENT, Lists.emptyList(), sender.getName());

            if (targetPlayer == null)
                Message.DISBANDED_ISLAND_OTHER_NAME.send(sender, island.getName());
            else
                Message.DISBANDED_ISLAND_OTHER.send(sender, targetPlayer.getName());

            if (BuiltinModules.BANK.disbandRefund > 0) {
                Message.DISBAND_ISLAND_BALANCE_REFUND.send(island.getOwner(),
                        Formatters.NUMBER_FORMATTER.format(island.getIslandBank()
                                .getBalance().multiply(BigDecimal.valueOf(BuiltinModules.BANK.disbandRefund))));
            }

            island.disbandIsland();
        }
    }

}
