package tt.android.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {

	public String download(String urlStr) {
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		BufferedReader bufferReader = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			System.out.println(httpURLConnection.toString());
			bufferReader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream()));
			System.out.println(bufferReader);
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (Exception e) {
			// : handle exception
			e.printStackTrace();
		} finally {
			try {
				bufferReader.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return stringBuffer.toString();
	}

	public String readFormFile(String path) {
		System.out.println(path);
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		File file = new File(path);
		System.out.println(file.exists());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			while ((line = br.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return stringBuffer.toString();
	}
}

