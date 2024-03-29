package com.briup.knn;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * Knn第四步：
 *        通过相识度进行排序
 *        1.输入位置：hdfs文件系统：/knn_data/gsd_result/part-r-00000
 *        2.输出位置：hdfs文件系统：/knn_data/gsd_result_sorted
 */
public class SortByDegree extends Configured  implements Tool{
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new SortByDegree(), args);
	}
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf=getConf();
		Job job=Job.getInstance(conf,"sdb");
		job.setJarByClass(this.getClass());
		job.setMapperClass(SBDMapper.class);
		job.setMapOutputKeyClass(TagDegree.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		SequenceFileInputFormat.addInputPath(job, new Path("/knn_data/gsd_result/part-r-00000"));
		TextOutputFormat.setOutputPath(job, new Path("/knn_data/gsd_result_sorted"));
		
		job.setGroupingComparatorClass(TagDegreeGroupComparator.class);
		job.waitForCompletion(true);
		return 0;
	}
	public static class SBDMapper extends Mapper<LongWritable,Text,TagDegree,NullWritable>{
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, TagDegree, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String line=value.toString();
			String[] infos=line.split("\t");
			String tag=infos[0];
			String degree=infos[1];
			TagDegree tg=new TagDegree(tag,Double.parseDouble(degree));
			//tg默认调用toString
			context.write(tg, NullWritable.get());
		}
	}
}
