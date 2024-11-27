package de.accso.loom.part1_threadlocal.framework;

import de.accso.loom.part1_threadlocal.context.CorrelationId;
import de.accso.loom.part1_threadlocal.context.RegionCode;
import de.accso.loom.part1_threadlocal.context.User;

public interface Callback {
    CorrelationId getCorrelationId();
    RegionCode    getRegion();
    User          getUser();
}
