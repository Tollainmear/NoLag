package com.github.tollainmear.CommandsExecutors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class EBlackExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //todo-修改并存储配置文件、configNode
        //获取后面的参数，当没有给出生物ID到时候，默认进行射线追踪，添加第一个追踪到的生物类型到缓存黑名单
        //添加前需要confirm确认
        return null;
    }
}
