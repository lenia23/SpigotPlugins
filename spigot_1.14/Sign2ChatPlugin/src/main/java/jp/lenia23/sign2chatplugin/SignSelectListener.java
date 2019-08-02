package jp.lenia23.sign2chatplugin;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static java.lang.String.*;

public class SignSelectListener implements Listener {
    private final Sign2ChatPlugin plugin;

    public SignSelectListener(Sign2ChatPlugin p) {
        this.plugin = p;
    }

    @EventHandler
    public void SignInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            Material type = block.getType();

            Player player = e.getPlayer();
            //if no api-version, following line returns false
            if (Tag.SIGNS.isTagged(type)) {
                Sign sign = (Sign) block.getState();
                //sign.setEditable(true);
                String s = join("", sign.getLines());
                player.sendRawMessage("[Signboard] " + s);

            }
        }
    }
}
