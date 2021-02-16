package com.cyphur.ccloggeruuid;


import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.listeners.*;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class CCLoggerUUID extends JavaPlugin {
   private static CCLoggerUUID plugin;
   private FileConfiguration config;
   private LogWriter _logWriter;
   private boolean _perPlayerLog = true;
   private boolean _perDateLog = true;
   private boolean _CyphurChatConnected = false;
   private boolean _DiscordSRV = false;
   private String _template;
   private String _DiscordTemplate;
   private DiscordListener discordListener;

   public void onLoad(){
      plugin = this;
   }

   public void onEnable() {

      getLogger().info("------------------Loading CCLoggerUUID------------------");
      this.saveDefaultConfig();
      config = this.getConfig();

      //hook CyphurChatChannels
      if(Bukkit.getPluginManager().isPluginEnabled("CyphurChatChannels")){
         getLogger().info("Found CyphurChatChannels! Integrating...");
         _template = this.config.getString("hooks.CyphurChatChannels.template");
         _CyphurChatConnected = true;
      } else {
         getLogger().info("CyphurChatChannels hook failed.");
         _template = this.config.getString("log.template");
         _CyphurChatConnected = false;
      }

      //hook DiscordSRV
      _DiscordSRV = Bukkit.getPluginManager().isPluginEnabled("DiscordSRV");
      getLogger().info(_DiscordSRV ? "Found DiscordSRV! Integrating..." : "DiscordSRV not found.");
      this._DiscordTemplate = this.config.getString("hooks.DiscordSRV.template");


      //read config
      _perDateLog = this.config.getBoolean("log.perDateLog");
      _perPlayerLog = this.config.getBoolean("log.perplayerlog");
      getLogger().info("perDateLog == " + _perDateLog);
      getLogger().info("perPlayerLog == " + _perPlayerLog);
      getLogger().info( "template == " + _template);

      this._logWriter = new LogWriter(this);
      this.RegisterListeners();
      getLogger().info("Registered All Listeners");


      getLogger().info("------------------Loaded CCLoggerUUID------------------");
   }

   public void onDisable() {

      getLogger().info("------------------Disabling CCLoggerUUID------------------");

      HandlerList.unregisterAll(this);
      getLogger().info("Unregistered Bukkit Listeners");


      if (this.is_DiscordSRVConnected()) {
         this.discordListener = new DiscordListener(this);
         DiscordSRV.api.unsubscribe(this.discordListener);
         getLogger().info("Unriegistered DiscordSRV Listeners");
      }

      getLogger().info("------------------Disabled CCLoggerUUID------------------");

   }

   private void RegisterListeners() {
      this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
      this.getServer().getPluginManager().registerEvents(new LoginListener(this), this);
      this.getServer().getPluginManager().registerEvents(new DeathListener(this), this);
      this.getServer().getPluginManager().registerEvents(new CommandListener(this), this);
      if (this.is_DiscordSRVConnected()) {
         this.discordListener = new DiscordListener(this);
         DiscordSRV.api.subscribe(this.discordListener);
         getLogger().info("Registered DiscordSRV Listeners");
      }
   }

   public LogWriter LogWriter() {
      return this._logWriter;
   }

   public boolean isperPlayerLog() {
      return _perPlayerLog;
   }

   public boolean isperDateLog() {
      return _perDateLog;
   }

   public boolean isCyphurChatConnected() {
      return _CyphurChatConnected;
   }

   public boolean is_DiscordSRVConnected() {
      return _DiscordSRV;
   }

   public String getTemplate() {
      return _template;
   }

   public String getDiscordTemplate() {
      return _DiscordTemplate;
   }

   public static CCLoggerUUID getPlugin() {
      return plugin;
   }
}
