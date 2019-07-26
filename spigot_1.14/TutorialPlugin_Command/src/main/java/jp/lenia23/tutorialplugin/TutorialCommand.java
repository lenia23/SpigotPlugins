package jp.lenia23.tutorialplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialCommand implements CommandExecutor {

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

            player.sendRawMessage(command.getLabel()+label + " is called.");

        }


        //If return true, the usage of command is printed
        return true;
    }
}
