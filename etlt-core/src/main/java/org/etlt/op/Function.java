package org.etlt.op;

import org.etlt.job.JobContext;
@Deprecated
public interface Function {

    Object operate(JobContext context, Object ... args);

    void checkArgs(Object ... args);

}
