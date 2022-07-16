package fr.shayfox.remapped;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * It's Enum class which contain all paths for SpecialSource and his obfuscate, deObfuscate commands.
 * <br>Enum variables:
 * <br>-{@link #REMAPPED_MOJANG}
 * <br>-{@link #MAPS_MOJANG}
 * <br>-{@link #REMAPPED_OBF}
 * <br>-{@link #MAPS_SPIGOT}
 * @see #toPath(String)
 */
public enum PathBank {
    /**
     * path to file: spigot-(spigot version e.g: "1.19-R0.1-SNAPSHOT")-remapped-mojang.jar
     */
    REMAPPED_MOJANG(Arrays.asList(System.getProperty("user.home"), ".m2", "repository", "org", "spigotmc", "spigot", "%s"), "spigot-%s-remapped-mojang.jar"),
    /**
     * path to file: minecraft-server-(spigot version e.g: "1.19-R0.1-SNAPSHOT")-maps-mojang.txt
     */
    MAPS_MOJANG(Arrays.asList(System.getProperty("user.home"), ".m2", "repository", "org", "spigotmc", "minecraft-server", "%s"), "minecraft-server-%s-maps-mojang.txt"),
    /**
     * path to file: minecraft-server-(spigot version e.g: "1.19-R0.1-SNAPSHOT")-remapped-obf.jar
     */
    REMAPPED_OBF(Arrays.asList(System.getProperty("user.home"), ".m2", "repository", "org", "spigotmc", "spigot", "%s"), "spigot-%s-remapped-obf.jar"),
    /**
     * path to file: minecraft-server-(spigot version e.g: "1.19-R0.1-SNAPSHOT")-maps-spigot.csrg
     */
    MAPS_SPIGOT(Arrays.asList(System.getProperty("user.home"), ".m2", "repository", "org", "spigotmc", "minecraft-server", "%s"), "minecraft-server-%s-maps-spigot.csrg");

    private final List<String> paths;
    private final String name;

    PathBank(List<String> paths, String name) {
        this.paths = paths;
        this.name = name;
    }

    /**
     * method to return path of variable
     * @param version String <i>(e.g: "1.19-R0.1-SNAPSHOT")</i>
     * @return {@link Path}
     */
    public Path toPath(String version){
        String pathStr = String.join(File.separator, serialized(paths, version));
        return Path.of(pathStr, serialized(name, version));
    }

    private List<String> serialized(List<String> list, String replace){
        List<String> finalList = new ArrayList<>(list);
        Collections.replaceAll(finalList, "%s", replace);
        return finalList;
    }
    private String serialized(String str, String replace){
        return str.replaceAll("%s", replace);
    }
}
