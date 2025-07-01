package com.dot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoManager {

    private static Scanner scanner = new Scanner(System.in); // Use a single scanner for input

    // Function to read details for a single video from user input
    public static Video readVideo() {
        System.out.print("Enter video title: ");
        String title = scanner.nextLine();
        System.out.print("Enter video link: ");
        String link = scanner.nextLine();
        return new Video(title, link);
    }

    // Function to add new videos to the list
    public static void addNewVideos(List<Video> videos) {
        System.out.print("How many new videos to add? ");
        int totalNewVideos = Integer.parseInt(scanner.nextLine()); // Read int using nextLine() then parse

        for (int i = 0; i < totalNewVideos; i++) {
            System.out.println("--- Enter details for Video " + (videos.size() + 1) + " ---");
            Video vid = readVideo();
            videos.add(vid);
        }
        System.out.println(totalNewVideos + " video(s) added successfully.");
    }

    // Function to print details of a single video
    public static void printVideo(Video video) {
        System.out.println("--------------------");
        System.out.println("Title of Video: " + video.getTitle()); // Modified for feature-y
        System.out.println("Link: " + video.getLink());
    }

    // Function to print all videos in a list
    public static void printVideos(List<Video> videos) {
        if (videos.isEmpty()) {
            System.out.println("\nNo videos to display.");
            return;
        }
        System.out.println("\n--- Your Video List ---");
        for (int i = 0; i < videos.size(); i++) {
            System.out.println("Video " + (i + 1) + ":");
            printVideo(videos.get(i));
        }
        System.out.println("--------------------");
    }

    // Function to write a single video to a file
    public static void writeVideoToTxt(Video video, BufferedWriter writer) throws IOException {
        writer.write(video.getTitle());
        writer.newLine(); // Writes a newline character
        writer.write(video.getLink());
        writer.newLine();
    }

    // Function to write all videos to "data.txt"
    public static void writeVideosToTxt(List<Video> videos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            writer.write(String.valueOf(videos.size()));
            writer.newLine();
            for (Video video : videos) {
                writeVideoToTxt(video, writer);
            }
            System.out.println("\nSuccessfully saved " + videos.size() + " video(s) to data.txt.");
        } catch (IOException e) {
            System.err.println("\nError saving videos: " + e.getMessage());
        }
    }

    // Function to read a single video from a file
    public static Video readVideoFromTxt(BufferedReader reader) throws IOException {
        String title = reader.readLine(); // readLine() returns null at EOF
        String link = reader.readLine();
        if (title == null || link == null) {
            return null; // Indicates end of file or incomplete entry
        }
        return new Video(title, link);
    }

    // Function to read all videos from "data.txt"
    public static List<Video> readVideosFromTxt() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String totalLine = reader.readLine();
            if (totalLine == null) {
                System.out.println("\ndata.txt is empty or corrupted. Starting with an empty video list.");
                return videos;
            }
            int total = Integer.parseInt(totalLine);

            for (int i = 0; i < total; i++) {
                Video video = readVideoFromTxt(reader);
                if (video != null) {
                    videos.add(video);
                } else {
                    System.err.println("Warning: Incomplete video entry found in data.txt. Skipping remaining entries.");
                    break; // Break if an incomplete entry is found
                }
            }
            System.out.println("\nSuccessfully loaded " + videos.size() + " video(s) from data.txt.");
        } catch (IOException e) {
            System.err.println("\ndata.txt not found or error reading: " + e.getMessage());
            System.out.println("Starting with an empty video list. Please add new videos or create the file by saving.");
        } catch (NumberFormatException e) {
            System.err.println("\nError parsing total video count from data.txt: " + e.getMessage());
            System.out.println("data.txt might be corrupted. Starting with an empty video list.");
        }
        return videos;
    }

    // Main menu display
    public static void displayMenu() {
        System.out.println("\n--- Video Manager Menu ---");
        System.out.println("1. Add New Videos");
        System.out.println("2. View All Videos");
        System.out.println("3. Save Videos to File");
        System.out.println("4. Load Videos from File");
        System.out.println("5. Exit");
        System.out.println("------------------------");
    }

    public static void main(String[] args) {
        List<Video> videos = readVideosFromTxt(); // Load existing videos at startup

        while (true) {
            displayMenu();
            System.out.print("Enter your choice (1-5): ");
            String choiceStr = scanner.nextLine(); // Read choice as String

            try {
                int choice = Integer.parseInt(choiceStr);

                switch (choice) {
                    case 1:
                        addNewVideos(videos);
                        break;
                    case 2:
                        printVideos(videos);
                        break;
                    case 3:
                        writeVideosToTxt(videos);
                        break;
                    case 4:
                        if (!videos.isEmpty()) {
                            System.out.print("Loading will overwrite current unsaved videos. Continue? (y/n): ");
                            String confirm = scanner.nextLine().toLowerCase();
                            if (!confirm.equals("y")) {
                                System.out.println("Load cancelled.");
                                continue;
                            }
                        }
                        videos = readVideosFromTxt(); // Update the current list
                        break;
                    case 5:
                        System.out.println("Exiting Video Manager. Goodbye!");
                        scanner.close(); // Close the scanner before exiting
                        return; // Exit the main method
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    // New helper methods added for testability (as per unit testing guide)
    public static Video createVideo(String title, String link) {
        /** Creates and returns a Video object directly. */
        return new Video(title, link);
    }

    public static int getVideosCount(List<Video> videosList) {
        /** Returns the number of videos in a list. */
        return videosList.size();
    }
}