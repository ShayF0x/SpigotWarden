package fr.shayfox.remapped;

import fr.shayfox.remapped.tasks.BuildTools;
import fr.shayfox.remapped.tasks.RemapJar;
import fr.shayfox.remapped.tasks.Task;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;

import java.nio.file.Path;


/**
 * Main class of <strong>Remapped</strong>
 * @version 1.0
 * @author ShayFox
 */
public class SpigotWarden implements Plugin<Project> {
    private Project project;
    private SpigotWardenExtension extension;
    private final Manager manager;
    private TaskProvider<Jar> buildJar;

    public SpigotWarden() {
        this.manager = new Manager(this);
    }

    @Override
    public void apply(Project project) {
        this.project = project;
        this.extension = initExtension();

        project.getPlugins().apply(JavaLibraryPlugin.class);

        buildJar = project.getTasks().named(JavaPlugin.JAR_TASK_NAME, Jar.class);
        buildJar.get().getDestinationDirectory().fileValue(extension.getBuildOutput().get());

        final TaskProvider<BuildTools> buildTools = addTask("buildTools", "Build spigot-remapped.jar to include in your dependencies", BuildTools.class);
        final TaskProvider<RemapJar> remapJar = addTask("remapJar", "Run this to create spigot readable jar from your plugin", RemapJar.class);
    }

    private SpigotWardenExtension initExtension() {
        SpigotWardenExtension extension = project.getExtensions()
                .create("SpigotWarden", SpigotWardenExtension.class);

        extension.getMinecraftVersion().convention("1.19-R0.1-SNAPSHOT");
        extension.getBuildOutput().convention(Path.of(System.getProperty("user.dir")).resolve("build").resolve("libs").toFile());

        return extension;
    }

    private TaskProvider addTask(String name, String description, Class clazz) {
        TaskProvider<? extends Task> task = project.getTasks().register(name, clazz);
        task.configure(currentTask -> {
            currentTask.main = this;
            currentTask.setDescription(description);
            task.get().load();
        });
        return task;
    }

    /**
     * get Remapped extension class which contains plugin's properties
     * @return RemappedExtension class
     * @see SpigotWardenExtension
     */
    public SpigotWardenExtension getExtension() {
        return extension;
    }
    /**
     * get Manager class which contains the plugin's utility methods
     * @return Manager class
     * @see Manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * get Jar gradle task class to compile plugin.jar and change output directory
     * @return Jar class taskProvider
     * @see Jar
     */
    public TaskProvider<Jar> getBuildJar() {
        return buildJar;
    }
}
