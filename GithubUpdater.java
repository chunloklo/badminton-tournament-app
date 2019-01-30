import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

public class GithubUpdater {

    public static void run(String ... commands) throws InterruptedException,IOException {
        List<String> command = new ArrayList<String>();
        for (String s : commands) {
            command.add(s);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Map<String, String> environ = builder.environment();

        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
        System.out.println("Program terminated!");
    }

    public static void update() throws InterruptedException,IOException {
        java.util.Date date= new java.util.Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        run("git", "-C", "output/badminton", "status");
        run("git", "-C", "output/badminton", "add", "*");
        run("git", "-C", "output/badminton", "commit", "-m", "\"" + currentTime
            + " Updating Website\"");
        run("git", "-C", "output/badminton", "pull", "origin", "gh-pages");
        run("git", "-C", "output/badminton", "push", "origin", "gh-pages");
    }

}
    // public static void main(String[] args) {
    //     // run("git -C output/badminton commit -m \"Automatic Updater Worked!\""
    //     //     + "&& git -C output/badminton pull origin gh-pages"
    //     //     + "git -C output/badminton push origin gh-pages");
    //     run("git -C output/badminton commit -m \"Automatic Updater Worked!\"");
    //     run("git -C output/badminton pull origin gh-pages")
    // }