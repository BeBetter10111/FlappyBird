package game.core;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Queue;

public class AssetLoader {
    private final Map<String, BufferedImage> images = new HashMap<>();
    private final Map<String, Clip> sounds = new HashMap<>();

    private InputStream openStream(String path) {
        String withSlash = path.startsWith("/") ? path : "/" + path;
        InputStream is = AssetLoader.class.getResourceAsStream(withSlash);
        if (is != null) {
            return is;
        }
        String[] candidates = {
            path,
            "src/" + path,
            "../" + path,
            "../../" + path
        };
        for (String candidate : candidates) {
            File f = new File(candidate);
            if (f.exists()) {
                try {
                    return new FileInputStream(f);
                } catch (FileNotFoundException ignored) {}
            }
        }
        throw new RuntimeException(
            "Asset not found: " + path +
            "\nWorking directory: " + new File(".").getAbsolutePath() +
            "\nFix: In IntelliJ, right-click the 'src/assets' folder -> Mark Directory As -> Resources Root"
        );
    }

    public BufferedImage loadImage(String path) {
        if (images.containsKey(path)) {
            return images.get(path);
        }
        try (InputStream is = openStream(path)) {
            BufferedImage img = ImageIO.read(is);
            if (img == null) {
                throw new RuntimeException("ImageIO returned null for: " + path);
            }
            images.put(path, img);
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    public BufferedImage loadScaledImage(String path, int scale) {
        BufferedImage original = loadImage(path);

        if (path.toLowerCase().contains("bird")) {
            original = removeLightBackgroundFromEdges(original);
        }

        int w = original.getWidth() * scale;
        int h = original.getHeight() * scale;

        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = scaled.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );
        g.drawImage(original, 0, 0, w, h, null);
        g.dispose();

        return scaled;
    }

    private BufferedImage removeLightBackgroundFromEdges(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = result.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, 0, 0, null);
        g.dispose();

        boolean[][] visited = new boolean[height][width];
        Queue<Point> queue = new ArrayDeque<>();

        for (int x = 0; x < width; x++) {
            queue.add(new Point(x, 0));
            queue.add(new Point(x, height - 1));
        }

        for (int y = 0; y < height; y++) {
            queue.add(new Point(0, y));
            queue.add(new Point(width - 1, y));
        }

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            int x = p.x;
            int y = p.y;

            if (x < 0 || x >= width || y < 0 || y >= height) {
                continue;
            }

            if (visited[y][x]) {
                continue;
            }

            visited[y][x] = true;

            int argb = result.getRGB(x, y);

            int a = (argb >> 24) & 0xff;
            int r = (argb >> 16) & 0xff;
            int gr = (argb >> 8) & 0xff;
            int b = argb & 0xff;

            boolean isTransparent = a < 20;

            // Xóa nền trắng / xám nhạt / caro nhạt quanh chim
            boolean isLightBackground =
                    r > 170 &&
                            gr > 170 &&
                            b > 170;

            if (isTransparent || isLightBackground) {
                result.setRGB(x, y, 0x00000000);

                queue.add(new Point(x + 1, y));
                queue.add(new Point(x - 1, y));
                queue.add(new Point(x, y + 1));
                queue.add(new Point(x, y - 1));
            }
        }

        return result;
    }

    private BufferedImage removeBirdBackgroundBorder(BufferedImage image) {
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);

                int a = (argb >> 24) & 0xff;
                int r = (argb >> 16) & 0xff;
                int g = (argb >> 8) & 0xff;
                int b = argb & 0xff;

                boolean isAlmostWhite = r > 235 && g > 235 && b > 235;
                boolean isAlmostBlack = r < 8 && g < 8 && b < 8;

                // Xóa nền trắng hoặc đen quanh ảnh chim
                if (a == 0 || isAlmostWhite || isAlmostBlack) {
                    result.setRGB(x, y, 0x00000000);
                } else {
                    result.setRGB(x, y, argb);
                }
            }
        }

        return result;
    }

    public Clip loadSound(String path) {
        if (sounds.containsKey(path)) {
            return sounds.get(path);
        }
        try (InputStream is = new BufferedInputStream(openStream(path))) {
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            sounds.put(path, clip);
            return clip;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sound: " + path, e);
        }
    }

    public Font loadFont(String path, float size) {
        try (InputStream is = openStream(path)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font: " + path, e);
        }
    }
}
