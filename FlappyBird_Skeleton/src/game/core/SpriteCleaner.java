package game.core;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Utility xử lý sprite — xoá viền sáng/trắng quanh ảnh (SRP).
 *
 * Thuật toán: BFS flood-fill từ 4 cạnh ảnh, xoá mọi pixel sáng liền kề
 * (R, G, B đều > LIGHT_THRESHOLD) bằng cách set alpha = 0.
 *
 * Chỉ xoá pixel "liên thông" với viền → KHÔNG xoá pixel sáng nằm
 * giữa sprite (vd đốm trắng trên cánh chim).
 */
public final class SpriteCleaner {

    private static final int LIGHT_THRESHOLD = 170;   // R/G/B > 170 = sáng
    private static final int ALPHA_THRESHOLD = 20;    // alpha < 20 = đã trong suốt

    private SpriteCleaner() {}

    public static BufferedImage removeLightBackground(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(src, 0, 0, null);
        g.dispose();

        boolean[][] visited = new boolean[h][w];
        Queue<Point> queue = new ArrayDeque<>();

        for (int x = 0; x < w; x++) {
            queue.add(new Point(x, 0));
            queue.add(new Point(x, h - 1));
        }
        for (int y = 0; y < h; y++) {
            queue.add(new Point(0, y));
            queue.add(new Point(w - 1, y));
        }

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;

            if (x < 0 || x >= w || y < 0 || y >= h) continue;
            if (visited[y][x]) continue;
            visited[y][x] = true;

            int argb = result.getRGB(x, y);
            int a  = (argb >> 24) & 0xff;
            int r  = (argb >> 16) & 0xff;
            int gr = (argb >> 8)  & 0xff;
            int b  =  argb        & 0xff;

            boolean isTransparent = a < ALPHA_THRESHOLD;
            boolean isLight = r > LIGHT_THRESHOLD
                    && gr > LIGHT_THRESHOLD
                    && b > LIGHT_THRESHOLD;

            if (isTransparent || isLight) {
                // Set pixel này trong suốt
                result.setRGB(x, y, 0x00000000);
                // Lan sang 4 hướng
                queue.add(new Point(x + 1, y));
                queue.add(new Point(x - 1, y));
                queue.add(new Point(x, y + 1));
                queue.add(new Point(x, y - 1));
            }
        }

        return result;
    }
}