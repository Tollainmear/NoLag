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
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Plugin(id = "nolag",name = "NoLag",version = "1.0",authors = "Tollainmear")
public class NoLag {
    private static String version = "1.0";

    public static NoLag instance;
    private static String pluginName = "NoLag";

    private NoLagCommandManager nlCmdManager;
    private Translator translator;

    //强制清理模式，当检测到与指定生物/方块交互时，若此生物/方块在黑名单内，强制删除对应的生物/方块
    private boolean isForced;
    //是否开启自动区块卸载
    private boolean isAutoUnload;

    public static List<String> geteBlackList() {
        return eBlackList;
    }

    //禁止生成生物的黑名单（加载事件、生成事件、交互事件监听中删除指定生物）
    private static List<String> eBlackList;

    public static List<String> getbBlackList() {
        return bBlackList;
    }

    //强制清理的（方块）黑名单（同时禁止放置）
    private static List<String> bBlackList;
    //自动清理的周期
    private int interval;
    //针对于每个世界单区块怪物生成的上限
    private Map<String,Integer> spawnLimit;

    public static Map<String, String> getTempConfirmMap() {
        return tempConfirmMap;
    }

    private static Map<String,String> tempConfirmMap = new HashMap<>();

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

//    @Listener
//    public void onStart(GameStartingServerEvent event) throws IOException {
//        translator.checkUpdate();
//    }

//    @Listener
//    public void onStarted(GameStartedServerEvent event) throws IOException {
//        //todo-服务器开始之后，为清理事件开辟一个循环的多线程??Clear();
//    }

    private void Clear() {
        //todo-根据ConfigNode进行清理
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

    //todo-在生物生成/加载的时候，检测所在区块的实体数，超过数量之后禁止生成；是否为黑名单内的生物，如果是，禁止生成。
//    @Listener
//    public void onMobSpawn(SpawnEntityEvent event){
//
//    }

    //todo-在交互方块的时候，检测是否强制删除黑名单内的方块
 //   @Listener
  //   public void onInteractBlock(InteractBlockEvent event){
 //           if ()
 //   }

    //todo-在放置方块的时候，检测是否强制删除黑名单内的方块
//    @Listener
//    public void onPlaceBlock(ChangeBlockEvent$Place$Impl event){
//
//    }

    public void cfgInit() throws IOException {
        configNode = configLoader.load();
        if (configNode.getNode(pluginName).getNode("Language").isVirtual()) {
            configNode.getNode(pluginName).getNode("Language").setValue(Locale.getDefault().toString());
            translator = new Translator(this);
            configNode.getNode(pluginName).getNode("Language").setComment(translator.getstring("cfg.comment.Language"));
            translator.logInfo("cfg.notFound");
        } else translator = new Translator(this);

        bBlackList = configNode.getNode(pluginName).getNode("B-BlackList").getChildrenList().stream().map(CommentedConfigurationNode::getString).collect(Collectors.toList());
        eBlackList = configNode.getNode(pluginName).getNode("e-BlackList").getChildrenList().stream().map(CommentedConfigurationNode::getString).collect(Collectors.toList());

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
        //todo-加载方块黑名单
        //todo-加载生物黑名单
        //todo-加载不同世界的每区块生物上限
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

    //为控制台输出文件加上颜色
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
