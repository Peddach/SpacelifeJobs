package de.petropia.spacelifeJobs.gui;

import de.petropia.spacelifeCore.player.JobStats;
import de.petropia.spacelifeCore.player.SpacelifeDatabase;
import de.petropia.spacelifeCore.player.SpacelifePlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.text.DecimalFormat;
import java.util.List;

public class JobsOverviewGui {

    private static final String FARMWORLD = "Dieser Job kann nur in der Farmwelt ausgeführt werden";
    private static final String BUILDWORLD = "Dieser Job kann nur in der Bauwelt ausgeführt werden";

    public JobsOverviewGui(Player player){
        SpacelifePlayer spacelifePlayer = SpacelifeDatabase.getInstance().getCachedPlayer(player.getUniqueId());
        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(3)
                .title(Component.text("Jobübersicht", NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD))
                .create();
        gui.setItem(10, createJobItem("Meeresbiologe", getJobStats(spacelifePlayer, "Meeresbiologe"),Material.FISHING_ROD, FARMWORLD));
        gui.setItem(11, createJobItem("Krieger", getJobStats(spacelifePlayer, "Krieger"), Material.DIAMOND_SWORD, FARMWORLD));
        gui.setItem(12, createJobItem("Höhlenforscher", getJobStats(spacelifePlayer, "Caveforscher"), Material.IRON_PICKAXE, FARMWORLD));
        gui.setItem(14, createJobItem("Netherentdecker", getJobStats(spacelifePlayer, "Nether"), Material.QUARTZ, FARMWORLD));
        gui.setItem(15, createJobItem("Förster", getJobStats(spacelifePlayer, "Forster"), Material.GOLDEN_AXE, FARMWORLD));
        gui.setItem(16, createJobItem("Landwirt", getJobStats(spacelifePlayer, "Landwirt"), Material.STONE_HOE, BUILDWORLD));
        gui.open(player);
    }

    private GuiItem createJobItem(String jobName, JobStats stats,  Material material, String... extraInfo){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<Component> lore = new java.util.ArrayList<>(List.of(
                Component.empty(),
                Component.text("Verdienst: ", NamedTextColor.GRAY).append(Component.text(decimalFormat.format(stats.getCurrentMoney()) + "$", NamedTextColor.GOLD)),
                Component.text("Level: ", NamedTextColor.GRAY).append(Component.text(stats.getLevel(), NamedTextColor.GOLD)),
                Component.text("Wirkungsgrad: ", NamedTextColor.GRAY).append(Component.text("-" + decimalFormat.format(stats.getBuff() * 100) + "%", NamedTextColor.GOLD)),
                Component.empty()
        ));
        for (String string : extraInfo){
            lore.add(Component.text(string, NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
        }
        return ItemBuilder.from(material)
                .name(Component.text(jobName, NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .lore(lore)
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .asGuiItem();
    }

    private JobStats getJobStats(SpacelifePlayer player, String id){
        return player.getJobStats().computeIfAbsent(id, unused -> {
            JobStats stats = new JobStats(id);
            stats.levelUp();
            return stats;
        });
    }
}
