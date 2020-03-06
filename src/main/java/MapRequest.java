import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MapRequest extends Mapper<LongWritable, Text, IntWritable, RequestInfo> {
    private final static IntWritable key = new IntWritable(1);
    private RequestInfo info;

    public void map(LongWritable ipKey, Text value, Context context) throws IOException, InterruptedException{
        info = new RequestInfo(value.toString());

        key.set((int)info.getIp().get());
        UserAgent browser = new UserAgent(value.toString());

        try{
            context.getCounter("Браузеры", browser.getBrowser().getName()).increment(1);
            context.write(key, info);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
