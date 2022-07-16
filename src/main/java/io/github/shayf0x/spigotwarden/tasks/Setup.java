package io.github.shayf0x.spigotwarden.tasks;

import io.github.shayf0x.spigotwarden.Manager;

/**
 * <strong>setup task</strong>, his test and download all files necessary,
 */
public abstract class Setup extends Task{
    private Manager manager;
    @Override
    public void load() {
        this.manager = main.getManager();
    }

    /**
     * Generate and download BuildTools and SpecialSource if it's necessary
     */
    @Override
    public void execute() {
        main.getBuildJar().get().getDestinationDirectory().fileValue(main.getExtension().getBuildOutput().get());
        manager.testSpecialSource();
        manager.createIfNotExistBuildOutput();
        final boolean buildToolsTest = manager.testBuildTools(main.getExtension().getMinecraftVersion().get());
        if(!buildToolsTest){
            System.out.println("\u001b[1mFile missing, generate BuildTools.jar...\u001b[0m");
            manager.cmdBuildTools(main.getExtension().getMinecraftVersion().get());
        }
    }
}
