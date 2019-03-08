package JavaTournament;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ScreenUtil {

    public static final Dimension LOGIN_PAGE_SIZE = new Dimension(260,160);
    public static final Dimension MAIN_PAGE_SIZE = new Dimension(800, 530);
    public static final Dimension SELECT_OPPONENT_PAGE_SIZE = new Dimension(  800,550);
    public static  final Dimension BATTLE_PAGE_SIZE = new Dimension(  850,500);

    public static Point getCenterPoint(Dimension dimension){

        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = environment.getScreenDevices();
        DisplayMode dmode = devices[0].getDisplayMode();
        int screenWidth = dmode.getWidth();
        int screenHeight = dmode.getHeight();

        //frame.setPreferredSize(new Dimension(LOGINPAGE_WIDTH, LOGINPAGE_HEIGHT));
        Dimension screenSize = new Dimension(screenWidth, screenHeight);
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - ( dimension.width / 2),
                middle.y - (dimension.height / 2 ) );
        System.out.println(newLocation.x + " "+ newLocation.y);

        return newLocation;
    }


    public static Font getBeermoneyFont(float size){
        Font customFont;
        Font customFont18 = null;
        try {

            URL resource  = ScreenController.class.getResource("/beermoney.ttf");
            String TTFpath = Paths.get(resource.toURI()).toString();
            customFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(TTFpath));
            customFont18 = customFont.deriveFont(size); //11 шрифт

        }
        catch (FontFormatException | IOException | URISyntaxException e2) 	 {e2.printStackTrace();}
        System.out.println(customFont18);
        return customFont18;
    }

    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
}
