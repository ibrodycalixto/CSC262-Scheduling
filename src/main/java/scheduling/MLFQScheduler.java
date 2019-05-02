package scheduling;
import java.util.*;
import java.util.Random;

public class MLFQScheduler extends Scheduler{
    //overriding run makes life easy :)
    //the probability a job will move down a queue after being run
    private double prob;
    //the number of queues (priority  levels that will be run)
    private int numQ;


    public MLFQScheduler(List<JobInfo> jobs, double p, int k) {
        super(jobs);
        numQ = k;
        prob = p;
    }

    @Override
    public JobInfo chooseJob(List<JobInfo> current) {
        List<List<JobInfo>> queues = new ArrayList<>();
        for(int i = 0; i < numQ; i++) {
            queues.add(new ArrayList<JobInfo>());
        }
        for(JobInfo job : current)  {
            if (job.getIndex() == -1) {
                job.setIndex(0);
                queues.get(0).add(job);
            }else{
                queues.get(job.getIndex()).add(job);
            }
        }

        //find highest non-empty index list
        int runQ = 0;
        for (int i = 0; i < queues.size(); i++){
            if (!queues.get(i).isEmpty()){
                runQ = i;
            }
        }

        Scheduler scheduler = new RRScheduler(queues.get(runQ));
        JobInfo chosen = scheduler.chooseJob(queues.get(runQ));

        //randomly choose a double
        Random rand = new Random();
        double down = rand.nextDouble();

        //If less than prob and not already in lowest queue move job down a queue
        if ((down < prob) && (chosen.getIndex() < numQ-1)) {
            int newIndex = chosen.getIndex() + 1;
            chosen.setIndex(newIndex);
        }

        //check if lowest queue is full, move everything up
        if (current.size() == queues.get(numQ - 1).size()) {
            System.out.println("Bottom q full, reup");
            for (JobInfo job : queues.get(numQ - 1)) {
                job.setIndex(0);
            }
        }

        return chosen;
    }
}
