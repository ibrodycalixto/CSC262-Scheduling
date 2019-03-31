package scheduling;

import java.util.Collections;
import java.util.List;

public class STCFScheduler extends Scheduler {

    public STCFScheduler(List<JobInfo> jobs) {
        super(jobs);
    }

    @Override
    public JobInfo chooseJob(List<JobInfo> current) {
        Collections.sort(current, new ByCompletion());
        return current.get(0);
    }
}
