package com.bgsoftware.superiorskyblock.modules.generators;

import com.bgsoftware.common.config.CommentedConfiguration;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.modules.BuiltinModule;
import com.bgsoftware.superiorskyblock.modules.generators.commands.CmdAdminAddGenerator;
import com.bgsoftware.superiorskyblock.modules.generators.commands.CmdAdminClearGenerator;
import com.bgsoftware.superiorskyblock.modules.generators.commands.CmdAdminSetGenerator;
import com.bgsoftware.superiorskyblock.modules.generators.listeners.GeneratorsListener;
import org.bukkit.event.Listener;

import java.io.File;

public final class GeneratorsModule extends BuiltinModule {

    private boolean enabled = true;

    public GeneratorsModule(){
        super("generators");
    }

    @Override
    public void onEnable(SuperiorSkyblockPlugin plugin) {
    }

    @Override
    public Listener[] getModuleListeners() {
        return !enabled ? null : new Listener[] {new GeneratorsListener(plugin, this)};
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands() {
        return null;
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands() {
        return !enabled ? null : new SuperiorCommand[]{new CmdAdminAddGenerator(), new CmdAdminClearGenerator(), new CmdAdminSetGenerator()};
    }

    @Override
    public void onDisable() {
    }

    @Override
    protected void onPluginInit() {
        super.onPluginInit();

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        CommentedConfiguration config = CommentedConfiguration.loadConfiguration(configFile);

        if(config.contains("generators")){
            super.config.set("enabled", config.getBoolean("generators"));
            config.set("generators", null);

            File moduleConfigFile = new File(getDataFolder(), "config.yml");

            try{
                super.config.save(moduleConfigFile);
                config.save(configFile);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public boolean isEnabled() {
        return enabled && isInitialized();
    }

    protected void updateConfig(){
        enabled = config.getBoolean("enabled");
    }

}
