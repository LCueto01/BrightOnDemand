package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VideoPlayer {

	private final VideoLibrary videoLibrary;
	private ArrayList<Video> videos;
	private PlaylistLibrary playlistCollection;
	private Video pausedVideo = null;
	private Video currentVideo = null;
	private boolean isPaused = false;

	public VideoPlayer() {
		this.videoLibrary = new VideoLibrary();
		this.playlistCollection = new PlaylistLibrary();
		videos = (ArrayList<Video>) videoLibrary.getVideos();
	}

	public void numberOfVideos() {
		System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
	}

	public void showAllVideos() {// this code is shite lmao
		System.out.println("Here's a list of all available videos:");
		
		
		  ArrayList<Video> sortedVideos = videos;
		  Video vidTemp = null;
		  int letterAtIndex = 0;
		  
		  
		  for(int i = 0; i< sortedVideos.size();i++) {
			 
			  if(i != sortedVideos.size()-1 && sortedVideos.get(i).getTitle().charAt(letterAtIndex) > sortedVideos.get(i+1).getTitle().charAt(letterAtIndex)) {
				  vidTemp = sortedVideos.get(i);
				  sortedVideos.set(i, sortedVideos.get(i+1));
				  sortedVideos.set(i+1, vidTemp);
				  i= -1;
				  letterAtIndex = 0;
			  }
			  else if (i != sortedVideos.size()-1 && sortedVideos.get(i).getTitle().charAt(letterAtIndex) == sortedVideos.get(i+1).getTitle().charAt(letterAtIndex)) {
				 letterAtIndex++;
				 if(i != sortedVideos.size()-1 && sortedVideos.get(i).getTitle().charAt(letterAtIndex) > sortedVideos.get(i+1).getTitle().charAt(letterAtIndex)) {
					  vidTemp = sortedVideos.get(i);
					  sortedVideos.set(i, sortedVideos.get(i+1));
					  sortedVideos.set(i+1, vidTemp);
					  letterAtIndex = 0;
				  }
			  }
			  
			  
		  }
		  videos = sortedVideos;
		 

		for (Video vid : videos) {
			//System.out.println(vid.getTitle() + " " + vid.getVideoId() + " " + vid.getTags());
			if(!vid.getFlagStatus()) {
				System.out.println(vid.getDetails());
			}
			else {
				System.out.println(vid.getDetails() + " - FLAGGED (reason: " + vid.getFlagReason()+")");
			}
		}
		
	}

	public void playVideo(String videoId) {

		int index = 0;
		for (Video vid : videos) {
			if(vid.getVideoId().compareTo(videoId) == 0 && currentVideo == null) {
				currentVideo = vid;
				if(!vid.getFlagStatus()) {
					System.out.println("Playing video: " + currentVideo.getTitle());
					break;
				}
				else {
					System.out.println("Cannot play video: Video is currently flagged (reason: " + vid.getFlagReason() + ")");
				}
			}
			else if(currentVideo != null && vid.getVideoId().compareTo(videoId) == 0) {
				System.out.println("Stopping video: " + currentVideo.getTitle());
				currentVideo = vid;
				System.out.println("Playing video: " + currentVideo.getTitle());
				break;
			}
			else if(index == videos.size()-1) {
				System.out.println("Cannot play video: Video does not exist");
			}
			else {
				index++;
			}
		}
	}

	public void stopVideo() {
		if(currentVideo != null) {
			System.out.println("Stopping video: " + currentVideo.getTitle());
			currentVideo = null;
		}
		else {
			System.out.println("Cannot stop video: No video is currently playing");
		}
	}

	public void playRandomVideo() {
		Random numberGenerator = new Random();
		int randomNumber = numberGenerator.nextInt(videos.size()-1);
		boolean anyFlagged = false;
		Video randomVid;
		for(Video vid: videos) {
			if(vid.getFlagStatus()) {
				anyFlagged = true;
			}
		}
		if(!anyFlagged) {
			randomVid = videos.get(randomNumber);
			this.playVideo(randomVid.getVideoId());
		}
		else {
			ArrayList<Video> unflaggedVideos = videos;
			for(int i = 0; i < unflaggedVideos.size();i++) {
				if(unflaggedVideos.get(i).getFlagStatus()) {
					unflaggedVideos.remove(unflaggedVideos.get(i));
				}
			}
			if(unflaggedVideos.size() > 0) {
				randomNumber = numberGenerator.nextInt(unflaggedVideos.size()-1);
				randomVid = unflaggedVideos.get(randomNumber);
				this.playVideo(randomVid.getVideoId());	
			}
			else {
				System.out.println("No videos available");
			}
		}
		
		
	}

	public void pauseVideo() {
		if(!isPaused && currentVideo != null) {
			System.out.println("Pausing video: "+currentVideo.getTitle());
			pausedVideo = currentVideo;
			isPaused = true;
		}
		else if(currentVideo == null) {
			System.out.println("Cannot pause video: No video is currently playing");
		}
		else {
			System.out.println("Video already paused: " +  currentVideo.getTitle());
		}
	}

	public void continueVideo() {
		if(pausedVideo == null && currentVideo != null) {
			System.out.println("Cannot continue video: Video is not paused");
		}
		else if(currentVideo == null) {
			System.out.println("Cannot continue video: No video is currently playing");
		}
		else {
			System.out.println("Continuing video: " +currentVideo.getTitle());
			pausedVideo = null;
		}
	}

	public void showPlaying() {
		if(currentVideo != null && pausedVideo == null) {
			System.out.println("Currently playing: "+ currentVideo.getDetails());	
		}
		// for detecting if video is paused
		else if(currentVideo != null && pausedVideo != null && currentVideo.getDetails().compareTo(pausedVideo.getDetails())== 0) {
			System.out.println("Currently playing: "+ pausedVideo.getDetails() + " - Paused");	
		}
		else {
			System.out.println("No video is currently playing");
		}
	}

	public void createPlaylist(String playlistName) {
		if(playlistCollection.getPlaylist(playlistName) == null) {
			playlistCollection.addPlayList(playlistName);
			System.out.println("Successfully created new playlist: " + playlistName);
		}
		else {
			System.out.println("Cannot create playlist: A playlist with the same name already exists");
		}
		
	}

	public void addVideoToPlaylist(String playlistName, String videoId) {
		VideoPlaylist foundPlaylist = playlistCollection.getPlaylist(playlistName);
		boolean videoAdded = false;
		int index = 0;
		for(Video vid: videos) {
			if(vid.getVideoId().compareTo(videoId) == 0 && foundPlaylist != null) {
				if(foundPlaylist.checkExists(videoId)) {
					System.out.println("Cannot add video to " + playlistName + ": Video already added");
					break;
				}
				else {
					if(!vid.getFlagStatus()) {
						playlistCollection.addVideoToPlaylist(playlistName, vid);
						videoAdded = true;
						System.out.println("Added video to " + playlistName + ": " + vid.getTitle());	
					}
					else {
						System.out.println("Cannot add video to "+ playlistName +": Video is currently flagged (reason: " + vid.getFlagReason() +")");
						break;
					}
				}
			}
			else if(foundPlaylist == null) {
				System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
				break;
			}
			else if(index == videos.size()-1 && videoAdded == false) {
				System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
			}
			index++;
		}
	}

	public void showAllPlaylists() {
		if(playlistCollection.getAllPlaylists().size() == 0) {
			System.out.println("No playlists exist yet");
		}
		else {
			System.out.println("Showing all playlists:");
			for(VideoPlaylist playlist : playlistCollection.getAllPlaylists()) {
				System.out.println(playlist.getName());
			}
		}
	}

	public void showPlaylist(String playlistName) {
		VideoPlaylist foundPlaylist = playlistCollection.getPlaylist(playlistName);
		if(foundPlaylist != null) {
			System.out.println("Showing playlist: " + playlistName);
			if(foundPlaylist.getVideos().size() == 0) {
				System.out.println("No videos here yet");
			}
			else {
				for(Video vid: foundPlaylist.getVideos()) {
					if(vid != null) {
						if(!vid.getFlagStatus()) {
							System.out.println(vid.getDetails());	
						}
						else {
							System.out.println(vid.getDetails() +" - FLAGGED (reason: " + vid.getFlagReason() +")");	
						}
					}
				}	
			}
		}
		else {
			System.out.println("Cannot show playlist " + playlistName +": Playlist does not exist");
		}
	}

	public void removeFromPlaylist(String playlistName, String videoId) {
		 VideoPlaylist existingPlaylist = playlistCollection.getPlaylist(playlistName);
		 if(existingPlaylist == null) {
				System.out.println("Cannot remove video from " + playlistName +": Playlist does not exist");
			}
		for(Video vid: videos) {
			if(vid.getVideoId().compareTo(videoId) == 0 && existingPlaylist != null) {
				if(!existingPlaylist.checkExists(videoId)) {
					System.out.println("Cannot remove video from " + playlistName +": Video is not in playlist");
				}
				else {
					playlistCollection.removeSongFromPlaylist(playlistName, videoId);
					System.out.println("Removed video from " + playlistName +": " + vid.getTitle());
					break;	
				}
			}
		}
	}

	public void clearPlaylist(String playlistName) {
		VideoPlaylist foundPlaylist = playlistCollection.getPlaylist(playlistName);
		if(foundPlaylist != null) {
			System.out.println("Successfully removed all videos from " + playlistName);
			foundPlaylist.clearVideos();
		}
		else {
			System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
		}
	}

	public void deletePlaylist(String playlistName) {
		if(playlistCollection.getPlaylist(playlistName) != null) {
			playlistCollection.deletePlaylist(playlistName);
			System.out.println("Deleted playlist: " + playlistName);
		}
		else {
			System.out.println("Cannot delete playlist my_playlist: Playlist does not exist");
		}
	}

	public void searchVideos(String searchTerm) {
		Scanner userInput = new Scanner(System.in);
		ArrayList<Video> foundVids = new ArrayList<>();
		int index = 0;
		for(Video vid: videos) {
			if(vid.getTitle().toUpperCase().contains(searchTerm.toUpperCase()) && !vid.getFlagStatus()) {
				foundVids.add(vid);
			}
			else if(index == videos.size()-1 && foundVids.size() == 0) {
				System.out.println("No search results for " + searchTerm);
				return;
			}
			index++;
		}
		System.out.println("Here are the results for " + searchTerm +":");
		Collections.sort(foundVids, new VideoComparator());
		for(Video vid: foundVids) {
			if(!vid.getFlagStatus()) {
				System.out.println((foundVids.indexOf(vid)+1) + ") " +vid.getDetails());
			}
		}
		System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
		System.out.println("If your answer is not a valid number, we will assume it's a no.");
		
		try {
			int choice = userInput.nextInt();
			if( choice > 0 && choice < foundVids.size()+1) {
				playVideo(foundVids.get(choice-1).getVideoId());
			}
		}catch(Exception e) {
			
		}
		
		
	}

	public void searchVideosWithTag(String videoTag) {
		Scanner userInput = new Scanner(System.in);
		ArrayList<Video> foundVids = new ArrayList<>();
		int videoNumber = 0;
		int index = 0;
		
		for(Video vid: videos) {
		List<String> videoTags = vid.getTags();	
		
		if(!vid.getFlagStatus()) {
			for(String tag: videoTags) {
				
				if(tag.toUpperCase().trim().compareTo(((videoTag.toUpperCase()))) == 0) {
					foundVids.add(vid);
					videoNumber++;
				}
			}
		}
				if(index == videos.size()-1 && videoNumber == 0) {
				System.out.println("No search results for " + videoTag);
				return;
			}
		index++;
		}
		System.out.println("Here are the results for " + videoTag +":");
		Collections.sort(foundVids, new VideoComparator());
		for(Video vid: foundVids) {
			if(!vid.getFlagStatus()) {
				System.out.println((foundVids.indexOf(vid)+1) + ") " +vid.getDetails());
			}
		}
		System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
		System.out.println("If your answer is not a valid number, we will assume it's a no.");
		try {
			int choice = userInput.nextInt();
			if( choice > 0 && choice <= foundVids.size()+1) {
				playVideo(foundVids.get(choice-1).getVideoId());
			}
		}catch(Exception e) {
			
		}
	}

	public void flagVideo(String videoId) {
		for(int i = 0; i < videos.size();i++) {
			if(videos.get(i).getVideoId().compareTo(videoId) == 0) {
				if(!videos.get(i).getFlagStatus()) {
					videos.get(i).toggleFlag();
					System.out.println("Successfully flagged video: " + videos.get(i).getTitle() +" (reason: " +videos.get(i).getFlagReason() +")");
					break;
				}
				else {
					System.out.println("Cannot flag video: video is already flagged (reason: " + videos.get(i).getFlagReason() + ")");
					break;
				}
			}
			else if(i == videos.size()-1 && !videos.get(i).getFlagStatus()) {
				System.out.println("Cannot flag video: Video does not exist");
			}
		}
	}

	public void flagVideo(String videoId, String reason) {
		for(int i = 0; i < videos.size();i++) {
			if(videos.get(i).getVideoId().compareTo(videoId) == 0) {
				if(!videos.get(i).getFlagStatus()) {
					videos.get(i).toggleFlag(reason);
					System.out.println("Successfully flagged video: " + videos.get(i).getTitle() +" (reason: " +videos.get(i).getFlagReason() +")");
					break;
				}
				else {
					System.out.println("Cannot flag video: Video is already flagged");
					break;
				}
			}
			else if(i == videos.size()-1 && !videos.get(i).getFlagStatus()) {
				System.out.println("Cannot flag video: Video does not exist");
			}
		}
	}

	public void allowVideo(String videoId) {
		for(int i = 0; i < videos.size();i++) {
			if(videos.get(i).getVideoId().compareTo(videoId) == 0) {
				if(videos.get(i).getFlagStatus()) {
					videos.get(i).toggleFlag();
					System.out.println("Successfully removed flag from video: " + videos.get(i).getTitle());
					break;
				}
				else {
					System.out.println("Cannot remove flag from video: Video is not flagged");
					break;
				}
			}
			else if(i == videos.size()-1 && videos.get(i).getFlagStatus()) {
				System.out.println("Cannot remove flag from video: Video does not exist");
			}
		}
	}
	
	/*
	 * Other class methods
	 */

}