package scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class SQMScache extends Scheduler{
    //time needs to be a hashmap
    private HashMap<String, List<String>> cpuTime;
    private List<String> cpuNames;
    private List<String> time;

    public SQMScache(List<JobInfo> jobs, List<String> cpuNames){
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
        return current.get(0);
    }

    @Override
    public void run() {
        while (!isDone()) {
            for (String name : cpuNames) {
                List<JobInfo> jobs = jobsAvailableNow();
                if (isDone()) {
                    break;
                } else if (jobs.isEmpty()) {
                    cpuTime.get(name).add("|");
                } else {
                    JobInfo job = chooseJob(jobs);
                    JobInfo rJob = evalJob(job, name, jobs);
                    if (!rJob.hasFinished()) {
                        rJob.setCPU(name);
                        cpuTime.get(name).add(rJob.getName());
                        rJob.runSingleStep();
                        time.add(rJob.getName());
                        rJob.setLastRun(this.getTime());
                        if (rJob.hasFinished()) {
                            rJob.setCompletion(this.getTime());
                        }
                    } else {
                        cpuTime.get(name).add("|");
                    }
                }
            }
        }
    }

    @Override
    public void printStats(String schedulerName){
        System.out.println("SQMS WITH CACHE AFFINITY");
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

    private JobInfo evalJob(JobInfo job, String name, List<JobInfo> jobs) {
        if (!job.getCPU().equals("")){
            for (JobInfo j : jobs){
                if (j.getCPU().equals(name) && !j.hasFinished()){
                    job = j;
                }
            }
        }
        return job;
    }



}

