package com.cyphur.ccloggeruuid.structures;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.cyphurchatchannels.PlayerSession;
import com.cyphur.cyphurchatchannels.channel.Channel;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;

public class ChatObject {
   private String _chattemplate;
   private Player _player;
   private String _message;
   private Location _location;
   private String _ipaddress;
   private Date _log_date;
   private Channel _channel;

   public ChatObject(AsyncPlayerChatEvent event, String template) {
      this._player = event.getPlayer();
      this._message = event.getMessage();
      this._location = this._player.getLocation();
      this._ipaddress = this._player.getAddress().getAddress().getHostAddress();
      this._log_date = new Date();
      this._chattemplate = template;
      if(CCLoggerUUID.getPlugin().isCyphurChatConnected()){
         _channel = PlayerSession.getSession(event.getPlayer()).getChannel();
      } else {
         _channel = null;
      }
   }

   public String format() {
      String template = this._chattemplate;
      template = template.replaceAll("%ip", this._ipaddress);
      template = template.replaceAll("%date", this._log_date.toString());
      template = template.replaceAll("%world", this._location.getWorld().getName());
      template = template.replaceAll("%x", Integer.toString((int)this._location.getX()));
      template = template.replaceAll("%y", Integer.toString((int)this._location.getY()));
      template = template.replaceAll("%z", Integer.toString((int)this._location.getZ()));
      template = template.replaceAll("%name", this._player.getName());
      template = template.replaceAll("%content", Matcher.quoteReplacement(this._message));
      if(_channel != null) {
         template = template.replaceAll("%channel", _channel.getID());
      }
      return template;
   }

   public String GetIpAddress() {
      return this._ipaddress;
   }

   public String GetLogDate() {
      return this._log_date.toString();
   }

   public String GetWorld() {
      return this._location.getWorld().getName();
   }

   public String GetX() {
      return Integer.toString((int)this._location.getX());
   }

   public String GetY() {
      return Integer.toString((int)this._location.getY());
   }

   public String GetZ() {
      return Integer.toString((int)this._location.getZ());
   }

   public String GetName() {
      return this._player.getName();
   }

   public String GetContent() {
      return Matcher.quoteReplacement(this._message);
   }

   public UUID GetUUID() { return this._player.getUniqueId();}
}
