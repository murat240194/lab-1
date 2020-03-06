import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobContext;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

public class RequestCounter extends Configured implements Tool {
    public int run(String[] args) throws Exception{
        if(args.length != 2){
            System.err.printf("Использование: %s нужно два аргумента, ввод и вывод" + "файла\n", getClass().getSimpleName());
            return -1;
        }
        Job job = new Job();
        job.setJarByClass(RequestCounter.class);
        job.setJobName("Counter");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(RequestInfo.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(MapRequest.class);
        job.setReducerClass(ReduceRequest.class);

        int value = job.waitForCompletion(true) ? 0:1;

        for(CounterGroup group : job.getCounters())
        {
            for(Counter counter : group){
                System.out.println(counter.getName() + " " + counter.getValue());
            }
        }

        if(job.isSuccessful()){
            System.out.println("Работа была успешной");
        }else if(!job.isSuccessful()){
            System.out.println("Работа не была успешной");
        }

        return value;
    }

}
