import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class RequestInfo implements Writable {
    private LongWritable ip;
    private LongWritable col;
    private LongWritable byteSum;

    public RequestInfo(){
        ip = new LongWritable(0);
        col = new LongWritable(0);
        byteSum = new LongWritable(0);
    }

    public RequestInfo(String inputValue){
        String[] inputValues = inputValue.split(" ");
        this.ip = new LongWritable(Long.parseLong(inputValues[0].substring(2)));
        if (inputValues[9].equals("-")){
            this.byteSum = new LongWritable(0);
        }
        else{
            this.byteSum = new LongWritable(Long.parseLong(inputValues[9]));
        }
        col = new LongWritable(0);
    }

    public LongWritable getIp(){
        return ip;
    }
    public void setIp(LongWritable ip)
    {
        this.ip = ip;
    }

    public LongWritable getCol(){
        return col;
    }
    public void setCol(LongWritable col){
        this.col = col;
    }

    public LongWritable getByteSum(){
        return byteSum;
    }
    public void setByteSum(LongWritable byteSum){
        this.byteSum = byteSum;
    }

    public void AddToSum(long sum){
        byteSum.set(byteSum.get() + sum);
        col.set(col.get() + 1);
    }
    @Override
    public String toString(){
        int temp = (int)col.get();
        if(temp == 0) temp = 1;
        return (byteSum.get() / temp) + " " + byteSum;
    }

    public int HashCode(){
        return Objects.hash(ip, col, byteSum);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        ip.write(dataOutput);
        col.write(dataOutput);
        byteSum.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        ip.readFields(dataInput);
        col.readFields(dataInput);
        byteSum.readFields(dataInput);
    }
}
