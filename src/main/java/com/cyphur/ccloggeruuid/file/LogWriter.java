package com.cyphur.ccloggeruuid.file;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.print.attribute.standard.PresentationDirection;

public class LogWriter {
   private CCLoggerUUID _plugin;
   private FileConfiguration _config;

   private File mainLogs;
   private File playerLogFolder;


   public LogWriter(CCLoggerUUID instance) {
      this._plugin = instance;
      this._config = instance.getConfig();
      mainLogs = null;
      playerLogFolder = null;

      try {
         if (this._plugin.isperDateLog()) {
            this.mainLogs = new File(this._plugin.getDataFolder(), "dateLog");
            if (!mainLogs.exists()) {
               mainLogs.mkdirs();
            }
         } else {
            mainLogs = new File(this._plugin.getDataFolder(), "cclogger.log");
            if (!mainLogs.exists()) {
               mainLogs.createNewFile();
            }
         }

         if(this._plugin.isperPlayerLog()){
            playerLogFolder = new File(this._plugin.getDataFolder(), "players");
            if (!playerLogFolder.exists()) {
               playerLogFolder.mkdirs();
            }
         } else {
            playerLogFolder = null;
         }

      } catch (IOException var3) {
         var3.printStackTrace();
         instance.getLogger().severe("init of LogWriter Errored!" + var3.getMessage());
      }

   }

   public void WriteToMainFile(String content) {
      if(mainLogs == null){
         return;
      }

      if(this._plugin.isperDateLog()) {
         File dateFile = new File(this.mainLogs, getDate() + ".log" );
         this.Write(dateFile, content);

      } else {
         this.Write(this.mainLogs, content);
      }


   }


   public void WriteToPlayerFile(UUID playerUUID, String content) {
      if (this._config.getBoolean("log.perplayerlog")) {
         File playerFile = new File(playerLogFolder, playerUUID.toString() + ".log");
         this.Write(playerFile, content);
      }

   }

   private void Write(File file, String content) {
      try {
         if (!file.exists()) {
            file.createNewFile();
         }

         FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write(content + "\n");
         bw.close();
      } catch (IOException var5) {
         var5.printStackTrace();
         verifyLogs();
      }

   }

   private String getDate(){
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      return sdf.format(date);
   }

   private void verifyLogs(){
      try {

         if (!mainLogs.exists()) {
            if (this._plugin.isperDateLog()) {
               mainLogs.mkdirs();
            } else {
               mainLogs.createNewFile();
            }
         }
         if (playerLogFolder.exists()) {
            playerLogFolder.mkdirs();
         }
      }catch (IOException ioe){
         ioe.printStackTrace();
      }
   }
}
