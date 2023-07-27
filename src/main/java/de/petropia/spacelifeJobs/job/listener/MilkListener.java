package de.petropia.spacelifeJobs.job.listener;

import de.petropia.spacelifeJobs.ConfigResolver;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.Job;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class MilkListener implements Listener {

    private final Job job;
    private final double amount;

    public MilkListener(Job job){
        this.job = job;
        amount = ConfigResolver.getCustomJobDouble(job, "Milk");
        Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMilkLister(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if(event.getRightClicked().getType() == EntityType.COW || event.getRightClicked().getType() == EntityType.MUSHROOM_COW){
            if(player.getInventory().getItemInMainHand().getType() == Material.BOWL || player.getInventory().getItemInMainHand().getType() == Material.BUCKET){
                job.reportMoneyBack(player, amount);
            }
        }
    }
}
