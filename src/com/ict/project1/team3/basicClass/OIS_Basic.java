package com.ict.project1.team3.basicClass;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class OIS_Basic<T> {
	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private ObjectInputStream ois = null;

	public VO_Basic<T> readObject(String pathName) {
		VO_Basic<T> result = null;

		if (checkPathName(pathName)) {
			try {
				openAll(pathName);

				// 객체 역직렬화
				result = (VO_Basic<T>) readObject();

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

	protected Object readObject() throws ClassNotFoundException, IOException {
		return ois.readObject();
	}
	
	protected void openAll(String pathName) throws Exception {
		File file = new File(pathName);
		fis = new FileInputStream(file);
		bis = new BufferedInputStream(fis);
		ois = new ObjectInputStream(bis);
	}

	protected boolean closeAll() {
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
