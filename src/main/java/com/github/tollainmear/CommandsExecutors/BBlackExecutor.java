package com.github.tollainmear.CommandsExecutors;

import com.github.tollainmear.NoLag;
import com.github.tollainmear.Translator;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.Console;
import java.util.Optional;

public class BBlackExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Translator translator = NoLag.getInstance().getTranslator();
        //todo-修改并存储配置文件、configNode
        //获取后面的参数，当没有给出方块ID到时候，默认进行射线追踪，添加第一个非空气方块到缓存黑名单
        //添加前需要confirm确认
        if (src instanceof Player){
            Player player = (Player) src;
            BlockRay<World> blockRay = BlockRay.from(player)
                    .distanceLimit(20).build();
            while (blockRay.hasNext()) {
                BlockRayHit<World> blockRayHit = blockRay.next();
                Location<World> Location = blockRayHit.getLocation();
                BlockType targetBlock = Location.getBlockType();
                if (!(targetBlock.equals(BlockTypes.AIR))) {
                    String i = targetBlock.getId();
                    player.sendMessage(Text.of(i));
                }
            }
        }
        else{
            src.sendMessage(translator.getText("message.ConsoleSource.error"));
        }
        return CommandResult.success();
    }
}
