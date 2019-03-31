package scheduling;
import java.util.Comparator;

public class ByDuration implements Comparator<JobInfo>{
    @Override public int compare(JobInfo A, JobInfo B) {
        int comp =  Integer.compare(A.getDuration(), B.getDuration());
        if (comp == 0) {
            comp = A.getName().compareTo(B.getName());
        }
        return comp;
    }
}
