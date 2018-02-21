package com.github.tollainmear.CommandsExecutors;

import com.github.tollainmear.NoLag;
import com.github.tollainmear.Translator;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

public class MainExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //todo-主要帮助菜单，显示提示性文字
        List<Text> list = new ArrayList<>();
        Translator translator = NoLag.getInstance().getTranslator();

        list.add(Text.of(TextColors.GREEN,"/nl interval",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.interval")));
        list.add(Text.of(TextColors.GREEN,"/nl clear",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.clear")));
        list.add(Text.of(TextColors.GREEN,"/nl unloadchunks",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.unloadChunks")));
        list.add(Text.of(TextColors.GREEN,"/nl limit",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.limit")));
        list.add(Text.of(TextColors.GREEN,"/nl eblack",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.eblack")));
        list.add(Text.of(TextColors.GREEN,"/nl bblack",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.bblack")));
        list.add(Text.of(TextColors.GREEN,"/nl version",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.version")));
        list.add(Text.of(TextColors.GREEN,"/nl reload",TextColors.GRAY," - ",TextColors.YELLOW,translator.getstring("Command.reload")));
        list.add(Text.of(TextColors.GREEN,translator.getstring("github")));

        PaginationList.builder()
                .title(Text.of(NoLag.getInstance().getPluginName()))
                .contents(list)
                .padding(Text.of(TextColors.GRAY,"="))
                .sendTo(src);
        return CommandResult.success();
    }
}
