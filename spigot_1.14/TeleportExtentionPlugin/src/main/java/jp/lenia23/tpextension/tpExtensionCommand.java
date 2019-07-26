package jp.lenia23.tpextension;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpExtensionCommand implements CommandExecutor {
    private final tpExtension plugin;

    public tpExtensionCommand(tpExtension ref) {
        plugin = ref;
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
                    return true;
                } else if (subCommand.equalsIgnoreCase("list") && arg_len == 1 ) {
                    player.sendRawMessage("Registered tplabel: ");
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
}
