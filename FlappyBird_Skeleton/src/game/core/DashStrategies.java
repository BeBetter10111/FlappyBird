package game.core;

import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.image.BufferedImage;


public class AssetLoader {

    // TODO: khai báo 2 map cache cho image và sound

    /**
     * Load 1 ảnh từ đường dẫn (đã có sẵn trong AssetPaths).
     * Nếu đã cache → trả về luôn, chưa thì đọc file → cache → trả về.
     */
    public BufferedImage loadImage(String path) {
        return null;
    }

    /**
     * Load ảnh rồi scale lên `scale` lần (giữ pixel art không bị mờ).
     * Dùng RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR.
     */
    public BufferedImage loadScaledImage(String path, int scale) {
        // TODO: implement
        return null;
    }

    /**
     * Load file WAV và trả về Clip đã open sẵn.
     */
    public Clip loadSound(String path) {
        // TODO: implement
        return null;
    }

    /**
     * Load file TTF và trả về Font với size yêu cầu.
     */
    public Font loadFont(String path, float size) {
        // TODO: implement
        return null;
    }
}
