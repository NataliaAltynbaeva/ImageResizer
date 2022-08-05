import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int newWidth = 300;

    public static void main(String[] args) {

        String srcFolder = args[0]; //enter your path here
        String dstFolder = args[1]; //enter your path here

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();

        int processors = numberOfProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processors);

        for (File file : files) {
            executorService.submit(new Resizer(file, newWidth, dstFolder));
        }

        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("Finished all files");
        } catch (InterruptedException e) {
            System.out.println("Program did not finish within 15 minutes");
        }
    }
    private static int numberOfProcessors() {
        Runtime runtime = Runtime.getRuntime();
        int numOfProcess = runtime.availableProcessors();
        return numOfProcess;
    }

}
