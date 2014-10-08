package tt.android.XMLHandler;

import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tt.android.songentity.SongInfo;

public class XMLHandler extends DefaultHandler{
	private List<SongInfo> infoList = null;
	private SongInfo info = null;
	private String tagName;

	public XMLHandler(List<SongInfo> infoList) {
		super();
		this.infoList = infoList;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch, start, length);
		switch (tagName) {
		case "id":
			info.setId(temp);
			tagName = "";
			break;
		case "mp3.name":
			info.setSongName(temp);
			tagName = "";
			break;
		case "mp3.size":
			info.setSongSize(temp);
			tagName = "";
			break;
		case "lrc.name":
			info.setLrcName(temp);
			tagName = "";
			break;
		case "lrc.size":
			info.setLrcSize(temp);
			tagName = "";
			break;
		default:
			break;
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if ("resource".equals(qName)) {
			infoList.add(info);
			System.out.println(info.toString());
			tagName = "";
		}
		
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		if ("resource".equals(qName)) {
			info = new SongInfo();
		}
		tagName = qName;
	}
}

