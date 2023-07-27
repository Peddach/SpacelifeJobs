package de.petropia.spacelifeJobs.job.listener;

import de.petropia.spacelifeJobs.ConfigResolver;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.Job;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class KillListener implements Listener {

    private final Job job;
    private final HashMap<EntityType, Double> revenue_map = new HashMap<>();

    public KillListener(Job job) {
        this.job = job;
        for(ConfigResolver.EnumValueEntry<EntityType> entry : ConfigResolver.getEnumValueEntries(job, EntityType.class, "Kill")){
            revenue_map.put(entry.enumValue(), entry.value());
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityKill(EntityDeathEvent event){
        Double amount = revenue_map.get(event.getEntity().getType());
        if(amount == null){
            return;
        }
        if(event.getEntity().getKiller() == null){
            return;
        }
        job.reportMoneyBack(event.getEntity().getKiller(), amount);
    }
}
