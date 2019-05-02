package scheduling;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LottoScheduler extends Scheduler{

    public LottoScheduler(List<JobInfo> jobs){
        super(jobs);
    }
    @Override
    public JobInfo chooseJob(List<JobInfo> current){
        List<Double> stack = new ArrayList<>();
        double total = 0;
        for(JobInfo job : current) {
            total = total + job.getDuration();
        }
        for(JobInfo job : current){
            job.setWeight(job.getDuration()/total);
        }
        double last = 0;
        for(JobInfo job : current){
            last += job.getWeight();
            stack.add(last);
        }
        Random rand = new Random();
        double lotto = rand.nextDouble();
        JobInfo chosen = current.get(0);
        for(int i = 0; i < stack.size(); i++){
            if(lotto < stack.get(i)) {
                 chosen = current.get(i);
                 break;
            }
        }
        return chosen;
    }
}
