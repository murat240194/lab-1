import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ReduceRequest extends Reducer<IntWritable, RequestInfo, IntWritable,RequestInfo> {
    public void reduce(IntWritable key, Iterable<RequestInfo> values, Context context) throws IOException, InterruptedException{
        RequestInfo aggrigrate = new RequestInfo();
        for(RequestInfo value : values){
            aggrigrate.AddToSum(value.getByteSum().get());
        }

        aggrigrate.setIp(new LongWritable(key.get()));
        context.write(key, aggrigrate);
    }

}
