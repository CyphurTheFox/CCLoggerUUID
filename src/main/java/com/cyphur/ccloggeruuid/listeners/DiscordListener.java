package com.cyphur.ccloggeruuid.listeners;

import com.cyphur.ccloggeruuid.CCLoggerUUID;
import com.cyphur.ccloggeruuid.file.LogWriter;
import com.cyphur.ccloggeruuid.structures.DiscordObject;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DiscordListener {
    private String _logtemplate;
    private CCLoggerUUID _plugin;
    private LogWriter _logWriter;

    public DiscordListener(CCLoggerUUID instance) {
        this._plugin = instance;
        this._logtemplate = instance.getDiscordTemplate();

        this._logWriter = instance.LogWriter();

        if(this._logWriter == null){
            instance.getLogger().severe("Missing LogWriter!");
        }
    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void onDiscord(DiscordGuildMessageReceivedEvent event) {
        DiscordObject co = new DiscordObject(event, this._logtemplate);

        this._logWriter.WriteToMainFile(co.format());
    }
}
