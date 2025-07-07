Instructions for Building and Running the Video Manager Application

This application uses Apache Maven for build automation. Ensure you have Java Development Kit (JDK) 17 or higher and Apache Maven (version 3.9.10 or higher recommended) installed on your system.

1.  **Navigate to the Project Root:**
    Open your terminal or command prompt and navigate to the root directory of this project (the directory containing the 'pom.xml' file).

2.  **Clean the Project (Optional but Recommended):**
    To remove any previously compiled classes and packages:
    ```bash
    mvn clean
    ```

3.  **Compile the Application:**
    To compile the Java source code:
    ```bash
    mvn compile
    ```

4.  **Run Unit Tests:**
This project includes JUnit 5 unit tests located in the `src/test/java/com/dot/` directory. When `mvn test` is executed, both `AppTest.java` and `VideoManagerTest.java` are run.

* **`VideoManagerTest.java`**:
    * This file contains custom unit tests specifically designed to verify the core functionalities of the `VideoManager` class. These tests cover operations such as adding, viewing, saving, and loading video entries.
    * **Test Scenarios (Passing):**
        * `testAddVideo`: Checks if a video can be successfully added to the manager's collection and if its properties are correctly stored.
        * `testDisplayVideos`: Verifies that the manager can correctly retrieve and present a list of added videos.
        * `testSaveAndLoadVideos`: Confirms that video data can be correctly written to `data.txt` and then accurately loaded back into the manager, ensuring data persistence.
    * **Test Scenarios (Intentional Failures):**
        * `testVideoTitleEqualityFails`: This test deliberately asserts that a video's actual title (e.g., "Correct Title") should be equal to an incorrect, different title (e.g., "Incorrect Title"). Since these strings are not equal, the assertion fails, demonstrating a deliberate test failure.
        * `testReadVideoFromEmptyFileFails`: This test intentionally attempts to read a video object from an empty or invalid source (simulating an empty file) and then asserts that the resulting video object is *not* null. If the method correctly returns `null` for an empty file (which is often the expected behavior for an empty/invalid read), this assertion will fail, demonstrating a deliberate test failure.
    * These intentional failures demonstrate the build's ability to run tests and report failures, while the `pom.xml` configuration allows the build to proceed to the packaging phase despite these failures.

    To execute the JUnit 5 unit tests. Note: Two tests are intentionally designed to fail for demonstration purposes, but the build will still proceed to the packaging phase due to configuration.
    ```bash
    mvn test
    ```

5.  **Package the Application into an Executable JAR:**
    This command will compile, run tests, and then package the application into a single, executable JAR file. The JAR will be located in the 'target/' directory.
    ```bash
    mvn package
    ```

6.  **Run the Executable JAR:**
    Once the 'mvn package' command completes successfully, you can run the application directly from the generated JAR file:
    ```bash
    java -jar target/video-manager-app-1.0-SNAPSHOT.jar
    ```
    This will start the console application, and user can interact with its menu.

---
