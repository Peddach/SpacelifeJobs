package de.petropia.spacelifeJobs.command;

import de.petropia.spacelifeCore.player.SpacelifeDatabase;
import de.petropia.spacelifeCore.player.SpacelifePlayer;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.gui.JobsOverviewGui;
import de.petropia.turtleServer.server.TurtleServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            return false;
        }
        if(args.length == 0){
            new JobsOverviewGui(player);
            return true;
        }
        if(args[0].equalsIgnoreCase("clear")){
            if(!player.hasPermission("spacelife.jobs.command.job.clear")){
                return false;
            }
            if(args.length != 2){
                Jobs.getInstance().getMessageUtil().sendMessage(player, Component.text("Bitte gib einen Spieler an, welcher auf diesem Server online ist oder nicht auf Spacelife!", NamedTextColor.RED));
                return false;
            }
            String name = args[1];
            TurtleServer.getMongoDBHandler().getPetropiaPlayerByUsername(name).thenAccept(user -> Bukkit.getScheduler().runTask(Jobs.getInstance(), () -> {
                if(user.isOnline() && Bukkit.getPlayer(name) == null){
                    Jobs.getInstance().getMessageUtil().sendMessage(player, Component.text("Der Spieler ist nicht offline und nicht auf deinem Server", NamedTextColor.RED));
                    return;
                }
                if(Bukkit.getPlayer(name) != null){
                    SpacelifePlayer spacelifePlayer = SpacelifeDatabase.getInstance().getCachedPlayer(UUID.fromString(user.getUuid()));
                    spacelifePlayer.clearStats();
                    Jobs.getInstance().getMessageUtil().sendMessage(player, Component.text("Job level und verdienst wurden gelöscht!", NamedTextColor.RED));
                    return;
                }
                SpacelifeDatabase.getInstance().getSpacelifePlayer(UUID.fromString(user.getUuid())).thenAccept(spaceplayer -> {
                    spaceplayer.clearStats();
                    Jobs.getInstance().getMessageUtil().sendMessage(player, Component.text("Job level und verdienst wurden erfolgreich gelöscht!", NamedTextColor.RED));
                });
            }));
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("spacelife.jobs.command.job.clear")){
            return new ArrayList<>();
        }
        if(args.length == 1){
            return List.of("clear");
        }
        return new ArrayList<>();
    }
}
