package me.a8kj.ww.internal.plugin;

import java.util.Map;
import java.util.logging.*;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

@RequiredArgsConstructor
@Getter
public class WWPluginProvider implements PluginProvider {

    private final Logger logger;
    private final JavaPlugin plugin;
    @Override
    public Map<String, Configuration> getConfigurations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConfigurations'");
    }
}
