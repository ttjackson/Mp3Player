package tt.android.fileutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDCardRoot;

	public FileUtils() {
		// TODO Auto-generated constructor stub
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		System.out.println(SDCardRoot);
	}

	public File createFileInSDCard(String filename, String dir)
			throws IOException {
		String tempString = SDCardRoot + File.separator + dir + File.separator + filename;
		System.out.println(tempString);
		File file = new File(SDCardRoot + File.separator + dir + File.separator + filename);
		file.createNewFile();
		return file;
	}

	public File createSDDir(String dir) {
		System.out.println(SDCardRoot + File.separator + dir + File.separator);
		File dirFile = new File(SDCardRoot + File.separator + dir + File.separator);
		System.out.println(dirFile.mkdir());
		return dirFile;
	}

	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + File.separator + path + File.separator + fileName);
		return file.exists();
	}

	public File write2SDFromInput(String path, String fileName,
			InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return file;
	}
}
