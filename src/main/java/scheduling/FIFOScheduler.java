package scheduling;

import java.util.Collections;
import java.util.List;

/**
 * This class implements the FIFO or FCFS scheduler; 
 * FIFO = First job in-first job out. 
 * Ties are broken alphabetically.
 */
public class FIFOScheduler extends Scheduler {
    /**
     * To make a scheduler, you need a list of jobs.
     */
    public FIFOScheduler(List<JobInfo> jobs) {
        super(jobs);
    }

    /**
     * How do we pick the first job?
     */
    @Override
    public JobInfo chooseJob(List<JobInfo> current) {
        Collections.sort(current, new ByArrival());
        return current.get(0);
    }
}