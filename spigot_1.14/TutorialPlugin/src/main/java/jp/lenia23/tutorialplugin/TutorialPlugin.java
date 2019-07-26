package jp.lenia23.tutorialplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class TutorialPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }


}
