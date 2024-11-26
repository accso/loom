package de.accso.loom.part2_scopedvalues.framework;

import de.accso.loom.part2_scopedvalues.context.RegionCode;
import de.accso.loom.part2_scopedvalues.context.User;

import java.util.UUID;

public interface Callback {
    UUID       getCorrelationId();
    RegionCode getRegion();
    User       getUser();
}
