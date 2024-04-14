package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Usage: java Main <source_directory>");
            return;
        }

        List<Path> files;
        Path source = Path.of(args[0]);
        try (Stream<Path> stream = Files.list(source)){
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Path path : files) {
            try {
                // Read the image file into a BufferedImage
                BufferedImage image = ImageIO.read(path.toFile());
                // Create a Pair with the file name and the BufferedImage
                String name = path.getFileName().toString();
                Pair<String, BufferedImage> pair = Pair.of(name, image);
                BufferedImage newImage = new BufferedImage(image.getWidth(),
                        image.getHeight(),
                        image.getType());

                for (int i = 0; i < image.getWidth(); i++)
                {
                    for (int j = 0; j < image.getHeight(); j++)
                    {
                        int mirroredRgb = image.getHeight() - 1 - j;
                        int rgb = image.getRGB(i, j);
                        newImage.setRGB(i, mirroredRgb, rgb);
                    }
                }

                String outputFolder = "processed_images";
                String outputPath = outputFolder + File.separator + "processed_" + name;

                File folder = new File(outputFolder);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                ImageIO.write(newImage, "jpg", new File(outputPath));
                System.out.println("Saved processed image as: " + outputPath);

                if(name.equals("pudzian1.jpg")) {
                    displayImage(image);
                    displayImage(newImage);
                }

                //System.out.println("File name: " + name + ", Image dimensions: " + image.getWidth() + "x" + image.getHeight());
            } catch (IOException e) {
                System.err.println("Error processing file: " + path);
                e.printStackTrace();
            }
        }


        //System.out.println(files);
    }

    private static void displayImage(BufferedImage image) {
        Frame frame = new JFrame();
        ((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());

        // Create a JLabel and set its icon to the image
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);

        // Make the frame visible
        frame.setVisible(true);
    }
}