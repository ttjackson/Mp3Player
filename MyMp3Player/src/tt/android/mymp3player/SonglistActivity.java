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
import tt.android.songentity.SongInfo;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SonglistActivity extends Activity {

	private SongInfo songInfo = null;
	private List<SongInfo> infoList = new ArrayList<>();
	private String xml;
	private ListView songListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);
        songListView = (ListView)findViewById(R.id.Songlist);
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
			downloadXML();
		}
        return super.onOptionsItemSelected(item);
    }
    
    private void downloadXML() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpDownloader httpDownloader = new HttpDownloader();
				xml = httpDownloader.download("http://10.116.14.195:8080/MP3Player/resources.xml");
				System.out.println(xml);
				Message msg = myHandler.obtainMessage();
				msg.sendToTarget();
			}
		}).start();
	}
    
    Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			try {
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
	
	private void refreshList() {
		//Iterator<SongInfo> itr = infoList.iterator();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator<SongInfo> iterator = infoList.iterator(); iterator.hasNext();) {
			SongInfo info = (SongInfo)iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("song_name", info.getSongName());
			map.put("song_size", info.getSongSize());
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.simple_songlist_1, new String[]{"song_name","song_size"}, new int[]{R.id.mp3_name,R.id.mp3_size});
		songListView.setAdapter(simpleAdapter);
	}
	
}
