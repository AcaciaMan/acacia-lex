/*
 * Copyright 2014 Acacia Man
 * The program is distributed under the terms of the GNU General Public License
 * 
 * This file is part of acacia-lex.
 *
 * acacia-lex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * acacia-lex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * This file source code is taken from JUNG examples.
 */ 

package acaciagraph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import javax.swing.JFrame;

public class AcaciaGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    DirectedSparseGraph g = new DirectedSparseGraph();
    g.addVertex("<html>USER$<br>12</html>");
    g.addVertex("ALL_OBJECTS");
    g.addVertex("OBJAUTH$");
    g.addVertex("OBJ$");
    g.addEdge("Edge1", "USER$", "ALL_OBJECTS");
    g.addEdge("Edge2", "OBJAUTH$", "ALL_OBJECTS");
    g.addEdge("Edge3", "OBJ$", "ALL_OBJECTS");
    VisualizationImageServer vs =
      new VisualizationImageServer(
        new CircleLayout(g), new Dimension(300, 300));
    vs.setPreferredSize(new Dimension(350,350));
    // Show vertex
    vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    
    JFrame frame = new JFrame("DB Objects");
    frame.getContentPane().add(vs);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);    
    
    }
    
}
