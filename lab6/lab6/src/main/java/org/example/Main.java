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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
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
        int threads_num = Integer.parseInt(args[1]);

        try (Stream<Path> stream = Files.list(source)){
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ForkJoinPool customThreadPool = new ForkJoinPool(threads_num);

        long time = System.currentTimeMillis();

        try {
            customThreadPool.submit(() ->
                files.parallelStream()
                    .map(path -> {
                        try {
                            BufferedImage image = ImageIO.read(path.toFile());
                            return Pair.of(path.getFileName().toString(), image);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(pair -> {
                        BufferedImage image = pair.getRight();
                        BufferedImage newImage = new BufferedImage(image.getWidth(),
                                image.getHeight(),
                                image.getType());

                        for (int i = 0; i < image.getWidth(); i++) {
                            for (int j = 0; j < image.getHeight(); j++) {
                                int mirroredRgb = image.getHeight() - 1 - j;
                                int rgb = image.getRGB(i, j);
                                newImage.setRGB(i, mirroredRgb, rgb);
                            }
                        }
                        return Pair.of(Pair.of(pair.getLeft(), image), newImage);
                    })
                    .forEach(pair-> {
                        BufferedImage newImage = pair.getRight();
                        String name = pair.getLeft().getLeft();
                        BufferedImage original = pair.getLeft().getRight();
                        String outputFolder = "processed_images";
                        String outputPath = outputFolder + File.separator + "processed_" + name;

                        File folder = new File(outputFolder);
                        if (!folder.exists()) {
                            folder.mkdir();
                        }

                        try {
                            ImageIO.write(newImage, "jpg", new File(outputPath));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Saved processed image as: " + outputPath);

                        if(name.equals("pudzian1.jpg")) {
                            displayImage(original);
                            displayImage(newImage);
                        }
                    })).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }

        System.out.println("\nUsed threads: " + threads_num);
        System.out.println("Time: " + (System.currentTimeMillis() - time) / 1000.0 + "s");

        customThreadPool.shutdown();
    }

    private static void displayImage(BufferedImage image) {
        Frame frame = new JFrame();
        ((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }
}