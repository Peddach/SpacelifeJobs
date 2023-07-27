package de.petropia.spacelifeJobs.job.listener;

import de.petropia.spacelifeJobs.ConfigResolver;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.Job;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class BreedListener implements Listener {

    private final double amount;
    private final Job job;

    public BreedListener(Job job){
        this.job = job;
        amount = ConfigResolver.getCustomJobDouble(job, "Breed");
        Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBreedEvent(EntityBreedEvent event){
        if(event.getBreeder() == null){
            return;
        }
        if(event.getBreeder().getType() != EntityType.PLAYER){
            return;
        }
        Player player = (Player) event.getBreeder();
        job.reportMoneyBack(player, amount);
    }

}
