# CCLoggerUUID

This is my custom modified version of the minecraft server logging plugin CCLogger. It has been updated for more recent versions of minecraft. 
The updated version boasts a handful of improvements over the original. 

---

### Improvements

CCLoggerUUID saves log files by a player's UUID, as opposed to their name, which is no longer static.

CCLoggerUUID saves full server logs by date, in the format yyyy-MM-dd, making logs easier to download and search.

CCLoggerUUID includes a hook for popular plugin DiscordSRV, allowing messages from DiscordSRV to be logged in full server chat,
to help alleviate possible issues where logs become fragmented when discord is heavily involved in a conversation.

---

### Downsides
CCLoggerUUID does not, however, support MongoDB the same way the original CCLogger did.

CCLoggerUUID is not as compatible with older minecraft versions as the origninal CCLogger is.
