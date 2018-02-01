package com.github.tollainmear;

import com.github.tollainmear.CommandsExecutors.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import javax.security.auth.callback.ConfirmationCallback;

public class NoLagCommandManager {
    private final NoLag plugin;
    private Translator translator;

    //������������
    private CommandSpec interval;
    //ɾ��ָ�������ָ�����͵�����
    private CommandSpec clear;
    //�ֶ�ж������
    private CommandSpec unloadChunks;
    //����ָ�����絥������������
    private CommandSpec limit;
    /*�༭����������û�и�����ʱ�򣬽�ָ�������/����������������Ҫȷ�ϣ�*/
    //���������
    private CommandSpec eblack;
    //���������
    private CommandSpec bblack;
    //ȷ�����ȱʡ����Եķ���
    private CommandSpec confirm;

    private CommandSpec reload;
    private CommandSpec version;

    public NoLagCommandManager(NoLag plugin) {
        this.plugin = plugin;

        interval = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".clear")
                .description(Text.of("Set the interval of automatic cleaning"))
                .arguments(GenericArguments.optional(GenericArguments.integer(Text.of("line"))))
                .executor(new IntervalExecutor())
                .build();

        clear = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".clear")
                .description(Text.of("clear the mobs with specific entity type"))
                .arguments(GenericArguments.none())
                .executor(new ClearExecutor())
                .build();

        unloadChunks = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".unloadChunks")
                .description(Text.of("unload the usless chunks Manually"))
                .arguments(GenericArguments.none())
                .executor(new UnloadChunksExecutor())
                .build();

        limit = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".limit")
                .description(Text.of("set the limit of mobs per chunk in specific world"))
                .arguments(GenericArguments.optional(GenericArguments.integer(Text.of("line"))))
                .executor(new LimitExecutor())
                .build();

        eblack = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".eblack")
                .description(Text.of("add the block to the block-blacklist"))
                .arguments(GenericArguments.seq(
                        GenericArguments.integer(Text.of("line")),
                        GenericArguments.integer(Text.of("another line"))
                ))
                .executor(new EBlackExecutor())
                .build();

        bblack = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".bblack")
                .description(Text.of("add the block to the block-blacklist"))
                .arguments(GenericArguments.none())
                .executor(new BBlackExecutor())
                .build();

        confirm = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".confirm")
                .description(Text.of("confirm the block/entity to add to the black list"))
                .arguments(GenericArguments.none())
                .executor(new ConfirmExecutor())
                .build();

        reload = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".reload")
                .description(Text.of("relaod the KSE."))
                .arguments(GenericArguments.none())
                .executor(new ReloadExecutor())
                .build();

        version = CommandSpec.builder()
                .permission(plugin.getPluginName() + ".version")
                .description(Text.of("Show the version of KSE"))
                .arguments(GenericArguments.none())
                .executor(new VersionExecutor())
                .build();


    }

    public void init(NoLag plugin) {
        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(plugin, this.get(), "KaroglanSignEditor", "KSE", "Sign", "signeditor", "Se");
        translator = this.plugin.getTranslator();
        translator.logInfo("reportBug");
        translator.logInfo("github");
    }

    public CommandCallable get() {
        return CommandSpec.builder()
                .description(Text.of("KSE's main command."))
                .child(interval, "time","t")
                .child(clear, "remove","c")
                .child(unloadChunks, "unload", "uc")
                .child(limit, "l")
                .child(eblack, "eb")
                .child(bblack, "bb")
                .child(version, "version", "ver", "v")
                .child(reload, "reload", "r")
                .executor(new MainExecutor())
                .arguments(GenericArguments.none())
                .build();
    }
}
