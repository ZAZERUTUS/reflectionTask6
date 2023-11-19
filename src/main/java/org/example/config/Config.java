package org.example.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Config {
    protected String nameDb;
    protected String adminPassword;
    protected String adminUser;
    protected String hostDb;
    protected int portDb;
    protected String typeCache;
    protected int capacity;
}
