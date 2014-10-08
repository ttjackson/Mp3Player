package tt.android.songentity;

public class SongInfo {
	private String id;
	private String songName;
	private String songSize;
	private String lrcName;
	private String lrcSize;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getSongSize() {
		return songSize;
	}
	public void setSongSize(String songSize) {
		this.songSize = songSize;
	}
	public String getLrcName() {
		return lrcName;
	}
	public void setLrcName(String lrcName) {
		this.lrcName = lrcName;
	}
	public String getLrcSize() {
		return lrcSize;
	}
	public void setLrcSize(String lrcSize) {
		this.lrcSize = lrcSize;
	}
	@Override
	public String toString() {
		return "SongInfo [id=" + id + ", songName=" + songName + ", songSize="
				+ songSize + ", lrcName=" + lrcName + ", lrcSize=" + lrcSize
				+ "]";
	}


	
}

