package SaverchenkoGroup10Lab4VarC2;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GraphicsDisplay extends JPanel {

    private Double[][] graphicsData;

    private boolean showAxis = true;
    private boolean showMarkers = false;
    private boolean showMarkersCondition = false;
    private boolean showGrid = false;
    private boolean turnGraph = false;

    private BasicStroke axisStroke;
    private BasicStroke modifiedGraphicsStroke;
    private BasicStroke markerStroke;
    private BasicStroke graphicsStroke;

    private Font axisFont;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private double scale;

    public GraphicsDisplay () {

        setBackground(Color.WHITE);
        /*buttCap - прямой roundCap - закругленный squareCap - добавляет полуквадрат в конце линии
        xf - width
        miterJoin - острое пересечение roundJoint - закругленное bevelJoin - квадратное нужны для пересечения линий
        miterLimit - 10 f если угол между двумя линиями <=11 градусов то угла нет ???
        dash_phase Определение того, как создать штриховой узор путем чередования непрозрачных и прозрачных участков.
        fond bold font italic - жирный или курсив PLAINT - DEFAULT */
        graphicsStroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        axisStroke = new BasicStroke(2f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        axisFont = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
        modifiedGraphicsStroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10f,new float[] {18,5,5,5,12,5,5,5}, 0f);
        markerStroke = new BasicStroke(1.5f);
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

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        repaint();
    }

    public void setTurnGraph(boolean turnGraph) {
        this.turnGraph = turnGraph;
        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (graphicsData == null || graphicsData.length ==0 ) return;

        minX = graphicsData[0][0];
        maxX = graphicsData[graphicsData.length-1][0];
        minY = graphicsData[0][1];
        maxY = minY;

        for (int i = 1; i<graphicsData.length; i++) {
            if (graphicsData[i][1]<minY) {
                minY = graphicsData[i][1];
            }
            if (graphicsData[i][1]>maxY) {
                maxY = graphicsData[i][1];
            }
        }

        double scaleX = getSize().getWidth() / (maxX - minX); //500/1
        double scaleY = getSize().getHeight() / (maxY - minY); //500/16

        scale = Math.min(scaleX, scaleY);

        if (scale == scaleY) {
            double xIncrement = (getSize().getWidth()/scale - (maxX - minX))/2;
            maxX += xIncrement;
            minX -= xIncrement;
        }

        if (scale == scaleX) {
            double yIncrement = (getSize().getHeight()/scale - (maxY - minY))/2;
            maxY += yIncrement;
            minY -= yIncrement;
        }

        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();

        if (showAxis) paintAxis(canvas);
        paintGraphics(canvas);
        if (showMarkers) paintMarkers(canvas);

        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);

    }

    protected void paintMarkers (Graphics2D canvas) {

        canvas.setStroke(markerStroke);

        for (Double[] point : graphicsData) {
            GeneralPath marker = new GeneralPath();
            if (showMarkersCondition && point[1].intValue()%2==0 && point[1].intValue()!=0)
                canvas.setColor(Color.RED);
            else
                canvas.setColor(Color.blue);
            Point2D.Double center = xyToPoint(point[0],point[1]);
            marker.moveTo(center.getX() + 2.75, center.getY() - 7);
            marker.lineTo(marker.getCurrentPoint().getX() - 5.5, marker.getCurrentPoint().getY());
            marker.moveTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() + 14);
            marker.lineTo(marker.getCurrentPoint().getX() + 5.5, marker.getCurrentPoint().getY());
            marker.moveTo(center.getX(), marker.getCurrentPoint().getY());
            marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() - 14);
            marker.moveTo(center.getX() - 7, center.getY() + 2.75);
            marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() - 5.5);
            marker.moveTo(marker.getCurrentPoint().getX() + 14, marker.getCurrentPoint().getY());
            marker.lineTo(marker.getCurrentPoint().getX(), marker.getCurrentPoint().getY() + 5.5);
            marker.moveTo(marker.getCurrentPoint().getX(), center.getY());
            marker.lineTo(marker.getCurrentPoint().getX() - 14, marker.getCurrentPoint().getY());
            canvas.draw(marker);
        }
        repaint();
    }

    protected void paintAxis(Graphics2D canvas) {
        canvas.setStroke(axisStroke);
        canvas.setColor(Color.black);
        canvas.setPaint(Color.black);
        canvas.setFont(axisFont);
        FontRenderContext context = canvas.getFontRenderContext();
        if (minX<=0.0 && maxX>=0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0, minY)));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(0, maxY);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() + 5, arrow.getCurrentPoint().getY() + 14);
            arrow.lineTo(arrow.getCurrentPoint().getX() - 10, arrow.getCurrentPoint().getY());
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
        }
        if (minY<=0.0 && maxY>=0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(minX, 0), xyToPoint(maxX, 0)));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(maxX, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() - 14, arrow.getCurrentPoint().getY() - 5);
            arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY() + 10);
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
        }

    }

    protected void paintGraphics(Graphics2D canvas) {

        if (showMarkers) {
            canvas.setStroke(modifiedGraphicsStroke);
            canvas.setColor(Color.gray);
        }
        else {
            canvas.setStroke(graphicsStroke);
            canvas.setColor(Color.red.brighter().brighter());
        }

        GeneralPath graphics = new GeneralPath();
        for (int i=0; i<graphicsData.length; i++) {
            Point2D.Double point = xyToPoint(graphicsData[i][0],
                    graphicsData[i][1]);
            if (i>0) {
                graphics.lineTo(point.getX(), point.getY());
            } else {
                graphics.moveTo(point.getX(), point.getY());
            }
        }
        canvas.draw(graphics);
    }

    protected Point2D.Double xyToPoint(double x, double y) {
        double deltaX = x - minX;
        double deltaY = maxY - y;
        return new Point2D.Double(deltaX*scale, deltaY*scale);
    }

}
