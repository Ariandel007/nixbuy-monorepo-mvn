package com.mvnnixbuyapi.platform.model.entity;

import com.mvnnixbuyapi.platform.model.entity.valueobjects.PlatformId;
import com.mvnnixbuyapi.platform.model.entity.valueobjects.PlatformName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Platform {
    private PlatformId id;
    private PlatformName name;
}
