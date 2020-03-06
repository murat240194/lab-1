import org.apache.commons.configuration.Configuration;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.ToolRunner;

import javax.ws.rs.Path;

public class Program {
    public static void main(String[] args) throws Exception
    {
        System.out.println(args[0] + " " + args[1]);
        ToolRunner.run(new RequestCounter(), args);
    }
}
