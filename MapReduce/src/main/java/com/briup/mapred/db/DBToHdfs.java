package com.briup.mapred.db;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class DBToHdfs extends Configured 
	implements Tool{
	public static void main(String[] args)
			throws Exception {
		ToolRunner.run(new DBToHdfs(), args);
	}
	@Override
	public int run(String[] arg0) throws Exception {
		Configuration conf = getConf();
		Job job = Job.getInstance(conf,"showTable");
		job.setJarByClass(this.getClass());
		
		job.setMapperClass(ShowTableMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass
			(YearStat.class);
		
		
		DBConfiguration.configureDB
			(job.getConfiguration(), 
			"oracle.jdbc.driver.OracleDriver", 
			"jdbc:oracle:thin:@192.168.117.60:1521:XE",
			"briup", "briup");
		
		DBInputFormat.setInput
			(job, YearStat.class,
				"maxtmp", 
				"tmp < 9999", 
				"tmp", 
				"year","stationId","tmp");
		//select year,stationId,tmp 
		// from maxtmp
		// where tmp < 9999
		// order by tmp
		job.setInputFormatClass
			(DBInputFormat.class);
		
		job.setOutputFormatClass
			(TextOutputFormat.class);
		TextOutputFormat.setOutputPath
			(job, new Path(conf.get("outpath")));
		return job.waitForCompletion(true)?0:1;
	}
	public static class ShowTableMapper 
		extends Mapper<LongWritable, YearStat, 
		LongWritable, YearStat>{
		@Override
		protected void map(LongWritable key, YearStat value,
				Mapper<LongWritable, YearStat, LongWritable, YearStat>.Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}
}






