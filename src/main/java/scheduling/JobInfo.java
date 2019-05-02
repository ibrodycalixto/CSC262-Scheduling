package scheduling;

public class JobInfo implements Comparable<JobInfo> {
    /** What is this job called? */
    private String name;
    /** What time does this job arrive? */
    private int arrivalTime;
    /** How long will this job run? */
    private int duration;
    /** How long has this job run so far? */
    private int timeRun;
    /** When did this job finish? */
    private int completion;
    /** When did this job last run */
    private int lastRun;
    /** The number of tickets this job owns*/
    private double weight;
    /**index of the job**/
    private int index;
    /**current CPU**/
    private String CPU;


//@Override
//public String toString() {
    //return "JobInfo("+name+", "+lastRun+", "+duration+", tr="+timeRun+", "+hasFinished()+")";
//}

    /**
     * Construct a new job specification:
     */
    public JobInfo(String name, int arrivalTime, int duration, String CPU) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.timeRun = 0;
        this.completion = 0;
        this.index = -1;
        this.CPU = CPU;
    }
    
    /** Get the name of this job. */
    public String getName() {
        return this.name;
    }

    /**
     * Copy this job, ignoring "timeRun!"
     */
    public JobInfo copy() {
        return new JobInfo(this.name, this.arrivalTime, this.duration, this.CPU);
    }

    /** Has the job arrived at time "now"? */
    public boolean hasArrived(int now) {
        return this.arrivalTime <= now;
    }

    /** Is this job done? */
    public boolean hasFinished() {
        return this.duration <= this.timeRun;
    }

    /** Update this job's time spent running. */
    public void runSingleStep() {
        this.timeRun++;
    }

    /** 
     * Arrival time getter. 
     * @return the arrivalTime
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Duration of the job.
     * @return the duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Time the job has run
     * @return the timeRun
     */
    public int getTimeRun(){ return timeRun;}

    /**
     * When a job finishes, set completion time.
     */
    public void setCompletion(int now){completion = now;}

    /**
     * When a job finished
     * @return completion
     */
    public int getTurnaround() {return completion - arrivalTime;}

    /**
     * Set the latest runtime
     */
    public void setLastRun(int now) {lastRun = now;}

    /**
     * Get the last time the job was run
     * @return lastRun
     */
    public int getLastRun(){return lastRun;}

    /**
     * Set the weight
     */
    public void setWeight(double tix){weight = tix;}

    /**
     * Get the weight
     * @return weight
     */

    public double getWeight(){return weight;}

    /**
     * Set the index
     */
    public void setIndex(int q){index = q;}

    /**
     * Get the index
     * @return index
     */
    public int getIndex(){return index;}



    @Override
    public int compareTo(JobInfo o) {
        int byArrival = Integer.compare(this.arrivalTime, o.arrivalTime);
        if (byArrival == 0) {
            return this.name.compareTo(o.name);
        }
        return byArrival;
    }
}