package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PlaylistController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songsList = songService.fetchAllSongs();
		model.addAttribute("songs", songsList);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//Updating playlist table
		playlistService.addPlaylist(playlist);
		
		//Updating song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			//Update song object in database
			songService.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists = playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylists";
	}
	
	

}
