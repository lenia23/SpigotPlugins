package jp.lenia23.sign2chatplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Sign2ChatPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SignSelectListener(this), this);
        getLogger().info("onEnable is called!");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

}
