package com.wss.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

@Service
public class VideoService {
	
	//properties에서 youtube api key 를 가져옴.
    @Value("${youtube.api.key}")
    private String apiKey;

    public List<Video> getMostPopularVideos() {
        List<Video> videos = new ArrayList<>();
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                    httpRequest -> {}).setApplicationName("youtube-search").build();

            YouTube.Videos.List videosList = youtube.videos().list("snippet, statistics");
            videosList.setChart("mostPopular");
            videosList.setRegionCode("kr");
            videosList.setMaxResults(4L);
            videosList.setKey(apiKey);

            VideoListResponse response = videosList.execute();

            List<Video> results = response.getItems();
            if (results != null) {
                videos.addAll(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }
    private final YouTube youtube;
    
    public VideoService() {
		youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
				httpRequest -> {}).setApplicationName("youtube-search").build();
	}

    public List<SearchResult> searchVideos(final String queryTerm, final long number) {
        try {
            final YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setType("video");
            search.setFields("items(id/videoId,snippet/title)");
            search.setMaxResults(number);

            final SearchListResponse searchResponse = search.execute();

            final List<SearchResult> searchResults = searchResponse.getItems();

            return searchResults;
        } catch (final GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (final IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (final Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}

