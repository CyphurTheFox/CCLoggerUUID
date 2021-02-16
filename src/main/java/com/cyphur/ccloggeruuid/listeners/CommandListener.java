package com.cyphur.ccloggeruuid.listeners;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.structures.CommandObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
   private String _logtemplate;
   private CCLoggerUUID _plugin;
   private LogWriter _logWriter;

   public CommandListener(CCLoggerUUID instance) {
      this._plugin = instance;
      this._logtemplate = instance.getTemplate();
      this._logWriter = instance.LogWriter();
   }

   @EventHandler
   public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
      CommandObject co = new CommandObject(event, this._logtemplate);

      this._logWriter.WriteToMainFile(co.format());
      this._logWriter.WriteToPlayerFile(co.GetUUID(), co.format());
   }
}
