package com.github.joeypark8.catchcatch;

import org.bukkit.plugin.java.JavaPlugin;

import static com.github.joeypark8.catchcatch.command.CatchCommand.register;

public final class Catch_catch extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("Catch-catch is activated!!");

        register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
