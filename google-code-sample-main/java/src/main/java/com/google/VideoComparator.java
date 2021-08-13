package com.google;

import java.util.Comparator;

public class VideoComparator implements Comparator<Video> {

	@Override
	public int compare(Video o1, Video o2) {
		// TODO Auto-generated method stub
		return o1.getTitle().compareTo(o2.getTitle());
	}

}
