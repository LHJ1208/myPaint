package com.ict.project1.team3.basicClass;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class VO_Basic<T> implements Externalizable {
	private String filePath;
	private ArrayList<T> dataList;

	public VO_Basic() {
	}

	public VO_Basic(String sourcePath, ArrayList<T> sourceList) {
		this.filePath = sourcePath;
		this.dataList = sourceList;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(filePath);
		out.writeObject(dataList);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		filePath = (String) in.readObject();
		dataList = (ArrayList<T>) in.readObject();
	}

	public String getBackImgSrcPath() {
		return filePath;
	}

	public ArrayList<T> getRectangleList() {
		return dataList;
	}
}
