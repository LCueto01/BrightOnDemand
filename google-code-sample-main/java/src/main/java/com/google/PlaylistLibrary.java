package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistLibrary {

	private ArrayList<VideoPlaylist> allPlaylists;
	
	public PlaylistLibrary() {
		allPlaylists = new ArrayList<VideoPlaylist>();
	}

	public ArrayList<VideoPlaylist> getAllPlaylists() {
		return allPlaylists;
	}
	
	public void addPlayList(String playlistName) {
		boolean alreadyCreated = checkPlaylist(playlistName);
		if(!alreadyCreated) {
			VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);
			allPlaylists.add(newPlaylist);
		}
		Collections.sort(allPlaylists, new PlaylistComparator());
	}
	
	public void addVideoToPlaylist(String playlistName, Video vid) {
		for(VideoPlaylist playlist: allPlaylists) {
			if(playlist.getName().toUpperCase().compareTo(playlistName.toUpperCase()) == 0) {
				playlist.addToPlaylist(vid);
			}
		}
	}
	
	public void removeSongFromPlaylist(String playlistName, String videoId) {
		for(VideoPlaylist playlist: allPlaylists) {
			if(playlist.getName().toUpperCase().compareTo(playlistName.toUpperCase()) == 0) {
				playlist.removeVideo(videoId);
			}
		}
	}
	
	public VideoPlaylist getPlaylist(String playlistName) {
		for(VideoPlaylist playlist: allPlaylists) {
			if(playlist != null && playlist.getName().toUpperCase().compareTo(playlistName.toUpperCase()) == 0) {
				return playlist;
			}
		}
		return null;
	}
	
	public void deletePlaylist(String playlistName) {
		int index = 0;
		for(VideoPlaylist playlist: allPlaylists) {
			if(playlist.getName().toUpperCase().compareTo(playlistName.toUpperCase()) == 0) {
				allPlaylists.set(index, null);
				break;
			}
			index++;
		}
	}
	
	public boolean checkPlaylist(String playlistName) {
		for(VideoPlaylist play: allPlaylists) {
			if(play.getName().compareTo(playlistName) == 0) {
				return true;
			}
		}
		return false;
	}
}
