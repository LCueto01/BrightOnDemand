package com.google;

import java.util.Comparator;

public class PlaylistComparator implements Comparator<VideoPlaylist>{

	@Override
	public int compare(VideoPlaylist o1, VideoPlaylist o2) {
		// TODO Auto-generated method stub
		return o1.getName().compareTo(o2.getName());
	}

}
