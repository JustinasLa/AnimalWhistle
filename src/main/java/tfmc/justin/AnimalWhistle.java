package tfmc.justin;

import me.Plugins.TLibs.Enums.APIType;
import me.Plugins.TLibs.Objects.API.ItemAPI;
import me.Plugins.TLibs.TLibs;
import org.bukkit.plugin.java.JavaPlugin;
import tfmc.justin.config.AnimalWhistleConfig;
import tfmc.justin.listeners.WhistleInteractListener;

public class AnimalWhistle extends JavaPlugin {
    
    private static AnimalWhistle instance;
    private ItemAPI api;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        api = (ItemAPI) TLibs.getApiInstance(APIType.ITEM_API);
        
        AnimalWhistleConfig config = new AnimalWhistleConfig(this);
        
        getServer().getPluginManager().registerEvents(new WhistleInteractListener(this, api, config), this);
        
        getLogger().info("AnimalWhistle plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AnimalWhistle plugin has been disabled!");
    }
    
    public static AnimalWhistle getInstance() {
        return instance;
    }
    
    public ItemAPI getApi() {
        return api;
    }
}
