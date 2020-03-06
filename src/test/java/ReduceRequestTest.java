import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReduceRequestTest {
    private ReduceDriver<IntWritable, RequestInfo, IntWritable, RequestInfo> reduceDriver;
    @Before
    public void setUp() throws Exception {
        ReduceRequest reducer = new ReduceRequest();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    @Test
    public void reduce() throws IOException {
        List<RequestInfo> info = new ArrayList<>();
        info.add(new RequestInfo());
        info.add(new RequestInfo());
        info.add(new RequestInfo());
        info.get(0).setIp(new LongWritable(1));
        info.get(0).setByteSum(new LongWritable(40000));
        info.get(1).setIp(new LongWritable(1));
        info.get(1).setByteSum(new LongWritable(7000));
        info.get(2).setIp(new LongWritable(1));
        info.get(2).setByteSum(new LongWritable(13000));

        RequestInfo result = new RequestInfo();
        result.setIp(new LongWritable(1));
        result.setByteSum(new LongWritable(60000));
        result.setCol(new LongWritable(3));

        reduceDriver.withInput(new IntWritable(1), info);
        reduceDriver.withOutput(new IntWritable(1), result);
        reduceDriver.runTest();

    }
}