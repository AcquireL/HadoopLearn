package com.briup.mapred.join.reduceside;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class IdTagGroupComparator 
	extends WritableComparator{
	public IdTagGroupComparator() {
		super(IdTag.class,true);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable a, 
		WritableComparable b) {
		IdTag it1 = (IdTag)a;
		IdTag it2 = (IdTag)b;
		return it1.getId().compareTo(it2.getId());
	}
}


