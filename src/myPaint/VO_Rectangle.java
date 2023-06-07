package myPaint;

import java.awt.Rectangle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class VO_Rectangle implements Externalizable {
	private Rectangle rectangle;

	public VO_Rectangle() {
	}

	public VO_Rectangle(Rectangle seat) {
		this.rectangle = seat;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(rectangle);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		rectangle = (Rectangle) in.readObject();
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
}
