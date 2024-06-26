package com.briup.hdfs.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SeqReaderTest extends Configured implements Tool {
	public int run(String[] strings) throws Exception {
		Configuration conf = getConf();
		/*
		 * conf.set("fs.defaultFS", "hdfs://192.168.29.132:9000");
		 */
		SequenceFile.Reader.Option op = SequenceFile.Reader.file(new Path(conf.get("path")));
		SequenceFile.Reader reader = new SequenceFile.Reader(conf, op);
		Writable key = (Writable) reader.getKeyClass().newInstance();
		Writable value = (Writable) reader.getValueClass().newInstance();

		long p = reader.getPosition();
		reader.sync(p);
		while (reader.next(key, value)) {
			String str = reader.syncSeen() ? "*" : "";
			System.out.println(str + "key:" + key + ",value:" + value);
			long pos = reader.getPosition();
			reader.sync(pos);
		}
		reader.close();
		return 0;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new SeqReaderTest(), args);
	}
}
