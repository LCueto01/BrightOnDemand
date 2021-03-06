package com.google;

import java.util.ArrayList;


/** A class used to represent a Playlist */
class VideoPlaylist {

	
	private ArrayList<Video> playlist;
	private String playlistName;
	
	public VideoPlaylist(String name) {
		this.playlistName = name;
		playlist = new ArrayList<Video>();
	}
	
	public String getName() {
		return playlistName;
	}
	
	public void addToPlaylist(Video newVid) {
		playlist.add(newVid);
	}
	
	public ArrayList<Video> getVideos() {
		return playlist;
	}
	
	public void clearVideos() {
		this.playlist = new ArrayList<Video>();
	}
	
	public void removeVideo(String videoId) {
		int index = 0;
		for(Video vid: playlist) {
			if(vid.getVideoId().compareTo(videoId)== 0) {
				playlist.set(index, null);
				break;
			}
			index++;
		}
	}
	
	public boolean checkExists(String videoId) {
		for(Video vid: playlist) {
			if(vid!= null && vid.getVideoId().compareTo(videoId) == 0) {
				return true;
			}
		}
		return false;
	}
	
}
