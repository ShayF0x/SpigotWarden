package fr.shayfox.remapped.tasks;

import fr.shayfox.remapped.Manager;
import fr.shayfox.remapped.PathBank;
import org.gradle.internal.jvm.Jvm;

import java.io.File;
import java.nio.file.Path;

/**
 * <strong>RemapJar task</strong>, his build plugin.jar,
 * obfuscate ({@link #obfuscate(String)} and deObfuscate ({@link #deObfuscate(String)}
 * plugin.jar to make plugin can readable by spigot
 */
public abstract class RemapJar extends Task{
    private Manager manager;

    /**
     * run "jar" task before to generate plugin.jar
     */
    @Override
    public void load() {
        this.dependsOn(main.getBuildJar());
        this.manager = main.getManager();
    }

    /**
     * Generate and download BuildTools and SpecialSource if it's necessary
     * then obfuscate and deObfuscate jar file to make spigot can read plugin.jar
     * <br>Obfuscate and deObfuscate commands use <a href="https://www.spigotmc.org/threads/spigot-bungeecord-1-19.559742/">md_5 command using SpecialSource</a>
     */
    @Override
    public void execute() {
        main.getBuildJar().get().getDestinationDirectory().fileValue(main.getExtension().getBuildOutput().get());
        final String specialSourceVersion = manager.testSpecialSource();
        final boolean createOutput = manager.createIfNotExistBuildOutput();
        final boolean buildToolsTest = manager.testBuildTools(main.getExtension().getMinecraftVersion().get());
        if(!buildToolsTest){
            System.out.println("Un fichié manque, génération du buildTools");
            manager.cmdBuildTools(main.getExtension().getMinecraftVersion().get());
        }
        System.out.println("Run obfuscate");
        obfuscate(specialSourceVersion);
        System.out.println("run deobfuscate");
        deObfuscate(specialSourceVersion);
    }

    private void obfuscate(String version){
        final Path jarBuild = main.getBuildJar().get().getArchiveFile().get().getAsFile().toPath();
        final String jarName = main.getBuildJar().get().getArchiveFileName().get().replaceAll(".jar", "");
        final Path root = Path.of(System.getProperty("user.dir")).resolve(".gradle").resolve("remapped");
        final Path obfJar = root.resolve(String.format("%s-obf.jar", jarName));
        final String spigotVersion = main.getExtension().getMinecraftVersion().get();
        final Path remappedMojang = PathBank.REMAPPED_MOJANG.toPath(spigotVersion);
        final Path mapsMojang = PathBank.MAPS_MOJANG.toPath(spigotVersion);

        final String[] commandLine = new String[]{
                Jvm.current().getJavaExecutable().getAbsolutePath(),
                "-cp",
                String.format("SpecialSource-%s.jar%s%s", version, File.pathSeparator, remappedMojang),
                "net.md_5.specialsource.SpecialSource",
                "--live",
                "-i",
                jarBuild.toString(),
                "-o",
                obfJar.toString(),
                "-m",
                mapsMojang.toString(),
                "--reverse"
        };
        manager.runCommand(commandLine, root);
    }

    private void deObfuscate(String version){
        final Path jarBuild = main.getBuildJar().get().getArchiveFile().get().getAsFile().toPath();
        final String jarName = main.getBuildJar().get().getArchiveFileName().get().replaceAll(".jar", "");
        final Path root = Path.of(System.getProperty("user.dir")).resolve(".gradle").resolve("remapped");
        final Path obfJar = root.resolve(String.format("%s-obf.jar", jarName));
        final String spigotVersion = main.getExtension().getMinecraftVersion().get();
        final Path remappedObf = PathBank.REMAPPED_OBF.toPath(spigotVersion);
        final Path mapsSpigot = PathBank.MAPS_SPIGOT.toPath(spigotVersion);

        final String[] commandLine = new String[]{
                Jvm.current().getJavaExecutable().getAbsolutePath(),
                "-cp",
                String.format("SpecialSource-%s.jar%s%s", version, File.pathSeparator, remappedObf),
                "net.md_5.specialsource.SpecialSource",
                "--live",
                "-i",
                obfJar.toString(),
                "-o",
                jarBuild.toString(),
                "-m",
                mapsSpigot.toString()
        };
        manager.runCommand(commandLine, root);
    }

}
