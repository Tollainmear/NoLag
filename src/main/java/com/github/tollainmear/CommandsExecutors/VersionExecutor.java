package com.github.tollainmear.CommandsExecutors;

import com.github.tollainmear.NoLag;
import com.github.tollainmear.Translator;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class VersionExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        //todo-向指令源发送版本信息
        Translator translator = NoLag.getInstance().getTranslator();
        Text i = translator.getText("version");
        src.sendMessage(i);
        return CommandResult.success();
    }
}
