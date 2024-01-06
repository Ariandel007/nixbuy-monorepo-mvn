package com.mvnnixbuyapi.platform.model.entity;

import com.mvnnixbuyapi.platform.model.entity.valueobjects.PlatformId;
import com.mvnnixbuyapi.platform.model.entity.valueobjects.PlatformName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
    private PlatformId id;
    private PlatformName name;

    public Platform(Long id, String name) {
        this.id = new PlatformId(id);
        this.name = new PlatformName(name);
    }
}
