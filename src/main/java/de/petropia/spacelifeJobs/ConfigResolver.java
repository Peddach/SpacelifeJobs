package de.petropia.spacelifeJobs;

import de.petropia.spacelifeJobs.job.Job;

import java.util.ArrayList;
import java.util.List;

public class ConfigResolver {

    public static double getJobBaseValue(){
        return Jobs.getInstance().getConfig().getDouble("LvlBaseValue");
    }

    public static double getJobMultiplier(Job job){
        return Jobs.getInstance().getConfig().getDouble("Jobs."+job.getConfigID()+".Faktor");
    }

    public static double getMaxMoneyBuff(){
        return Jobs.getInstance().getConfig().getDouble("MaxMoneyBuff");
    }

    public static <E extends Enum<E>> List<EnumValueEntry<E>> getEnumValueEntries(Job job, Class<E> enumClass, String configEnd) {
        List<String> stringList = Jobs.getInstance().getConfig().getStringList("Jobs." + job.getConfigID() + "." + configEnd);
        List<EnumValueEntry<E>> valueEntries = new ArrayList<>();
        stringList.forEach(string -> {
            String[] split = string.split(";");
            if (split.length != 2) {
                Jobs.getInstance().getLogger().warning("Job " + job.getName() + " not right "+ configEnd +" formatted (Semicolon?): " + string);
                return;
            }
            double value;
            E enumValue;
            try {
                value = Double.parseDouble(split[1]);
                enumValue = Enum.valueOf(enumClass, split[0]);
            } catch (IllegalArgumentException e) {
                Jobs.getInstance().getLogger().warning("Job " + job.getName() + " not right " + configEnd + " formatted (parse): " + string);
                return;
            }
            valueEntries.add(new EnumValueEntry<>(enumValue, value));
        });
        if(valueEntries.size() == 0){
            Jobs.getInstance().getLogger().warning("No entires for Jobs."+job.getConfigID()+"."+configEnd);
        }
        return valueEntries;
    }

    public static double getCustomJobDouble(Job job, String key){
        return Jobs.getInstance().getConfig().getDouble("Jobs." + job.getConfigID() + "." + key);
    }

    public record EnumValueEntry<E extends Enum<E>>(E enumValue, double value) {}
}
