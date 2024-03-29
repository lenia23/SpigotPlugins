package jp.lenia23.tpextension;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class tpExtensionCommand implements TabExecutor {
    private final tpExtension plugin;
    private final List<String> subCommandList = new ArrayList<String>(Arrays.asList("add", "list", "remove", "tp", "update"));
    private Set<String> tpLabelList;

    public tpExtensionCommand(tpExtension ref) {
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

            if (label.equalsIgnoreCase("tpe")) {
                int arg_len = args.length;
                //subCommand: tp | add | remove | list
                String subCommand = (arg_len == 0) ? "" : args[0];

                if (subCommand.equalsIgnoreCase("tp") && arg_len == 2 ) {
                    String tplabel = args[1];
                    if (plugin.exists(tplabel)){
                        String x = plugin.getLabelVal(tplabel, "x");
                        String y = plugin.getLabelVal(tplabel, "y");
                        String z = plugin.getLabelVal(tplabel, "z");
                        player.performCommand("tp @p " + x + " " + y + " " + z );
                        return true;
                    }
                } else if (subCommand.equalsIgnoreCase("add") && arg_len == 5 ) {
                    String tplabel = args[1];
                    int x = Integer.parseInt(args[2]);
                    int y = Integer.parseInt(args[3]);
                    int z = Integer.parseInt(args[4]);

                    if (plugin.exists(tplabel)){
                        player.sendRawMessage(tplabel + " already exists. If you want to update coordinates, use update subcommand.");
                        return true;
                    } else {
                        plugin.addLabel(tplabel, x, y, z);
                        player.sendRawMessage(tplabel+ ": [x: " + x + ", y: " + y + ", z: " + z + "] is added");
                        tpLabelList = plugin.getListLabels();
                        return true;
                    }

                } else if (subCommand.equalsIgnoreCase("update") && arg_len == 5 ) {
                    String tplabel = args[1];
                    int x = Integer.parseInt(args[2]);
                    int y = Integer.parseInt(args[3]);
                    int z = Integer.parseInt(args[4]);
                    if (plugin.exists(tplabel)){
                        plugin.addLabel(tplabel, x, y, z);
                        player.sendRawMessage(tplabel+ ": [x: " + x + ", y: " + y + ", z: " + z + "] is updated");
                        return true;
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
                        player.sendRawMessage(l + ": [x: " + x + ", y: " + y + ", z: " + z + "]");
                    }
                    return true;
                }
            }
        }
        //If return true, the usage of command is printed
        return false;
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
                    for (String tpLabel: tpLabelList) {
                        if (tpLabel.startsWith(currentString)) {tabList.add(tpLabel);}
                    }
                }else if ((3<= arg_len && arg_len <= 5) && (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("add")) ) {
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
