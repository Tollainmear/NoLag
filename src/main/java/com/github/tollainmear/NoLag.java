package com.github.tollainmear;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.manipulator.mutable.item.BlockItemData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.gen.ChangeBlockEvent$Place$Impl;
import org.spongepowered.api.event.gen.SpawnEntityEvent$ChunkLoad$Impl;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Plugin(id = "nolag",name = "NoLag",version = "1.0",authors = "Tollainmear")
public class NoLag {
    private static String version = "1.0";

    public static NoLag instance;
    private static String pluginName = "NoLag";

    private NoLagCommandManager nlCmdManager;
    private Translator translator;

    //ǿ������ģʽ������⵽��ָ������/���齻��ʱ����������/�����ں������ڣ�ǿ��ɾ����Ӧ������/����
    private boolean isForced;
    //�Ƿ����Զ�����ж��
    private boolean isAutoUnload;
    //��ֹ��������ĺ������������¼��������¼��������¼�������ɾ��ָ�����
    private List<Entity> eBlackList;
    //ǿ������ģ����飩��������ͬʱ��ֹ���ã�
    private List<BlockItemData> bBlackList;
    //�Զ����������
    private int interval;
    //�����ÿ�����絥����������ɵ�����
    private Map<String,Integer> spawnLimit;

    private CommentedConfigurationNode configNode;

    @Inject
    @DefaultConfig(sharedRoot = false)
    ConfigurationLoader<CommentedConfigurationNode> configLoader;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;

    @Inject
    private Logger logger;

    public void setNlCmdManager(NoLagCommandManager nlCmdManager) {
        this.nlCmdManager = nlCmdManager;
    }

    @Listener
    public void onPreInit(GamePostInitializationEvent event) throws IOException {
        instance = this;
        cfgInit();
        nlCmdManager = new NoLagCommandManager(this);
        nlCmdManager.init(this);
    }

    @Listener
    public void onStart(GameStartingServerEvent event) throws IOException {
        translator.checkUpdate();
    }

    @Listener
    public void onStarted(GameStartedServerEvent event) throws IOException {
        //todo-��������ʼ֮��Ϊ�����¼�����һ��ѭ���Ķ��߳�??Clear();
    }

    private void Clear() {
        //todo-����ConfigNode��������
    }

    @Listener
    public void onReload(GameReloadEvent event){
        MessageReceiver src =event.getCause().first(CommandSource.class).orElse(Sponge.getServer().getConsole());
        try {
            cfgInit();
            translator =new Translator(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(translator.getstring("message.KSEprefix")+translator.getstring("message.reload")));
    }

    //todo-����������/���ص�ʱ�򣬼�����������ʵ��������������֮���ֹ���ɣ��Ƿ�Ϊ�������ڵ��������ǣ���ֹ���ɡ�
    @Listener
    public void onMobSpawn(SpawnEntityEvent event){

    }

    //todo-�ڽ��������ʱ�򣬼���Ƿ�ǿ��ɾ���������ڵķ���
    @Listener
    public void onInteractBlock(InteractBlockEvent event){

    }

    //todo-�ڷ��÷����ʱ�򣬼���Ƿ�ǿ��ɾ���������ڵķ���
    @Listener
    public void onPlaceBlock(ChangeBlockEvent$Place$Impl event){

    }

    public void cfgInit() throws IOException {
        configNode = configLoader.load();
        if (configNode.getNode(pluginName).getNode("Language").isVirtual()) {
            configNode.getNode(pluginName).getNode("Language").setValue(Locale.getDefault().toString());
            translator = new Translator(this);
            configNode.getNode(pluginName).getNode("Language").setValue(Locale.getDefault().toString())
                    .setComment(translator.getstring("cfg.comment.Language"));
            translator.logInfo("cfg.notFound");
        } else translator = new Translator(this);


        if (configNode.getNode(pluginName).getNode("AutoUnload").isVirtual()) {
            configNode.getNode(pluginName).getNode("AutoUnload").setValue(true);
        }
        if (configNode.getNode(pluginName).getNode("interval").isVirtual()) {
            configNode.getNode(pluginName).getNode("interval").setValue("10")
                    .setComment(translator.getstring("cfg.comment.interval"));
        }
        if (configNode.getNode(pluginName).getNode("ForceMode").isVirtual()) {
            configNode.getNode(pluginName).getNode("ForceMode").setValue(true)
                    .setComment(translator.getstring("cfg.comment.forcemode"));
        }
        //todo-���ط��������
        //todo-�������������
        //todo-���ز�ͬ�����ÿ������������
        configLoader.save (configNode);
    }

    public static NoLag getInstance() {
        return instance;
    }

    public static String getVersion() {
        return version;
    }

    public Translator getTranslator() {
        return translator;
    }

    //Ϊ����̨����ļ�������ɫ
    public void log(String str) {
        logger.info("\033[36m" + str);
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public Logger getLogger() {
        return logger;
    }

    public CommentedConfigurationNode getConfigNode() {
        return configNode;
    }

    public Path getConfigPath() {
        return configPath;
    }

    public String getPluginName() {
        return pluginName;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigLoader() {
        return configLoader;
    }
}
