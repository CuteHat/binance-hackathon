package com.example.betelgeuseapi.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Map;

@Service
@Slf4j
public class CardProcessorService {

    /**
     * Renders the given service provider name, rank, stars and XP on the NFT card image.
     *
     * @param image     The base image to render on
     * @param SPName    The service provider name
     * @param rank      The rank of the service provider
     * @param rankTitle The title of the rank of the service provider
     * @param xp        The experience points earned by the nft owner
     */
    @SneakyThrows
    public void render(BufferedImage image, String SPName, int rank, String rankTitle, BigInteger xp) {
        // Service provider text
        Font krungthepFont20 = constructKrungthepFont(20f);
        drawCenteredText(image, SPName, krungthepFont20, Color.decode("#FFFFFF"), 80f);

        // Rank text
        Font krungthepFont24 = constructKrungthepFont(24f);
        float centerY = image.getHeight() / 2f;
        drawCenteredText(image, rankTitle, krungthepFont24, Color.decode("#868588"), centerY);

        // stars
        BufferedImage overlayImage = loadStarVisual();
        drawImage(image, overlayImage, rank, 1, image.getHeight() - 110);

        // Xp text
        Font krungthepFont14 = constructFiragoFont(14f);
        drawCenteredText(image, formatXp(xp), krungthepFont14, Color.decode("#868588"), image.getHeight() - 50);
    }

    /**
     * Draws the given text on the given image at the given yPosition, centered horizontally.
     *
     * @param image     The base image to draw on
     * @param text      The text to draw
     * @param font      The font to use for the text
     * @param color     The color of the text
     * @param yPosition The y position to draw the text on
     */
    private void drawCenteredText(BufferedImage image, String text, Font font, Color color, float yPosition) {
        Graphics2D imageGraphics = (Graphics2D) image.getGraphics();

        // Set rendering hints for better text quality
        imageGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        imageGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        imageGraphics.setFont(font);
        imageGraphics.setColor(color);

        FontMetrics fontMetrics = imageGraphics.getFontMetrics(font);
        int textWidth = fontMetrics.stringWidth(text);

        double width = (image.getWidth() * 1.0 - textWidth) / 2;
        imageGraphics.drawString(text, (float) width, yPosition);
    }

    /**
     * Draws the given overlay image multiple times based on the given count, padding, and y-position.
     *
     * @param baseImage    the base image to draw on
     * @param overlayImage the overlay image to draw multiple times
     * @param count        the number of times to draw the overlay image
     * @param padding      the padding between each overlay image
     * @param y            the y-position of the overlay image in the base image
     */
    private void drawImage(BufferedImage baseImage, BufferedImage overlayImage, int count, int padding, int y) {
        Graphics2D imageGraphics = (Graphics2D) baseImage.getGraphics();

        // Set rendering hints for better image quality
        imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int totalWidth = count * overlayImage.getWidth() + (count - 1) * padding;
        int startX = (baseImage.getWidth() - totalWidth) / 2;

        for (int i = 0; i < count; i++) {
            int x = startX + i * (overlayImage.getWidth() + padding);
            imageGraphics.drawImage(overlayImage, x, y, null);
        }
    }

    private String formatXp(BigInteger xp) {
        return xp.toString().concat("XP");
    }

    private Font constructKrungthepFont(float size) {
        Font xFont;
        try {
            xFont = Font.createFont(Font.TRUETYPE_FONT, loadKrungthepFont());
        } catch (FontFormatException | IOException exception) {
            log.error("can't create font", exception);
            throw new RuntimeException(exception);
        }

        Map attributes = xFont.getAttributes();
        attributes.put(TextAttribute.SIZE, size);

        return xFont.deriveFont(attributes);
    }

    private Font constructFiragoFont(float size) {
        Font xFont;
        try {
            xFont = Font.createFont(Font.TRUETYPE_FONT, loadFiragoFont());
        } catch (FontFormatException | IOException exception) {
            log.error("can't create font", exception);
            throw new RuntimeException(exception);
        }

        Map attributes = xFont.getAttributes();
        attributes.put(TextAttribute.SIZE, size);

        return xFont.deriveFont(attributes);
    }

    private static InputStream loadFiragoFont() {
        try {
            ClassPathResource resource = new ClassPathResource("static/FiraGO-Bold.ttf");
            return resource.getInputStream();
        } catch (FileNotFoundException exception) {
            log.error("can't read font", exception);
            throw new RuntimeException(exception);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream loadKrungthepFont() {
        try {
            ClassPathResource resource = new ClassPathResource("static/Krungthep.ttf");
            return resource.getInputStream();
        } catch (FileNotFoundException exception) {
            log.error("can't read font", exception);
            throw new RuntimeException(exception);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage loadStarVisual() {
        try {
            ClassPathResource resource = new ClassPathResource("static/star.png");
            InputStream inputStream = resource.getInputStream();
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            log.error("can't read X visual", exception);
            throw new RuntimeException(exception);
        }
    }

}
