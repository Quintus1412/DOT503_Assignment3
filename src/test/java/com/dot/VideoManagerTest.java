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
        // Reset scanner for each test if VideoManager uses it directly
        // This might require modifying VideoManager to accept a Scanner in its methods or constructor
        // For simplicity in testing, we will mock System.in/out directly.
        // Ensure the scanner in VideoManager is initialized only once or closed/reopened carefully.
        // If VideoManager's scanner is static, consider adding a reset method if needed.
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

        // Simulate user input for one video
        // The first line is for "How many new videos?", the subsequent lines are for video details
        String input = "1\nTest Video 1\nhttp://link1.com\n";
        provideInput(input); // Set mock input

        // Create a Scanner that reads from our mocked System.in
        // This Scanner will be local to the test method and closed implicitly by the JVM
        Scanner testScanner = new Scanner(System.in);
        try {
            VideoManager.addNewVideos(videos, testScanner); // Pass the testScanner
        } finally {
            testScanner.close(); // Important: Close the test scanner
            System.setIn(systemIn); // Restore original System.in
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


    // --- Failing Test Cases (2) ---

    @Test
    @DisplayName("Test 4 (Failing): Read video from empty file - expects non-null but gets null")
    void testReadVideoFromEmptyFileFails() throws IOException {
        // Create an empty mock file content
        String emptyFileContent = "";
        BufferedReader reader = new BufferedReader(new StringReader(emptyFileContent));

        // This should return null if the file is empty or corrupted (as per VideoManager logic for totalLine)
        // We expect it to return a valid Video object (which will fail)
        Video video = VideoManager.readVideoFromTxt(reader);
        assertNotNull(video, "Video object should NOT be null for an empty file (THIS TEST WILL FAIL)"); // This assertion is designed to fail
    }

    @Test
    @DisplayName("Test 5 (Failing): Check title equality with incorrect expected value")
    void testVideoTitleEqualityFails() {
        Video video = VideoManager.createVideo("Correct Title", "http://link.com");
        assertEquals("Incorrect Title", video.getTitle(), "Video title should NOT match this incorrect value (THIS TEST WILL FAIL)"); // This assertion is designed to fail
    }

    // Restore original System.in/out after all tests
    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
}