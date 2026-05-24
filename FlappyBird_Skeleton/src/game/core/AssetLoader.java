package game.core;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;

/**
 * Load và cache PNG / WAV / TTF.
 *
 * Tip: dùng HashMap<String, BufferedImage> và HashMap<String, Clip>
 * để cache, tránh load lại file đã load.
 */
public class AssetLoader {

    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, Clip>          soundCache = new HashMap<>();

    private InputStream openStream(String path) {
        String withSlash = path.startsWith("/") ? path : "/" + path;
        InputStream is = AssetLoader.class.getResourceAsStream(withSlash);
        if (is != null) return is;

        File f = new File(path);
        if (f.exists()) {
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e) {
            }
        }

        throw new RuntimeException(
                "Asset not found: " + path
        );
    }

    /**
     * Load 1 ảnh từ đường dẫn (đã có sẵn trong AssetPaths).
     * Nếu đã cache → trả về luôn, chưa thì đọc file → cache → trả về.
     */
    public BufferedImage loadImage(String path) {
        if(imageCache.containsKey(path)){
            return imageCache.get(path);
        }

        try(InputStream is = openStream(path)){
            BufferedImage img = ImageIO.read(is);
            if(img == null){
                throw new RuntimeException("Image return null for: " + path);
            }
            imageCache.put(path, img);
            return img;
        }catch(IOException e){
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    /**
     * Load ảnh rồi scale lên `scale` lần (giữ pixel art không bị mờ).
     * Dùng RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR.
     */
    public BufferedImage loadScaledImage(String path, int scale) {
        BufferedImage original = loadImage(path);

        int w = original.getWidth() * scale;
        int h = original.getHeight() * scale;

        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );
        g.drawImage(original, 0,0 , w, h, null);
        g.dispose();

        return scaled;
    }

    /**
     * Load file WAV và trả về Clip đã open sẵn.
     */
    public Clip loadSound(String path) {
        // Check cache
        if (soundCache.containsKey(path)) {
            return soundCache.get(path);
        }

        try (InputStream is = new BufferedInputStream(openStream(path))) {
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            soundCache.put(path, clip);
            return clip;

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException("Failed to load sound: " + path, e);
        }
    }

    /**
     * Load file TTF và trả về Font với size yêu cầu.
     */
    public Font loadFont(String path, float size) {
        try (InputStream is = openStream(path)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Failed to load font: " + path, e);
        }
    }
}
