package com.cyphur.ccloggeruuid.structures;

import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;

import java.util.Date;
import java.util.regex.Matcher;

public class DiscordObject {
    private String _chattemplate;
    private User _user;
    private String _message;
    private Date _log_date;

    public DiscordObject(DiscordGuildMessageReceivedEvent event, String template) {
        this._user = event.getAuthor();
        this._message = event.getMessage().getContentStripped();
        this._log_date = new Date();
        this._chattemplate = template;
    }

    public String format() {
        String template = this._chattemplate;
        template = template.replaceAll("%date", this._log_date.toString());
        template = template.replaceAll("%name", this._user.getName());
        template = template.replaceAll("%content", Matcher.quoteReplacement(this._message));
        return template;
    }

    public String GetLogDate() {
        return this._log_date.toString();
    }

    public String GetName() {
        return this._user.getName();
    }

    public String GetContent() {
        return Matcher.quoteReplacement(this._message);
    }

}
