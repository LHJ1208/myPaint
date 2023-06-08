package myPaint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

// ObjectInputStream : 객체 입력 스트림
// readObject() : 객체 역직렬화 메서드

public class OIS_SeatChart {
	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private ObjectInputStream ois = null;

	public VO_SeatChart readSeatChart(String pathName) {
		VO_SeatChart result = null;

		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 객체 역직렬화
				result = (VO_SeatChart) ois.readObject();

			} catch (Exception e) {
				e.printStackTrace();
				result = null;
			} finally {
				if (!closeAll()) {
					result = null;
				}
			}
		}
		return result;
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