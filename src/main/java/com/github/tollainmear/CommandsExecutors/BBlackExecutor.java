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
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.TabCompleteEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.Sponge;

import java.io.Console;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BBlackExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Translator translator = NoLag.getInstance().getTranslator();
        //todo-修改并存储配置文件、configNode
        //获取后面的参数，当没有给出方块ID到时候，默认进行射线追踪，添加第一个非空气方块到缓存黑名单
        //添加前需要confirm确认

        //Is Player?
        if (!(src instanceof Player)) {
            src.sendMessage(translator.getText("message.CommandSource.error"));
            return CommandResult.empty();
        }
        //RayTrace--
        else {
            Player player = (Player) src;
            Optional<String> blockID = args.getOne(Text.of("BlockID"));
            if (blockID.isPresent()) {
                NoLag.getbBlackList().add(blockID.get());
                player.sendMessage(Text.of(blockID.get()).concat(translator.getText("add-bbl-done")));
                return CommandResult.success();
            }
            BlockRay<World> blockRay = BlockRay.from(player)
                    .distanceLimit(6).build();
            while (blockRay.hasNext()) {
                BlockRayHit<World> blockRayHit = blockRay.next();
                Location<World> Location = blockRayHit.getLocation();
                BlockType targetBlock = Location.getBlockType();
                //capture the first block witch was not a AIR_Block
                if (!(targetBlock.equals(BlockTypes.AIR))) {
                    NoLag.getTempConfirmMap().put(player.getName(), targetBlock.getId());
                    player.sendMessage(Text.of(targetBlock.getId()).concat(translator.getText("add-bbl-temp")));

                    Sponge.getScheduler().createTaskBuilder()
                            .execute(() -> {
                                NoLag.getTempConfirmMap().remove(player.getName());
                            })
                            .delay(20, TimeUnit.SECONDS)
                            .submit(NoLag.getInstance());
                    return CommandResult.success();
                }
            }
            //if no block was founded return
            player.sendMessage(translator.getText("missingBlocks"));
            return CommandResult.empty();
        }
    }
}
