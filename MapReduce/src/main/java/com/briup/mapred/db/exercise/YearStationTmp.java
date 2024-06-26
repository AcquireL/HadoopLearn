package com.briup.mapred.db.exercise;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class YearStationTmp implements DBWritable, WritableComparable<YearStationTmp> {
	private IntWritable year = new IntWritable();
	private Text stationId = new Text();
	private DoubleWritable tmp = new DoubleWritable();

	public YearStationTmp() {
	}

	public YearStationTmp(IntWritable year, Text stationId, DoubleWritable tmp) {
		this.year = new IntWritable(year.get());
		this.stationId = new Text(stationId.toString());
		this.tmp = new DoubleWritable(tmp.get());
	}

	public YearStationTmp(String year, String stationId, double tmp) {
		this.year = new IntWritable(Integer.parseInt(year));
		this.stationId = new Text(stationId);
		this.tmp = new DoubleWritable(tmp);
	}

	public IntWritable getYear() {
		return year;
	}

	public void setYear(IntWritable year) {
		this.year = new IntWritable(year.get());
	}

	public Text getStationId() {
		return stationId;
	}

	public void setStationId(Text stationId) {
		this.stationId = new Text(stationId.toString());
	}

	public DoubleWritable getTmp() {
		return tmp;
	}

	public void setTmp(DoubleWritable tmp) {
		this.tmp = new DoubleWritable(tmp.get());
	}

	@Override
	public void write(DataOutput out) throws IOException {
		year.write(out);
		stationId.write(out);
		tmp.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		year.readFields(in);
		stationId.readFields(in);
		tmp.readFields(in);
	}

	@Override
	public int compareTo(YearStationTmp o) {
		return this.year.compareTo(o.year) == 0 ? this.stationId.compareTo(o.stationId) : this.year.compareTo(o.year);
	}

	@Override
	public String toString() {
		return year + "\t" + stationId + "\t" + tmp;
	}

	@Override
	public void write(PreparedStatement prep) throws SQLException {
		prep.setInt(1, this.year.get());
		prep.setString(2, this.stationId.toString());
		prep.setDouble(3, this.tmp.get());
	}

	@Override
	public void readFields(ResultSet resultSet) throws SQLException {
		if (resultSet == null) {
			return;
		}
		this.year = new IntWritable(resultSet.getInt(1));
		this.stationId=new Text(resultSet.getString(2));
		this.tmp=new DoubleWritable(resultSet.getDouble(3));
	}
}

