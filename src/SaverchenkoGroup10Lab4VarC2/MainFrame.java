package SaverchenkoGroup10Lab4VarC2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JFileChooser fileChooser = null;
    private boolean fileLoaded = false;

    public MainFrame() {

        super("Построение графиков функций на основе заранее подготовленных файлов");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        Image img = kit.getImage("src/SaverchenkoGroup10Lab4VarC2/icon.PNG");
        setIconImage(img);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file = menuBar.add(new JMenu("Файл"));
        JMenu graphics = menuBar.add(new JMenu("График"));
        JMenuItem open = file.add(new JMenuItem("Открыть файл с графиком"));
        open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("C:\\Users\\SergeySaber\\IdeaProjects\\lab3p"));
                }
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    openGraphics(fileChooser.getSelectedFile());
            }
        });
    }

    protected void openGraphics (File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            Double[][] graphicsData = new Double[in.available() / (Double.SIZE / 8) / 4][];
            int i = 0;
            while (in.available() > 0) {
                double x = in.readDouble();
                double y = in.readDouble();
                in.skipBytes(2*(Double.SIZE/8));
                graphicsData[i++] = new Double[]{x, y};
            }
            for (int k =0; k<graphicsData.length; k++)
            {
                for (int j = 0; j<graphicsData[0].length; j++)
                    System.out.print(graphicsData[k][j]+" ");
                System.out.println();
            }
            if (graphicsData.length > 0)
                fileLoaded = true;

            in.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "Указанный файл не найден",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "Ошибка чтения координат точек из файла",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}