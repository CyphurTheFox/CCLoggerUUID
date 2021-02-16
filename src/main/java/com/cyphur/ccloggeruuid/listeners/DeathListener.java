package com.cyphur.ccloggeruuid.listeners;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.structures.DeathObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
   private String _logtemplate;
   private CCLoggerUUID _plugin;
   private LogWriter _logWriter;

   public DeathListener(CCLoggerUUID instance) {
      this._plugin = instance;
      this._logtemplate = instance.getTemplate();
      this._logWriter = instance.LogWriter();
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
      DeathObject deo = new DeathObject(event, this._logtemplate);

      this._logWriter.WriteToMainFile(deo.format());
      this._logWriter.WriteToPlayerFile(deo.GetUUID(), deo.format());
   }
}
