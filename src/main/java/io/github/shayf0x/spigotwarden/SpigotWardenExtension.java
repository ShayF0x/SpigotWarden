package io.github.shayf0x.spigotwarden;

import org.gradle.api.provider.Property;

import java.io.File;

/**
 * Manage the plugin's extensions.
 * @see #getMinecraftVersion()
 * @see #getBuildOutput()
 */
public interface SpigotWardenExtension {
    /**
     * Provide the target spigot version (e.g: 1.19-R0.1-SNAPSHOT).
     * <br><strong>REQUIRED</strong>.
     * @return the target spigot version string.
     */
    Property<String> getMinecraftVersion();
    /**
     * Provide the output directory (e.g: file('path')).
     * <br><strong>REQUIRED</strong>.
     * @return the output directory of your plugin.jar.
     */
    Property<File> getBuildOutput();

}
