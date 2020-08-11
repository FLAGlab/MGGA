package Visualization;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Automata.Automaton;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

public class JungTest {

    private static String initialState = "";
    private static String[] finalState;
    private static String[] allStates;

    public static void show(String[] pVertex, String[] pEdge, String pInitialState, String[] pFinalState, Automaton M) {
        initialState = pInitialState;
        finalState = pFinalState;
        allStates = pVertex;

        DirectedSparseGraph<String, String> g = new DirectedSparseGraph<String, String>();
        for (String v : pVertex) {
            g.addVertex(v);
        }

        for (String v : pEdge) {
            String[] nEdge = v.split("-");
            g.addEdge(v, nEdge[1], nEdge[2]);
        }

        VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(
                new CircleLayout<String, String>(g), new Dimension(600, 500));
        vs.setPreferredSize(new Dimension(650, 550));

        /*** Muesta las etiquetas de los vetices ***/
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

        /*** Coloca el label del vertex en el centro ***/
        vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        /*** Muestras las etiquetas de los arcos ***/
        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        vs.getRenderContext().setLabelOffset(20);
        vs.getRenderContext().setEdgeLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String edgeName) {
                String label = edgeName.split("-")[0];
                if (label.equals(""))
                    label = "#";
                return label;
            }
        });

        /*** Determina como se mostraran los vertices ***/
        vs.getRenderer().setVertexRenderer(new MyRenderer());

        /*** Permite interactuar y mover los vertices ***/
        DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vs.setGraphMouse(graphMouse);

        // Creando el panel en la parte inferior y agregando componentes       
        JPanel panel = new JPanel(); // el panel no está visible en la salida      
        JLabel label = new JLabel("Introducir texto");
        JTextField tf = new JTextField(10); // acepta hasta 10 caracteres        
        JButton send = new JButton("Enviar");
        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // label.setText(tf.getText());
                boolean result = false;
                String cadena = "La cadena digitada es errada";
                int icono = JOptionPane.ERROR_MESSAGE;
                if(!tf.getText().equals("") || tf.getText() != null) {
                    result = M.leer(tf.getText());
                }
                
                if(result) {
                    cadena = "Cadena procesada correctamente";
                    icono = JOptionPane.INFORMATION_MESSAGE;
                }
                
                JOptionPane.showMessageDialog(null, cadena, "Cadena Procesada",
                        icono);
            }
        });
        
        panel.add(label); // Componentes agregados usando Flow Layout        
        panel.add(tf);
        panel.add(send);

        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.getContentPane().add(BorderLayout.CENTER, vs);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    static class MyRenderer implements Renderer.Vertex<String, String> {
        @Override
        public void paintVertex(RenderContext<String, String> rc, Layout<String, String> layout, String vertex) {
            boolean isPicked = rc.getPickedVertexState().isPicked(vertex);
            Point2D point = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, layout.transform(vertex));
            Shape shape = rc.getVertexShapeTransformer().transform(vertex);
            GraphicsDecorator graphics = rc.getGraphicsContext();
            Rectangle2D bounds = shape.getBounds2D();
            Stroke oldStroke = graphics.getStroke();
            Paint oldPaint = graphics.getPaint();
            double x = point.getX(), y = point.getY();

            // -----------------------------------------------------------------------------
            if (vertex.equals(initialState)) {
                List<Point2D> points = new ArrayList<Point2D>(allStates.length);
                Point2D p = layout.transform(vertex);
                // Mueve la flecha del estado inicial al mover un vertice
                for (String nVertex : allStates) {
                    if (!nVertex.equals(vertex)) {
                        points.add(layout.transform(nVertex));
                    }
                }
                double bestScore = Double.NEGATIVE_INFINITY, bestAngle = 0.0;
                for (Point2D q : points) {
                    for (Point2D r : points) {
                        double score = GGeometry.angleBetween(p, q, r);
                        if (score > bestScore) {
                            bestScore = score;
                            bestAngle = GGeometry.angle(p, q)
                                    + 0.5 * score * (GGeometry.crossProduct(p, q, r) > 0 ? 1 : -1);
                        }
                    }
                }
                double r = bounds.getHeight() / 2 + 2;
                AffineTransform transform = graphics.getTransform();

                graphics.setTransform(compose(translate(-r, 0), rotate(bestAngle), translate(x, y), transform));
                graphics.setStroke(rc.getEdgeArrowStrokeTransformer().transform(vertex));
                graphics.setPaint(rc.getEdgeDrawPaintTransformer().transform(vertex));

                graphics.draw(line(0, 0, -17, 0));

                Polygon arrowHead = new Polygon();
                arrowHead.addPoint(0, 0);
                arrowHead.addPoint(-5, -5);
                arrowHead.addPoint(-5, 5);
                graphics.draw(arrowHead);
                graphics.fill(arrowHead);
                graphics.setTransform(transform);

            }
            AffineTransform transform = graphics.getTransform();
            graphics.setTransform(compose(translate(x, y), transform));
            graphics.setStroke(rc.getVertexStrokeTransformer().transform(vertex));
            graphics.setPaint(isPicked && Color.CYAN != null ? Color.CYAN : oldPaint);
            graphics.fill(shape);
            graphics.setPaint(rc.getVertexDrawPaintTransformer().transform(vertex));
            graphics.draw(shape);
            // Dibuja un segundo circulo sobre los estado finales
            for (String fS : finalState) {
                if (vertex.equals(fS))
                    graphics.draw(transform(shape, scale(0.75, 0.75, bounds.getCenterX(), bounds.getCenterY())));
            }
            graphics.setTransform(transform);
            graphics.setStroke(oldStroke);
            graphics.setPaint(oldPaint);
        }

    }

    public static AffineTransform translate(double pDeltaX, double pDeltaY) {
        return AffineTransform.getTranslateInstance(pDeltaX, pDeltaY);
    }

    public static AffineTransform rotate(double pAngle) {
        return AffineTransform.getRotateInstance(pAngle);
    }

    public static AffineTransform rotate(double pCenterX, double pCenterY, double pAngle) {
        return AffineTransform.getRotateInstance(pAngle, pCenterX, pCenterY);
    }

    public static AffineTransform scale(double pFactorX, double pFactorY) {
        return AffineTransform.getScaleInstance(pFactorX, pFactorY);
    }

    public static AffineTransform scale(double pFactorX, double pFactorY, double pCenterX, double pCenterY) {
        return compose(translate(-pCenterX, -pCenterY), scale(pFactorX, pFactorY), translate(pCenterX, pCenterY));
    }

    public static AffineTransform compose(AffineTransform... pAffineTransforms) {
        AffineTransform result = new AffineTransform();
        for (AffineTransform affineTransform : pAffineTransforms) {
            if (affineTransform != null)
                result.preConcatenate(affineTransform);
        }
        return result;
    }

    public static Shape transform(Shape pShape, AffineTransform pTransform) {
        return pTransform.createTransformedShape(pShape);
    }

    public static Line2D line(double pX1, double pY1, double pX2, double pY2) {
        return new Line2D.Double(pX1, pY1, pX2, pY2);
    }
}
