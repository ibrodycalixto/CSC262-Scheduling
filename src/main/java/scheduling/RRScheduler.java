package scheduling;
import java.util.Collections;
import java.util.List;

public class RRScheduler extends Scheduler{
    public RRScheduler(List<JobInfo> jobs){
        super(jobs);
    }
    @Override
    public JobInfo chooseJob(List<JobInfo> current) {
        Collections.sort(current, new ByLastRun());
        return current.get(0);
    }
}
