package hk.com.mtr.pcis.web.faces.util;

import java.io.*;
import java.util.*;

public class FileUtil {
	public static String getCurrentFileName(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) {
			return null;
		}
		String[] fileNames = file.list(new FilenameFilter() {
			public boolean accept(File f, String fileName) {

				String[] extNames = Constant.APP_FORM_EXT_NAME.split(",");
				for (String ext : extNames) {
					if (fileName.endsWith(ext))
						return true;
				}
				return false;

			}
		});

		List<String> fileNameList = Arrays.asList(fileNames);
		if (fileNameList != null && fileNameList.size() > 0)
			return fileNameList.get(0);
		else
			return null;
	}

	public static byte[] getResourceByte(String filePath) throws IOException {
		InputStream is = null;
		byte[] content = null;
		try {
			is = new FileInputStream(filePath);
			content = new byte[is.available()];
			is.read(content);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return content;
	}

	public static byte[] getResourceByte(File file) throws IOException {
		InputStream is = null;
		byte[] content = null;
		try {
			is = new FileInputStream(file);
			content = new byte[is.available()];
			is.read(content);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return content;
	}

	public static boolean moveFile(String source, String target) throws IOException {
		File oldFile = new File(source);
		File fileNewPath = new File(target);

		if (!fileNewPath.exists())
			if (!fileNewPath.mkdirs())
				return false;

		File newFile = new File(target + oldFile.getName());
		boolean isMoved = oldFile.renameTo(newFile);
		return isMoved;
	}
	
	public static byte[] getImageCompressByte(File file, final int originDPI, final int newDPI) throws IOException {
		ByteArrayOutputStream outputStream = null;
		byte[] content = null;
		try {			
			outputStream = new ByteArrayOutputStream();
			ImageCompress.resize(file, outputStream, originDPI,newDPI);
			content = outputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return content;
	}

}