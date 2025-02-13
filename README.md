# Hero Movement Speed

## Overview
This is a Java Swing application that allows users to upload a CSV file containing hero data and find the hero with the lowest movement speed and its corresponding HP. The program also displays images and plays an audio file associated with the selected hero.

## Features
- Upload a CSV file containing hero data.
- Identify the hero with the lowest movement speed.
- Display hero images and animations.
- Play an audio file associated with the hero.

## Requirements
### Install Java Development Kit (JDK)
Ensure you have Java installed on your system. You can download and install the latest JDK from:
[https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)

### Required Libraries
The program uses Java Swing for the GUI and Java Sound API for audio playback. No additional external libraries are needed.

## Installation
1. Clone this repository:
   ```sh
   git clone https://github.com/your-username/hero-movement-speed.git
   ```
2. Navigate to the project directory:
   ```sh
   cd hero-movement-speed
   ```
3. Compile the Java program:
   ```sh
   javac HeroMovementSpeed.java
   ```
4. Run the program:
   ```sh
   java HeroMovementSpeed
   ```

## CSV File Format
Ensure that the uploaded CSV file follows this format:
```
hero_name,movement_spd,hp
Hero1,300,2500
Hero2,280,2400
Hero3,290,2600
```
- The first row must contain column headers: `hero_name`, `movement_spd`, and `hp`.
- Hero images and audio files should be named accordingly (e.g., `hero1.jpg`, `hero1.gif`, `hero1.wav`).

## Recommended VS Code Extensions for Java Development
If you are using Visual Studio Code, install the following extension pack for Java:
- **Extension Pack for Java** (by Microsoft)
  - Java Development Kit (JDK) support
  - Debugger for Java
  - Test Runner for Java
  - Maven/Gradle support

You can install it directly from the VS Code marketplace:
[https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

## Contributing
If you’d like to contribute, feel free to fork the repository and submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any inquiries, feel free to reach out via GitHub issues or email: rycanano@gmail.com

