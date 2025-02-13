import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HeroMovementSpeed extends JFrame {
    private JPanel resultsPanel;
    private JLabel gifLabel;
    private JButton findHeroButton, uploadButton;
    private List<String[]> csvData;
    private String[] columns;

    public HeroMovementSpeed() {
        setTitle("Hero Movement Speed");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); // Make the window resizable
        setLayout(new BorderLayout(10, 10));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel headerLabel = new JLabel("Hero Movement Speed", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 120, 215));
        headerPanel.add(headerLabel);

        JLabel subHeaderLabel = new JLabel("Find the Hero with the Lowest Movement Speed and Its HP", SwingConstants.CENTER);
        subHeaderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subHeaderLabel.setForeground(new Color(100, 100, 100));
        headerPanel.add(subHeaderLabel);

        add(headerPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(new Color(240, 240, 240));
        add(resultsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        uploadButton = createStyledButton("Upload CSV");
        uploadButton.addActionListener(e -> uploadCSV());

        findHeroButton = createStyledButton("Find Hero");
        findHeroButton.addActionListener(e -> findLowestSpeedHero());

        buttonPanel.add(uploadButton);
        buttonPanel.add(findHeroButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 150, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
        return button;
    }

    private void uploadCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            csvData = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                columns = br.readLine().split(",");
                while ((line = br.readLine()) != null) {
                    csvData.add(line.split(","));
                }
                JOptionPane.showMessageDialog(this, "CSV file uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading CSV file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findLowestSpeedHero() {
        if (csvData == null || columns == null) {
            JOptionPane.showMessageDialog(this, "Please upload a CSV file first.");
            return;
        }

        int speedColumnIndex = Arrays.asList(columns).indexOf("movement_spd");
        int heroColumnIndex = Arrays.asList(columns).indexOf("hero_name");
        int hpColumnIndex = Arrays.asList(columns).indexOf("hp");

        if (speedColumnIndex == -1 || heroColumnIndex == -1 || hpColumnIndex == -1) {
            JOptionPane.showMessageDialog(this, "Required columns not found in the CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] lowestSpeedHero = csvData.get(0);
        for (String[] row : csvData) {
            if (Float.parseFloat(row[speedColumnIndex]) < Float.parseFloat(lowestSpeedHero[speedColumnIndex])) {
                lowestSpeedHero = row;
            }
        }

        displayResults(lowestSpeedHero[heroColumnIndex], lowestSpeedHero[speedColumnIndex], lowestSpeedHero[hpColumnIndex]);
        playAudio(lowestSpeedHero[heroColumnIndex].toLowerCase().replaceAll("[^a-z0-9]", "") + ".wav");
    }

    private void displayResults(String hero, String movement_spd, String hp) {
        String heroImage = hero.toLowerCase().replaceAll("[^a-z0-9]", "") + ".jpg";
        String heroGif = hero.toLowerCase().replaceAll("[^a-z0-9]", "") + ".gif";
        resultsPanel.removeAll();

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(240, 240, 240));

        JLabel heroImageLabel = new JLabel();
        heroImageLabel.setIcon(new ImageIcon(new ImageIcon(heroImage).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        heroImageLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 5));
        heroImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(heroImageLabel);

        JLabel heroNameLabel = new JLabel("Hero: " + hero);
        heroNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heroNameLabel.setForeground(new Color(0, 120, 215));
        heroNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(heroNameLabel);

        JLabel movementSpeedLabel = new JLabel("Movement Speed: " + movement_spd);
        movementSpeedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        movementSpeedLabel.setForeground(new Color(0, 0, 0));
        movementSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(movementSpeedLabel);

        JLabel hpLabel = new JLabel("HP: " + hp + "/" + hp);
        hpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hpLabel.setForeground(new Color(0, 0, 0));
        hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(hpLabel);

        resultsPanel.add(profilePanel);

        gifLabel = new JLabel();
        gifLabel.setIcon(new ImageIcon(new ImageIcon(heroGif).getImage().getScaledInstance(400, 225, Image.SCALE_DEFAULT)));
        gifLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 5));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(gifLabel);

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void playAudio(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing audio file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HeroMovementSpeed().setVisible(true));
    }
}