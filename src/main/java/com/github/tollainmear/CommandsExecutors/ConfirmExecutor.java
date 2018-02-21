package com.github.tollainmear.CommandsExecutors;

import com.github.tollainmear.NoLag;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.item.BlockItemData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Timer;

public class ConfirmExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //todo-确认添加到缓存黑名单上的对象，如果十秒内没有确认，自动清理缓存黑名单
        if (!(src instanceof Player)){
            src.sendMessage(Text.of("Command Source should be a player!"));
            return CommandResult.empty();
        }
        if (NoLag.getTempConfirmMap().containsKey(((Player)src).getName())){
            String BlockID = NoLag.getTempConfirmMap().get(((Player)src).getName());
            NoLag.getbBlackList().add(BlockID);
            src.sendMessage(Text.of("Done!" +BlockID+"was added to the Block BlackList!"));
        }
        return CommandResult.success();
    }
}
