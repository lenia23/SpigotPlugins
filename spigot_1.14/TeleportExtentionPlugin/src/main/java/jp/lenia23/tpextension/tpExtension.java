package jp.lenia23.tpextension;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class tpExtension extends JavaPlugin {
    public FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = getConfig();
        tpExtensionCommand tpe = new tpExtensionCommand(this);
        this.getCommand("tpe").setExecutor(tpe);
        this.getCommand("tpe").setTabCompleter(tpe);
        getLogger().info("TpExtensionPlugin is loaded!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TpExtensionPlugin is disabled");
    }

    public void addLabel(String label, int x, int y, int z) {
        this.config.set(label+".x", x);
        this.config.set(label+".y", y);
        this.config.set(label+".z", z);

        saveConfig();
        reloadTpConfig();
    }

    public void removeLabel(String label) {
        this.config.set(label, null);
        saveConfig();
        reloadTpConfig();
    }

    public Set<String> getListLabels() {
        return config.getKeys(false);
    }
    public String getLabelVal(String label, String key) {
        return config.getString(label+'.'+key);
    }

    public void reloadTpConfig() {
        reloadConfig();
        config = getConfig();
    }

    public boolean exists(String label){
        return config.contains(label);
    }
}
