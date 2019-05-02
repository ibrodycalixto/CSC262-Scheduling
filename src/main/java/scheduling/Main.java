package scheduling;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;

/**
 * This class reads input and runs the simulation.
 */
public class Main {


  /**
   * Crash with an error message.
   */
  public static void die(String message) {
    throw new RuntimeException(message);
  }


  /**
   * Parse to int or crash with an error message.
   */
  public static int intOrDie(String data) {
    try {
      return Integer.parseInt(data);
    } catch(NumberFormatException nfe) {
      throw new RuntimeException("Couldn't make "+data+" into an int.", nfe);
    }
  }

  /**
   * Parse to double or crash with an error message.
   */
  public static double doubleOrDie(String data) {
    try {
      return Double.parseDouble(data);
    } catch(NumberFormatException nfe) {
      throw new RuntimeException("Couldn't make "+data+" into an double.", nfe);
    }
  }


  /**
   * Main entry point.
   */
  public static void main(String[] args) throws IOException {
    double p = .5;
    int k = 3;
    String inputPath = "fig_7_1.jobs";
    if (args.length == 1) {
      inputPath = args[0];
    }

    //Build up a list of all jobs reading from file.
    List<JobInfo> allJobs = new ArrayList<>();

    //Build up a list of CPU names
    List<String> cpuNames = new ArrayList<>();

    // Loop through every line and parse out the job description.
    for (String line : Files.readAllLines(new File(inputPath).toPath())) {
      String[] data = line.trim().split("\\s+");
      // skip blank lines
      if (data.length == 0) {
        continue;
      }
      // require five or two fields
      if ((data.length != 5) && (data.length != 2)) {
        die("Unknown line length: "+line);
      }
      if (data[0].equals("job")) {
        JobInfo job = new JobInfo(data[1], intOrDie(data[2]), intOrDie(data[3]), data[4]);
        //add to all job list
        allJobs.add(job);
        if (cpuNames.isEmpty()) {
          cpuNames.add(data[4]);
        } else {
          //this is not super elegant but it works SO
          int t = 0;
          for (String name : cpuNames) {
            if (name.equals(data[4])) {
              t += 1;
              break;
            }
          }
          //if not repeated
          if (t == 0) {
            cpuNames.add(data[4]);
          }
        }
      }
      //assign configurable MLFQ values
      else if (data.length == 2) {
          p = (doubleOrDie(data[0]));
          k = (intOrDie(data[1]));
      } else {
        die("Unknown line: "+line);
      }
    }

    // Create a scheduler
    for (Scheduler sched : Arrays.asList(
            new FIFOScheduler(allJobs),
            new SJFScheduler(allJobs),
            new STCFScheduler(allJobs),
            new RRScheduler(allJobs),
            new LottoScheduler(allJobs),
            new MLFQScheduler(allJobs, p, k),
            new SQMScpu(allJobs, cpuNames)
    )) {
      sched.run();
      sched.printStats(sched.getClass().getSimpleName());
    }


  }
}
