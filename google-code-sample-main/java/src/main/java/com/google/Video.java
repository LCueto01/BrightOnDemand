package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean isFlagged = false;
  private String flagReason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }
  
  // returns full details of video to save time using multiple getters
  String getDetails() {
	  return this.title + " (" + this.videoId + ") " + this.tags;
  }
  
  public void toggleFlag() {
	  if(!this.isFlagged) {
		  isFlagged = true;
		  this.flagReason = "Not supplied";
	  }
	  else {
		  isFlagged = false;
	  }
  }
  public void toggleFlag(String reason) {
	  if(!this.isFlagged) {
		  isFlagged = true;
		  this.flagReason = reason;
	  }
	  else {
		  isFlagged = false;
		  this.flagReason = null;
	  }
  }
  
  
  public boolean getFlagStatus() {
	  return isFlagged;
  }
  
  public String getFlagReason() {
	  return flagReason;
  }
  
}
