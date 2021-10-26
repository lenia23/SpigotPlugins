package jp.bismaschiruo.tpextension.tpextension.teleportextensionplugin;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class tpExtensionCommand implements TabExecutor {
    private final TeleportExtensionPlugin plugin;
    private final List<String> subCommandList = new ArrayList<String>(Arrays.asList("add", "list", "remove", "tp", "update"));
    private Set<String> tpLabelList;

    public tpExtensionCommand(TeleportExtensionPlugin ref) {
        plugin = ref;
        tpLabelList = plugin.getListLabels();
    }

    // This method is called, when somebody uses our command
    /*
     * CommandSender represents whatever is sending the command. This could be a Player,
     * ConsoleCommandSender, or BlockCommandSender (a command block)
     * Command represents what the command being called is. This will almost always be known ahead of time.
     * Label represents the exact first word of the command (excluding arguments) that was entered by the sender
     * Args is the remainder of the command statement (excluding the label) split up by spaces and put into an array.
     */
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            //コマンドがちゃんと"tpe"か確認
            if (label.equalsIgnoreCase("tpe")) {
                int arg_len = args.length;
                //subCommand: tp | add | remove | list | none
                //subCommandが存在するかどうか
                String subCommand = (arg_len == 0) ? "" : args[0];

                if (subCommand.equalsIgnoreCase("tp") && arg_len == 2 ) {
                    String tplabel = args[1];
                    if (plugin.exists(tplabel)){
                        String x = plugin.getLabelVal(tplabel, "x");
                        String y = plugin.getLabelVal(tplabel, "y");
                        String z = plugin.getLabelVal(tplabel, "z");
                        String world = plugin.getLabelVal(tplabel, "world");
                        if (!world.equals(toString(player.getWorld().getEnvironment()))) {
                            player.sendMessage(tplabel + "は" + world + "用の座標です。ここは"+toString(player.getWorld().getEnvironment())+"です。");
                            return true;
                        }
                        int x_n = Integer.parseInt(x);
                        int y_n = Integer.parseInt(y);
                        int z_n = Integer.parseInt(z);
                        Material tp_block = player.getWorld().getBlockAt(x_n, y_n, z_n).getType();
                        int count = 0;
                        while ( tp_block != Material.AIR && tp_block != Material.CAVE_AIR && tp_block != Material.VOID_AIR ){
                            if(count==0) player.sendMessage("!!!!"+tplabel+"のテレポート先は地中なので、座標を調節してテレポートしました!!!!");
                            y_n++;
                            count++;
                            tp_block = player.getWorld().getBlockAt(x_n, y_n, z_n).getType();
                        }
                        y = String.valueOf(y_n);
                        player.performCommand("tp @p " + x + " " + y + " " + z );
                        return true;
                    }
                } else if (subCommand.equalsIgnoreCase("add") && arg_len == 5 ) {
                    String tplabel = args[1];
                    int x = Integer.parseInt(args[2]);
                    int y = Integer.parseInt(args[3]);
                    int z = Integer.parseInt(args[4]);
                    String world = toString(player.getWorld().getEnvironment());

                    if (plugin.exists(tplabel)){

                        player.sendRawMessage(tplabel + " already exists. If you want to update coordinates, use update subcommand.");
                    } else {
                        plugin.addLabel(tplabel, x, y, z, world);
                        player.sendRawMessage(tplabel+ ": [x: " + x + ", y: " + y + ", z: " + z + " ,world:" + world + "] is added");
                        tpLabelList = plugin.getListLabels();
                        return true;
                    }

                } else if (subCommand.equalsIgnoreCase("update") && arg_len == 5 ) {
                    String tplabel = args[1];
                    int x = Integer.parseInt(args[2]);
                    int y = Integer.parseInt(args[3]);
                    int z = Integer.parseInt(args[4]);
                    String world = toString(player.getWorld().getEnvironment());
                    if (plugin.exists(tplabel)){
                        plugin.addLabel(tplabel, x, y, z, world);
                        player.sendRawMessage(tplabel+ ": [x: " + x + ", y: " + y + ", z: " + z + " ,world:" + world + "] is updated");
                    } else {
                        player.sendRawMessage(tplabel + " does not exist. If you want to add coordinates, use add subcommand.");
                        return true;
                    }

                } else if (subCommand.equalsIgnoreCase("remove") && arg_len == 2 ) {
                    String tplabel = args[1];
                    plugin.removeLabel(tplabel);
                    player.sendRawMessage(tplabel + " is removed");
                    tpLabelList = plugin.getListLabels();
                    return true;
                } else if (subCommand.equalsIgnoreCase("list") && arg_len == 1 ) {
                    player.sendRawMessage("Registered tp labels: ");
                    for (String l : plugin.getListLabels()) {
                        String x = plugin.getLabelVal(l, "x");
                        String y = plugin.getLabelVal(l, "y");
                        String z = plugin.getLabelVal(l, "z");
                        String world = plugin.getLabelVal(l, "world");
                        player.sendRawMessage(l + ": [x: " + x + ", y: " + y + ", z: " + z + ", world:"+ world +"]");
                    }
                    return true;
                }
            }
        }
        //If return true, the usage of command is printed
        return false;
    }

    private String toString(World.Environment environment) {
        if(environment == World.Environment.NORMAL)return "NORMAL";
        else if (environment == World.Environment.NETHER)return "NETHER";
        else if (environment == World.Environment.THE_END)return "THE_END";
        return "";
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> tabList = new ArrayList<String>();
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (label.equalsIgnoreCase("tpe")) {
                int arg_len = args.length;
                String currentString = (arg_len !=0) ? args[arg_len - 1] : "";
                if (arg_len <= 1) {
                    for (String subCommand: subCommandList) {
                        if (subCommand.startsWith(currentString)) {tabList.add(subCommand);}
                    }
                }else if (arg_len == 2 && (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("remove"))){
                    //tpe listのラベル名を全部みていく
                    for (String tpLabel: tpLabelList) {
                        //現在のワールドと同じワールドのラベルだけもってくる
                        if (plugin.getLabelVal(tpLabel, "world").equals(toString(player.getWorld().getEnvironment()))){
                            if (tpLabel.startsWith(currentString)) {tabList.add(tpLabel);}
                        }
                    }
                }else if ((3<= arg_len && arg_len <= 5) && (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("add")) ) {
                    //updateかaddのとき、現在座標を補完する。
                    String x = Integer.toString(player.getLocation().getBlockX());
                    String y = Integer.toString(player.getLocation().getBlockY());
                    String z = Integer.toString(player.getLocation().getBlockZ());
                    switch (arg_len) {
                        case 3:
                            tabList.add(x+" "+y+" "+z);
                            tabList.add(x);
                            break;
                        case 4:
                            tabList.add(y+" "+z);
                            tabList.add(y);
                            break;
                        case 5:
                            tabList.add(z);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return tabList;
    }
}
