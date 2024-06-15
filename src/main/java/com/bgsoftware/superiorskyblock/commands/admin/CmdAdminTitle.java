package com.bgsoftware.superiorskyblock.commands.admin;

import com.bgsoftware.common.collections.Lists;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.commands.BaseCommand;
import com.bgsoftware.superiorskyblock.commands.IAdminPlayerCommand;
import com.bgsoftware.superiorskyblock.commands.arguments.CommandArguments;
import com.bgsoftware.superiorskyblock.commands.arguments.NumberArgument;
import com.bgsoftware.superiorskyblock.core.formatting.Formatters;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class CmdAdminTitle extends BaseCommand implements IAdminPlayerCommand {

    @Override
    protected List<String> aliases() {
        return Lists.singleton("title");
    }

    @Override
    protected String permission() {
        return "superior.admin.title";
    }

    @Override
    protected String usage(java.util.Locale locale) {
        return "admin title <" +
                Message.COMMAND_ARGUMENT_PLAYER_NAME.getMessage(locale) + "> <" +
                Message.COMMAND_ARGUMENT_TITLE_FADE_IN.getMessage(locale) + "> <" +
                Message.COMMAND_ARGUMENT_TITLE_DURATION.getMessage(locale) + "> <" +
                Message.COMMAND_ARGUMENT_TITLE_FADE_OUT.getMessage(locale) + "> " +
                "-title [" + Message.COMMAND_ARGUMENT_MESSAGE.getMessage(locale) + "] " +
                "-subtitle [" + Message.COMMAND_ARGUMENT_MESSAGE.getMessage(locale) + "]";
    }

    @Override
    protected String description(java.util.Locale locale) {
        return Message.COMMAND_DESCRIPTION_ADMIN_TITLE.getMessage(locale);
    }

    @Override
    public int getMinArgs() {
        return 8;
    }

    @Override
    public int getMaxArgs() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return true;
    }

    @Override
    public boolean supportMultiplePlayers() {
        return false;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, CommandSender sender, SuperiorPlayer targetPlayer, String[] args) {
        if (!targetPlayer.isOnline()) {
            Message.PLAYER_NOT_ONLINE.send(sender);
            return;
        }

        NumberArgument<Integer> fadeIn = CommandArguments.getInterval(sender, args[3]);

        if (!fadeIn.isSucceed())
            return;

        NumberArgument<Integer> duration = CommandArguments.getInterval(sender, args[4]);

        if (!duration.isSucceed())
            return;

        NumberArgument<Integer> fadeOut = CommandArguments.getInterval(sender, args[5]);

        if (!fadeOut.isSucceed())
            return;

        Map<String, String> parsedArguments = CommandArguments.parseArguments(args);

        String title = parsedArguments.get("title");
        String subtitle = parsedArguments.get("subtitle");

        if (title == null && subtitle == null) {
            Message.INVALID_TITLE.send(sender);
            return;
        }

        plugin.getNMSPlayers().sendTitle(targetPlayer.asPlayer(),
                title == null ? null : Formatters.COLOR_FORMATTER.format(title),
                subtitle == null ? null : Formatters.COLOR_FORMATTER.format(subtitle),
                fadeIn.getNumber(),
                duration.getNumber(),
                fadeOut.getNumber());

        Message.TITLE_SENT.send(sender, targetPlayer.getName());
    }

}
