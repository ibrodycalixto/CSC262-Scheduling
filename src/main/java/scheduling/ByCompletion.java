package scheduling;
import java.util.Comparator;

public class ByCompletion implements Comparator<JobInfo>{
    @Override public int compare(JobInfo A, JobInfo B) {
        int ADoneIn = A.getDuration() - A.getTimeRun();
        int BDoneIn = B.getDuration() - B.getTimeRun();
        int comp = Integer.compare(ADoneIn, BDoneIn);
        if (comp == 0) {
            comp = A.getName().compareTo(B.getName());
        }
        return comp;
    }
}
