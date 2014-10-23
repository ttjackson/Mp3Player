package tt.android.service;

import tt.android.downloader.HttpDownloader;
import tt.android.songentity.SongInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		SongInfo songInfo = (SongInfo) intent.getSerializableExtra("Mp3Info");
		DownloadThread downloadThread = new DownloadThread(songInfo);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable {
		SongInfo songInfo;
		public DownloadThread(SongInfo songInfo) {
			// TODO Auto-generated constructor stub
			this.songInfo = songInfo;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String urlString = "http://10.116.14.195:8080/MP3Player/" + songInfo.getSongName();
			HttpDownloader httpDownloader = new HttpDownloader();
			httpDownloader.downloadFile(urlString, "mp3/", songInfo.getSongName());
		}
	}
}
