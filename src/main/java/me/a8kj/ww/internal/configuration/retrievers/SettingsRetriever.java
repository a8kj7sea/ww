package me.a8kj.ww.internal.configuration.retrievers;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.parent.configuration.ConfigValueRetriever;

@RequiredArgsConstructor
@Getter
public class SettingsRetriever implements ConfigValueRetriever<SettingsPathIdentifiers> {

    private final YamlConfiguration yamlConfiguration;

}
