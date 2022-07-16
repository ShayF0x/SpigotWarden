package src.main.java.fr.shayfox.test;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.event.entity.*;

import java.awt.*;

public class Main extends JavaPlugin{
    @Override
    public void onEnable(){
        spawnVillager(Bukkit.getWorld("world").getSpawnLocation());
    }

    private void spawnVillager(Location location) {
        final CustomVillager villager = new CustomVillager(location);

        Level world = ((CraftWorld) location.getWorld()).getHandle();
        world.addFreshEntity(villager, CreatureSpawnEvent.SpawnReason.DEFAULT);
    }

    public class CustomVillager extends Villager {

        public CustomVillager(Location lov) {
            super(EntityType.VILLAGER, ((CraftWorld) loc.getWorld()).getHandle());
            this.setPos(loc.getX(), loc.getY(), loc.getZ());
            this.setCustomName(Component.literal("§8My Villager"));
            this.setCustomNameVisible(true);
            this.setPersistenceRequired(true);
            this.setVillagerData(new VillagerData(VillagerType.TAIGA, VillagerProfession.CARTOGRAPHER, 5));
        }

    }
}
