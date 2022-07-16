package fr.shayfox.remapped.tasks;

/**
 * <strong>BuildTools task</strong>, his execute BuildTools.jar
 * to generate spigot-mapped and file's necessary for SpecialSource.jar
 */
public abstract class BuildTools extends Task{

    @Override
    public void load() {

    }

    @Override
    public void execute() {
        main.getManager().cmdBuildTools(main.getExtension().getMinecraftVersion().get());
    }
}
