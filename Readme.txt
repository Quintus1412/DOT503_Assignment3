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
    This will start the console application, and you can interact with its menu.

---
