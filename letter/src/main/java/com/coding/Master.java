package com.coding;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Master {
    public final static String PATH = "D://stamp.bmp";
    private final static int WIDTH = 160;
    private final static int HEIGHT = 160;


    public static String drawCircularChapter(String name, String companyName, String numCode) {
        OutputStream os = null;
        InputStream is = null;
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(new Color(204, 41, 41));

        drawFiveStar(g, WIDTH / 2, HEIGHT / 2, HEIGHT / 6 - 3);
        g.setStroke(new BasicStroke(3));

        g.drawOval(2, 2, WIDTH - 4, HEIGHT - 4);
        g.setFont(new Font("宋体", Font.BOLD, 21));

        drawCenterMsg(g, name, WIDTH / 2 + 2, HEIGHT * 4 / 5);
        if (companyName != null && companyName.length() > 11) {
            int len = companyName.length();
            int fontSize = 18;
            switch (len) {
                case 12 -> fontSize = 20;
                case 13, 14 -> fontSize = 19;
                case 15 -> fontSize = 18;
                case 16 -> fontSize = 17;
                case 17 -> fontSize = 16;
                case 18 -> fontSize = 15;
                case 19, 20 -> fontSize = 14;
                case 21, 22 -> fontSize = 12;
                case 23, 24 -> fontSize = 10;
                case 25 -> fontSize = 9;
                default -> fontSize = 18;
            }
            g.setFont(new Font("宋体", Font.PLAIN, fontSize));
        }

        drawUpperMsg(g, name, WIDTH / 2, HEIGHT / 2, WIDTH / 2 - 10);


        g.setFont(new Font("宋体", Font.BOLD, 9));
        drawBelowNumber(g, numCode, WIDTH / 2, HEIGHT / 2, HEIGHT / 2);
        g.dispose();
        try {
            String imgFile = PATH;
            os = new FileOutputStream(imgFile);
            ImageIO.write(img, "bmp", os);

            img.flush();
            os.flush();
            return imgFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static void drawFiveStar(Graphics g, int x, int y, int r) {
        double ch = 72 * Math.PI / 180;
        int x1 = x,
                x2 = (int) (x - Math.sin(ch) * r),
                x3 = (int) (x + Math.sin(ch) * r),
                x4 = (int) (x - Math.sin(ch / 2) * r),
                x5 = (int) (x + Math.sin(ch / 2) * r);
        int y1 = y - r,
                y2 = (int) (y - Math.cos(ch) * r),
                y3 = y2,
                y4 = (int) (y + Math.cos(ch / 2) * r),
                y5 = y4;
        int bx = (int) (x + Math.cos(ch) * Math.tan(ch / 2) * r);
        int by = y2;

        Polygon a = new Polygon();
        Polygon b = new Polygon();
        a.addPoint(x2, y2);
        a.addPoint(x5, y5);
        a.addPoint(bx, by);

        b.addPoint(x1, y1);
        b.addPoint(bx, by);
        b.addPoint(x3, y3);
        b.addPoint(x4, y4);

        g.fillPolygon(a);
        g.fillPolygon(b);
    }

    public static void drawUpperMsg(Graphics2D gs, String message, int CENTERX, int CENTERY, int r) {
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length];
            System.arraycopy(messages2, 0, messages, 0, messages2.length);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) - 1;
            // 上坡度
            double ascent = -bounds.getY() - 2;
            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            double r2 = r - ascent;
            double x0 = CENTERX;
            double y0 = CENTERY - r + ascent;
            // 旋转角度
            double a = 2 * Math.asin(char_interval / (2 * r2));

            if (odd) {
                gs.drawString(messages[first], (float) (x0 - char_interval / 2), (float) y0);
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double aa = (i - first) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i], (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                            (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first - 1; i > -1; i--) {
                    double aa = (first - i) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(-aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                            (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double aa = (i - second + 0.3) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                            (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i + 0.7) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(-aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                            (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            }
        }
    }

    public static void drawBelowNumber(Graphics2D gs, String message, int CENTERX, int CENTERY, int r) {
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length - 1];
            System.arraycopy(messages2, 1, messages, 0, messages2.length - 1);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) + 4;
            // 上坡度
            double ascent = -bounds.getY();
            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            double r2 = r - ascent;
            double x0 = CENTERX;
            double y0 = CENTERY - r + ascent;
            // 旋转角度
            double a = 2 * Math.asin(char_interval / (2 * r2));
            if (odd) {
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double aa = (i - first + 0.5) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2 * Math.PI - aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                            2 * CENTERY - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i - 0.5) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2 * Math.PI + aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                            2 * CENTERY - (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double aa = (i - second + 1) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2 * Math.PI - aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                            2 * CENTERY - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2 * Math.PI + aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                            (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                            2 * CENTERY - (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            }
        }
    }

    public static void drawCenterMsg(Graphics2D gs, String message, int CENTERX, int CENTERY) {
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length];
            System.arraycopy(messages2, 0, messages, 0, messages2.length);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) - 1;

            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            if (odd) {
                gs.drawString(messages[first],
                        (float) (CENTERX - char_interval / 2 - char_interval / 10),
                        (float) CENTERY);
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double x = CENTERX + (i - first - 0.6) * char_interval;
                    gs.drawString(messages[i], (float) x,
                            (float) CENTERY);
                }
                // 中心点的左边
                for (int i = first - 1; i > -1; i--) {
                    double x = CENTERX - (first - i + 0.6) * char_interval;
                    gs.drawString(messages[i], (float) x,
                            (float) CENTERY);
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double x = CENTERX + (i - second - 0.1) * char_interval;
                    gs.drawString(messages[i], (float) x,
                            (float) CENTERY);
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double x = CENTERX - (first + 1 - i + 0.1) * char_interval;
                    gs.drawString(messages[i], (float) x,
                            (float) CENTERY);
                }

            }
        }
    }


    public static void main(String[] args) {
        drawCircularChapter("财务专用章", "汇丰银行", "500");
    }
}
