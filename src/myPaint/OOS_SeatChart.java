package myPaint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

//ObjectInputStream : 객체 출력 스트림
//writeObject : 객체 직렬화 메서드

public class OOS_SeatChart {
	private FileOutputStream fos = null;
	private BufferedOutputStream bos = null;
	private ObjectOutputStream oos = null;

	public boolean writeSeatChart(String pathName, VO_SeatChart source) {
		boolean result = true;

		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 객체 직렬화
				oos.writeObject(source);
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
