package de.accso.loom.part3_threadlocal.framework;

import de.accso.loom.part3_threadlocal.context.CorrelationId;
import de.accso.loom.part3_threadlocal.context.RegionCode;
import de.accso.loom.part3_threadlocal.context.User;

public interface Callback {
    CorrelationId getCorrelationId();
    RegionCode    getRegion();
    User          getUser();
}
