package myPaint;

import java.awt.Rectangle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class VO_SeatChart implements Externalizable {
	private String backImgSrcPath;
	private ArrayList<Rectangle> rectangleList;

	public VO_SeatChart() {
	}

	public VO_SeatChart(String sourcePath, ArrayList<Rectangle> sourceList) {
		this.backImgSrcPath = sourcePath;
		this.rectangleList = sourceList;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(backImgSrcPath);
		out.writeObject(rectangleList);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		backImgSrcPath = (String) in.readObject();
		rectangleList = (ArrayList<Rectangle>) in.readObject();
	}

	public String getBackImgSrcPath() {
		return backImgSrcPath;
	}

	public ArrayList<Rectangle> getRectangleList() {
		return rectangleList;
	}
}
