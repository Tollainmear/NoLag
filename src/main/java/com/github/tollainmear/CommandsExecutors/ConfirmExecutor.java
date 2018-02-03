package com.github.tollainmear.CommandsExecutors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class ConfirmExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //todo-确认添加到缓存黑名单上的对象，如果十秒内没有确认，自动清理缓存黑名单
        return null;
    }
}
