package com.cyphur.ccloggeruuid.listeners;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.structures.LoginObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {
   private String _logtemplate;
   private CCLoggerUUID _plugin;
   private LogWriter _logWriter;

   public LoginListener(CCLoggerUUID instance) {
      this._plugin = instance;
      this._logtemplate = instance.getTemplate();
      this._logWriter = instance.LogWriter();
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      LoginObject lo = new LoginObject(event, this._logtemplate);

      this._logWriter.WriteToMainFile(lo.format());
      this._logWriter.WriteToPlayerFile(lo.GetUUID(), lo.format());
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      LoginObject lo = new LoginObject(event, this._logtemplate);

      this._logWriter.WriteToMainFile(lo.format());
      this._logWriter.WriteToPlayerFile(lo.GetUUID(), lo.format());
   }
}
