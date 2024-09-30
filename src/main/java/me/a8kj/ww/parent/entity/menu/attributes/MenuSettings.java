package me.a8kj.ww.parent.entity.menu.attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.a8kj.ww.parent.entity.menu.enums.MenuSize;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSettings {
    private String title;
    private MenuSize menuSize;
}
