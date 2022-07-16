<div id="top"></div>
<h2 align="center">Spigot Warden (Remapper)</h2>
<p align="center"><i>like Warden mob in minecraft, Spigot Warden obscures what surrounds you</i></p>
<p align="center">
    <img src="https://img.shields.io/badge/build-Ready%20to%20go-brightgreen"/>
    <img src="https://img.shields.io/badge/dynamic/json?color=blue&label=Version&query=version&url=https%3A%2F%2Fraw.githubusercontent.com%2FShayF0x%2FSpigotWarden%2Fmaster%2FSpigotWarden.json"/>
</p>

### Why ?
Since 1.17 spigot has started to "obfuscate" the nms classes so it is more complicated to use them because understanding what a(), b() or even aF() or af() does takes time, since this version spigot has also added the suffix '--remapped' when generating spigot with buildTools, EVERYTHING IS CLEARER! Unfortunately the servers can't read the plugins using the "Remapped" version of spigot and well here comes "Spigot Warden" which generates your plugin.jar but in a language understandable and compatible with your server!
<br>⚠️**This is Gradle plugin**⚠️
<p align="right">(<a href="#top">back to top</a>)</p>

### Summary
- [Usage](#Usage)
    - [Configuration](#Configuration)
    - [Tasks](#Tasks)
    - [add spigot library](#spigot)
- [License](#License)
<p align="right">(<a href="#top">back to top</a>)</p>

### Usage
> " *which sorcery should I use ?* "

To add it to your plugin you have to **use gradle** and add this line to your **build.gradle**
```gradle
plugins {
    id 'fr.shayfox.spigotwarden' version '1.0.0'
}
```
⚠️*The version above is not necessarily latest*⚠️
<br>
<br>
#### Configuration
Then you can configure the gradle plugin by adding these parameters:
```gradle
SpigotWarden {
    setMinecraftVersion("1.19-R0.1-SNAPSHOT") //this set targeted spigot version (if not specified, default value: "1.19-R0.1-SNAPSHOT")
    setBuildOutput(file('<path to my server>/plugins')) //this set Output directory (if not specified, default value: "<your plugin project>/build/libs")
}
```

Which this plugin you can generate remapped spigot with buildTools' Task.
This task will also generate the necessary files to use the plugin and if the files are missing, it will be automatically launched.

#### Tasks
- `buildTools`: this task generate Spigot remapped and all file necessary for `remapJar` task
- `remapJar`: this task build, obfuscate and deObfuscate your plugin to make it compatible with your servers
- `setup`: this task test and download all files necessary for SpecialSource and this plugin (it is automatically run before `remapJar` task)

<div id="spigot"></div>

#### Don't Forget to add Spigot remapped dependency !
*This plugin allows you to translate the methods and classes of spigot-remapped so that the spigot.jar of your servers can read them, so you have to add the spigot-remapped library to your jar like this*
```gradle
dependencies {
    implementation 'org.spigotmc:spigot:<spigotVersion>:remapped-mojang'
}
```
and add this two repository:
```gradle
repositories {
    mavenCentral()
    mavenLocal()
}
```

#### You're done!
You can now easily develop your plugin and create the plugin.jar with the `remapJar` task!
<br>*(An example is available in this reference)*
<p align="right">(<a href="#top">back to top</a>)</p>

### License
[GPL-3.0](https://choosealicense.com/licenses/gpl-3.0/)
<p align="right">(<a href="#top">back to top</a>)</p>
