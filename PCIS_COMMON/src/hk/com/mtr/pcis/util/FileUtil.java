package hk.com.mtr.pcis.util;

import java.io.*;

import org.apache.tools.ant.util.FileUtils;

public class FileUtil {

	public static boolean moveFile(String source, String target) throws IOException {
		boolean isMoved = false;
		try {			
			File oldFile = new File(source);
			File fileNewPath = new File(target);
	
			if (!fileNewPath.exists())
				if (!fileNewPath.mkdirs())
					return false;
	
			File newFile = new File(target + oldFile.getName());
			
			FileUtils.getFileUtils().copyFile(oldFile, newFile);
			FileUtils.delete(oldFile);
			isMoved = true;
		}catch(IOException ex){
			throw ex;
		}
		return isMoved;
	}

}