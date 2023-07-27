package de.petropia.spacelifeJobs.job.listener;

import de.petropia.spacelifeJobs.ConfigResolver;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.Job;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearListener implements Listener {

    private final Job job;
    private final double amount;

    public ShearListener(Job job){
        this.job = job;
        amount = ConfigResolver.getCustomJobDouble(job, "Shear");
        Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onSheapSheer(PlayerShearEntityEvent event){
        job.reportMoneyBack(event.getPlayer(), amount);
    }

}
