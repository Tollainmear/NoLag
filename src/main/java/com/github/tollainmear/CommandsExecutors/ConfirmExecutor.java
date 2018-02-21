package com.github.tollainmear.CommandsExecutors;

import com.github.tollainmear.NoLag;
import com.github.tollainmear.Translator;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.manipulator.mutable.item.BlockItemData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Timer;

public class ConfirmExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Translator translator = NoLag.getInstance().getTranslator();
        //todo-确认添加到缓存黑名单上的对象，如果十秒内没有确认，自动清理缓存黑名单

        //is src a player?
        if (!(src instanceof Player)){
            src.sendMessage(translator.getText("message.CommandSource.error"));
            return CommandResult.empty();
        }
        //does player in tempConfirmMap?
        if (NoLag.getTempConfirmMap().containsKey(((Player)src).getName())){
            String BlockID = NoLag.getTempConfirmMap().get(((Player)src).getName());
            NoLag.getbBlackList().add(BlockID);
            src.sendMessage(Text.of().concat(Text.of(BlockID)).concat(translator.getText("add-bbl-done")));
            return CommandResult.success();
        }
        //nothing to confirmed
        src.sendMessage(translator.getText("noneToConfirmed"));
        return CommandResult.success();
    }
}
