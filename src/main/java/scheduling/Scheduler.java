package scheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * An algorithm (and tracking) for JobInfo descriptions.
 */
public abstract class Scheduler {
    /** What jobs do I have? */
    private List<JobInfo> jobs;

    /** What jobs have run at each timestep? */
    private List<String> time;

    /** Array of Turnaround Times */
    private List<Integer> turnaround;

    /** What time is it? */
    public int getTime() {
        return this.time.size();
    }


    /**
     * @param jobs the list of job information from the file.
     */
    public Scheduler(List<JobInfo> jobs) {
        this.time = new ArrayList<>();
        this.jobs = new ArrayList<>();
        // deep-copy jobs:
        for (JobInfo j : jobs) {
            this.jobs.add(j.copy());
        }
        this.turnaround = new ArrayList<>();
    }

    /**
     * What jobs are available right now for me to schedule?
     */
    public List<JobInfo> jobsAvailableNow() {
        List<JobInfo> relevant = new ArrayList<>();
        for (JobInfo job : this.jobs) {
            if (!job.hasFinished() && job.hasArrived(this.getTime())) {
                relevant.add(job);
            }
        }
        return relevant;
    }

    /** A scheduler is done when all jobs are done. */
    protected boolean isDone() {
        for (JobInfo job : this.jobs) {
            // If any job is not done, the answer is no.
            if (!job.hasFinished()) {
                return false;
            }
        }
        // All jobs must be done!
        return true;
    }
    
    /** 
     * Implemented by specific schedulers.
     * Choose job for next time-slice.
     */
    public abstract JobInfo chooseJob(List<JobInfo> current);

    /** Loop until all jobs have "finished" choosing a job to run next. */
    public void run() {
        while(!isDone()) {
            List<JobInfo> jobs  = jobsAvailableNow();
                JobInfo job = chooseJob(jobs);
                time.add(job.getName());
                job.runSingleStep();
                job.setLastRun(this.getTime());
                if (job.hasFinished()) {
                    job.setCompletion(this.getTime());
                    turnaround.add(job.getTurnaround());
            }
        }
    }

    /** The information about the job. */
    public void printStats(String schedulerName) {
        StringBuilder sb = new StringBuilder();
        for (String name : time) {
            sb.append(name);
        }
        System.out.println("Timeline(" + schedulerName + "): "+sb);
        int totTurn = 0;
        for (int i : turnaround){
            totTurn = totTurn + i;
        }
        System.out.println("Average Turnaround Time: " + (totTurn/jobs.size()));
    }
}