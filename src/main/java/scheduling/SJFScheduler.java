package scheduling;

import java.util.Collections;
import java.util.List;

/**
 * This class implements the SJF scheduler; 
 * SJF = Shortest Job First. 
 * Ties are broken alphabetically.
 */
public class SJFScheduler extends Scheduler {
    /**
     * To make a scheduler, you need a list of jobs.
     */
    public SJFScheduler(List<JobInfo> jobs) {
        super(jobs);
    }

    /**
     * How do we pick the first job?
     */
    @Override
    public JobInfo chooseJob(List<JobInfo> current) {
        Collections.sort(current, new ByDuration());
        return current.get(0);
    }
}