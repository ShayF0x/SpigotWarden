package fr.shayfox.remapped.tasks;

import fr.shayfox.remapped.SpigotWarden;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

/**
 * Parent class of all tasks:
 * <br>-{@link BuildTools}
 * <br>-{@link RemapJar}
 */
public abstract class Task extends DefaultTask {

    @Input
    public SpigotWarden main;

    public abstract void load();

    @TaskAction
    public abstract void execute();


    public Task() {
        this.setGroup("remapped");
    }
}
