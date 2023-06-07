package myPaint;

import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

//ObjectInputStream : 객체 출력 스트림
//writeObject : 객체 직렬화 메서드

public class OOS_Rectangle {
	private FileOutputStream fos = null;
	private BufferedOutputStream bos = null;
	private ObjectOutputStream oos = null;

	public boolean writeRectangle(Rectangle source, String pathName) {
		boolean result = true;

		// 1. 객체 복사
		VO_Rectangle vo = new VO_Rectangle(source);

		// 2. 객체 직렬화를 한 후 파일에 저장한다.
		// 직렬화가 된 정보는 볼 수 없다.
		// 읽을 때 역직렬화를 해야 볼 수 있다.
		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 3. 객체 직렬화
				oos.writeObject(vo);
				oos.flush();
			} catch (Exception e) {
				result = false;
			} finally {
				if (!closeAll()) {
					result = false;
				}
			}
		}

		return result;
	}

	public boolean writeRectangleList(CopyOnWriteArrayList<Rectangle> source, String pathName) {
		boolean result = true;

		// 1. 객체 복사
		CopyOnWriteArrayList<VO_Rectangle> list = new CopyOnWriteArrayList<>();
		for (Rectangle rectangle : source) {
			list.add(new VO_Rectangle(rectangle));
		}

		// 2. 모은 객체들을 직렬화를 한 후 파일에 저장한다.
		// 직렬화가 된 정보는 볼 수 없다.
		// 읽을 때 역직렬화를 해야 볼 수 있다.
		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 3. 객체 직렬화
				oos.writeObject(list);
				oos.flush();
			} catch (Exception e) {
				result = false;
			} finally {
				if (!closeAll()) {
					result = false;
				}
			}
		}

		return result;
	}

	private void openAll(String pathName) throws Exception {
		File file = new File(pathName);
		fos = new FileOutputStream(file);
		bos = new BufferedOutputStream(fos);
		oos = new ObjectOutputStream(bos);
	}

	private boolean closeAll() {
		boolean result = true;
		try {
			if (oos != null) {
				oos.close();
				oos = null;
			}
			if (bos != null) {
				bos.close();
				bos = null;
			}
			if (fos != null) {
				fos.close();
				fos = null;
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
