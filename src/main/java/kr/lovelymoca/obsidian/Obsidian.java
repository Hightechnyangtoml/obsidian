package kr.lovelymoca.obsidian;

import kr.lovelymoca.obsidian.command.GameCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Obsidian extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // 커맨드 등록
        getCommand("game").setExecutor(new GameCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
