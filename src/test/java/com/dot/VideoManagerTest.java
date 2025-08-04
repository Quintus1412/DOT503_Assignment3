package com.dot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Ensure Scanner is imported if used in mocks

class VideoManagerTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    // Helper to set mock input
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // --- Passing Test Cases (3) ---

    @Test
    @DisplayName("Test 1: Video object creation with correct title and link")
    void testCreateVideo() {
        Video video = VideoManager.createVideo("Test Title", "http://test.link");
        assertNotNull(video, "Video object should not be null");
        assertEquals("Test Title", video.getTitle(), "Video title should match input");
        assertEquals("http://test.link", video.getLink(), "Video link should match input");
    }

    @Test
    @DisplayName("Test 2: Adding a single new video increases list size by one")
    void testAddNewVideoIncreasesSize() {
        List<Video> videos = new ArrayList<>();
        int initialSize = videos.size();

        String input = "1\nTest Video 1\nhttp://link1.com\n";
        provideInput(input);
        Scanner testScanner = new Scanner(System.in);
        try {
            VideoManager.addNewVideos(videos, testScanner);
        } finally {
            testScanner.close();
            System.setIn(systemIn);
        }

        assertEquals(initialSize + 1, videos.size(), "List size should increase by 1");
        assertEquals("Test Video 1", videos.get(0).getTitle(), "Added video title should be correct");
    }

    @Test
    @DisplayName("Test 3: getVideosCount returns correct number of videos")
    void testGetVideosCount() {
        List<Video> videos = new ArrayList<>();
        videos.add(new Video("A", "a.com"));
        videos.add(new Video("B", "b.com"));
        assertEquals(2, VideoManager.getVideosCount(videos), "Should return 2 for a list with 2 videos");
        videos.clear();
        assertEquals(0, VideoManager.getVideosCount(videos), "Should return 0 for an empty list");
    }

    // --- Corrected Passing Test Cases (2) ---

    @Test
    @DisplayName("Test 4 (Corrected): Read video from empty file returns null")
    void testReadVideoFromEmptyFileReturnsNull() throws IOException {
        String emptyFileContent = "";
        BufferedReader reader = new BufferedReader(new StringReader(emptyFileContent));

        // FIX: The original assertion was designed to fail. The correct behavior is to return null.
        Video video = VideoManager.readVideoFromTxt(reader);
        assertNull(video, "Video object should be null for an empty file");
    }

    @Test
    @DisplayName("Test 5 (Corrected): Check video title equality with correct value")
    void testVideoTitleEqualityPasses() {
        Video video = VideoManager.createVideo("Correct Title", "http://link.com");

        // FIX: The original assertion was designed to fail. We now assert for equality with the correct title.
        assertEquals("Correct Title", video.getTitle(), "Video title should match the correct value");
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
}