package myPaint;

import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.CopyOnWriteArrayList;

// ObjectInputStream : 객체 입력 스트림
// readObject() : 객체 역직렬화 메서드

public class OIS_Rectangle {
	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private ObjectInputStream ois = null;

	public Rectangle readRectangle(String pathName) {
		Rectangle rect = null;
		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 객체 역직렬화
				rect = ((VO_Rectangle) ois.readObject()).getRectangle();

				// 개발자 확인용 코드
				System.out.print("x : " + rect.x + "\t ");
				System.out.print("y : " + rect.y + "\t ");
				System.out.print("width : " + rect.width + "\t ");
				System.out.println("height : " + rect.height);
			} catch (Exception e) {
				e.printStackTrace();
				rect = null;
			} finally {
				if (!closeAll()) {
					rect = null;
				}
			}
		}
		return rect;
	}

	@SuppressWarnings("unchecked")
	public CopyOnWriteArrayList<Rectangle> readRectangleList(String pathName) {
		CopyOnWriteArrayList<Rectangle> list = new CopyOnWriteArrayList<>();

		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 객체 역직렬화
				for (VO_Rectangle rectangle : (CopyOnWriteArrayList<VO_Rectangle>) ois.readObject()) {
					list.add(rectangle.getRectangle());
				}

				// 개발자 확인용 코드
				for (Rectangle k : list) {
					System.out.print("x : " + k.x + "\t ");
					System.out.print("y : " + k.y + "\t ");
					System.out.print("width : " + k.width + "\t ");
					System.out.println("height : " + k.height);
				}
			} catch (Exception e) {
				e.printStackTrace();
				list = null;
			} finally {
				if (!closeAll()) {
					list = null;
				}
			}
		}

		return list;
	}

	private void openAll(String pathName) throws Exception {
		File file = new File(pathName);
		fis = new FileInputStream(file);
		bis = new BufferedInputStream(fis);
		ois = new ObjectInputStream(bis);
	}

	private boolean closeAll() {
		boolean result = true;
		try {
			if (ois != null) {
				ois.close();
				ois = null;
			}
			if (bis != null) {
				bis.close();
				bis = null;
			}
			if (fis != null) {
				fis.close();
				fis = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public boolean checkPathName(String pathName) {
		boolean result = true;
		String trimPathName = pathName.trim();
		if (pathName == null || trimPathName.equals("null") || trimPathName.equals("nullnull")
				|| trimPathName.length() < 1) {
			result = false;
		}
		return result;
	}
}