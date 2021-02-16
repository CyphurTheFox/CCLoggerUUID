package com.cyphur.ccloggeruuid.listeners;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.structures.ChatObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
   private String _logtemplate;
   private CCLoggerUUID _plugin;
   private LogWriter _logWriter;

   public ChatListener(CCLoggerUUID instance) {
      this._plugin = instance;
      this._logtemplate = instance.getTemplate();

      this._logWriter = instance.LogWriter();

      if(this._logWriter == null){
         instance.getLogger().severe("Missing LogWriter!");
      }
   }

   @EventHandler
   public void onPlayerChat(AsyncPlayerChatEvent event) {
      ChatObject co = new ChatObject(event, this._logtemplate);

      this._logWriter.WriteToMainFile(co.format());
      this._logWriter.WriteToPlayerFile(co.GetUUID(), co.format());
   }
}
