package scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;


public class SQMScpu extends Scheduler{
    //time needs to be a hashmap actually
    private HashMap<String, List<String>> cpuTime;
    private List<String> cpuNames;
    private List<String> time;

    public SQMScpu(List<JobInfo> jobs, List<String> cpuNames){
        super(jobs);
        this.cpuTime = new HashMap<>();
        this.cpuNames = cpuNames;
        for (String name : cpuNames) {
            cpuTime.put(name, new ArrayList<>());
        }
        this.time = new ArrayList<>();
    }
    @Override
    public JobInfo chooseJob(List<JobInfo> current){
        Collections.sort(current, new ByLastRun());
        //System.out.println("chooseJob: "+current.get(0));
        return current.get(0);
    }

    @Override
    public void run() {
        while (!isDone()) {
            List<JobInfo> jobs = jobsAvailableNow();
            //debugging
            //System.out.println(cpuTime);
            //if (cpuTime.values().stream().anyMatch((x) -> x.size() > 100)) {
                //throw new RuntimeException();
            //}
            //
            for (String name : cpuNames) {
                if (jobs.isEmpty()) {
                    cpuTime.get(name).add("|");
                } else {
                    JobInfo job = chooseJob(jobs);
                    cpuTime.get(name).add(job.getName());
                    job.runSingleStep();
                    time.add(job.getName());
                    job.setLastRun(this.getTime());
                    if (job.hasFinished()) {
                        job.setCompletion(this.getTime());
                    }

                }
            }
        }
    }


    @Override
    public void printStats(String schedulerName){
        for (String name: cpuNames) {
            System.out.println(name);
            for (String time : cpuTime.get(name)) {
                System.out.print(time);
            }
            System.out.println("");
        }
    }

    public int getTime(){
        return time.size();
    }
}

