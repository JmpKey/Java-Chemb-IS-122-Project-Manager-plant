package org.example.plant.protocol;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public interface Adjustment {
    List<String> readConfigValuesDb() throws IOException;

    List<String> readConfigValuesMail();
}
