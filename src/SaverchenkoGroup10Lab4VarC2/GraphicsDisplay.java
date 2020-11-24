package SaverchenkoGroup10Lab4VarC2;

import javax.swing.*;
import java.awt.*;

public class GraphicsDisplay extends JPanel {

    private Double[][] graphicsData;

    private boolean showAxis = true;
    private boolean showMarkers = false;
    private boolean showMarkersCondition = false;
    private boolean showGrid = false;
    private boolean turnGraph = false;

    private BasicStroke axisStroke;
    private BasicStroke markerStroke;
    private BasicStroke graphicsStroke;

    private Font axisFont;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public GraphicsDisplay () {
        setBackground(Color.WHITE);
        /*buttCap - прямой roundCap - закругленный squareCap - добавляет полуквадрат в конце линии
        xf - width
        miterJoin - острое пересечение roundJoint - закругленное bevelJoin - квадратное нужны для пересечения линий
        miterLimit - 10 f если угол между двумя линиями <=11 градусов то угла нет ???
        dash_phase Определение того, как создать штриховой узор путем чередования непрозрачных и прозрачных участков.
        fond bold font italic - жирный или курсив PLAINT - DEFAULT */
        axisFont = new Font(Font.SANS_SERIF, Font.PLAIN, 36);

    }

    public void showGraphics(Double[][] graphicsData) {
        this.graphicsData = graphicsData;
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        repaint();
    }

    public void setShowMarkersCondition(boolean showMarkersCondition) {
        this.showMarkersCondition = showMarkersCondition;
        repaint();
    }
}

