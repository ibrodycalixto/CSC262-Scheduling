package scheduling;
import java.util.Comparator;

public class ByArrival implements Comparator<JobInfo>{
    @Override public int compare(JobInfo A, JobInfo B) {
        int comp =  Integer.compare(A.getArrivalTime(), B.getArrivalTime());
        if (comp == 0) {
            comp = A.getName().compareTo(B.getName());
        }
        return comp;
    }
}
