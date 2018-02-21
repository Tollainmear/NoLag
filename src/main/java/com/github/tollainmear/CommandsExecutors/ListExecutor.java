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
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Translator translator = NoLag.getInstance().getTranslator();
        NoLag plugin = NoLag.getInstance();
        Optional<String> type = args.getOne(Text.of("BBL|EBL"));
        if (type.isPresent()){
            if (type.get().equals("BBL")){

                String Title = translator.getstring("title-bbl");
                List<Text> contents = new ArrayList<>();
                for (String BlockID : NoLag.getbBlackList()) {
                    contents.add(Text.of(BlockID));
                }
                PaginationList.Builder builder = PaginationList.builder();
                builder.title(Text.of(TextColors.GOLD, TextStyles.BOLD, Title))
                        .contents(contents)
                        .padding(Text.of(TextColors.YELLOW, "-"))
                        .sendTo(src);
                return CommandResult.success();

            }else if (type.get().equals("EBL")){

                String Title = translator.getstring("title-ebl");
                List<Text> contents = new ArrayList<>();
                for (String EntityID : NoLag.geteBlackList()) {
                    contents.add(Text.of(EntityID));
                }
                PaginationList.Builder builder = PaginationList.builder();
                builder.title(Text.of(TextColors.GOLD, TextStyles.BOLD, Title))
                        .contents(contents)
                        .padding(Text.of(TextColors.YELLOW, "-"))
                        .sendTo(src);
                return CommandResult.success();
            }
        }
            src.sendMessage(translator.getText("List.CouldntPharseArgs"));
            return CommandResult.empty();
        }
    }

