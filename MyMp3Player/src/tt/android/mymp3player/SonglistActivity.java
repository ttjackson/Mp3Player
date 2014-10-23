package tt.android.mymp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import tt.android.XMLHandler.XMLHandler;
import tt.android.downloader.HttpDownloader;
import tt.android.service.DownloadService;
import tt.android.songentity.SongInfo;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SonglistActivity extends Activity {

	private List<SongInfo> infoList = new ArrayList<SongInfo>();
	private String xml;
	private ListView songListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_songlist);
		songListView = (ListView) findViewById(R.id.Songlist);
		songListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SongInfo songInfo = infoList.get(position);
				Intent intent = new Intent();
				intent.putExtra("Mp3Info", songInfo);
				intent.setClass(SonglistActivity.this, DownloadService.class);
				startService(intent);
			}
		});
		updateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.songlist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.refreshlist) {
			updateList();
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateList() {
		downloadXML();
	}

	private void downloadXML() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				HttpDownloader httpDownloader = new HttpDownloader();
				xml = httpDownloader
						.download("http://192.168.1.14:8080/MP3Player/resources.xml");
				System.out.println(xml);
				Message msg = myHandler.obtainMessage();
				msg.sendToTarget();
			}
		}).start();
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				infoList.clear();
				XMLHandler xmlHandler = new XMLHandler(infoList);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				parser.parse(new InputSource(new StringReader(xml)), xmlHandler);
				refreshList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.toString());
			}
			System.out.println(infoList.toString());
		};
	};
	
	public Handler downloadResultHandler = new Handler() {
		public void handleMessage(Message msg) {
			NotificationManager nManager = (NotificationManager) SonglistActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification();
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nManager.notify(1,notification);
		};
	};

	private void refreshList() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		for (Iterator<SongInfo> iterator = infoList.iterator(); iterator
				.hasNext();) {
			SongInfo info = (SongInfo) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("song_name", info.getSongName());
			map.put("song_size", info.getSongSize());
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,	R.layout.simple_songlist_1, 
				new String[] { "song_name",	"song_size" }, new int[] { R.id.mp3_name, R.id.mp3_size });
		songListView.setAdapter(simpleAdapter);
	}

	

}
