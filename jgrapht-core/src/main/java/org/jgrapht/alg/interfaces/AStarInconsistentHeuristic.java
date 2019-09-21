/*
 * (C) Copyright 2015-2018, by Brooks Bockman and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */
package org.jgrapht.alg.interfaces;

import java.util.*;

import org.jgrapht.*;
/**
 * Interface for an admissible but inconsistent heuristic used in A* search.
 * This interface is based primarily on {@link AStarAdmissibleHeuristic}, but
 * includes additional methods to update inconsistent heuristics, helping to
 * optimize A* search with inconsistent heuristics.  This interface is meant
 * to implement a BPMX style update step.  This update essentially checks the
 * heuristic values of a vertices neighbors to determine consistency, and if
 * it is inconsistent the heuristic estimate is increased to the lowest value
 * that would provide consistency.  Note, this BPMX approach does not fully 
 * convert a inconsistent heuristic into a consistent one.
 * 
 * For a basic implementation of this interface refer to the test repository
 * class ALTInconsistentHeuristic.
 *
 * @param <V> vertex type
 * @author Brooks Bockman 
 */
public interface AStarInconsistentHeuristic<V>
    extends
    AStarAdmissibleHeuristic<V>
{
    /**
     * Updates the heuristic estimate for the node being expanded by A* search.
     * 
     * When a node is expanded its heuristic is checked relative to its ancestors.
     * If the current node heuristic is too low to be consistent based on this, the
     * value is increased, reducing the number of total node expansions required.
     * 
     * Note that the updated heuristic must be placed into the hScoreMap for future use.
     * 
     * @param expandedVertex the vertex being expanded
     * @param targetVertex the target vertex in the search
     * @param hScoreMap mapping for current heuristic values
     * @return updated value for the heuristic for the expandedVertex
     */
    public double updateExpandedHeuristic(V expandedVertex, V targetVertex, Map<V, Double> hScoreMap);
    
    /**
     * Updates the heuristic estimate for a successor node during node expansion
     * in A* search.
     * 
     * When an expanded nodes successors heuristics are looked at, they  can
     * have their value increased if their heuristic is too low to be consistent
     * with the expanded node.  This helps reduce the total number of node
     * expansions.
     * 
     * Note that the updated heuristic must be placed into the hScoreMap for future use;
     * 
     * @param successor the successor vertex being looked at
     * @param targetVertex the target vertex in the search
     * @param hParent heuristic value of the parent vertex
     * @param edgeWeight edge weight between parent and successor vertices
     * @param hScoreMap mapping for current heuristic values
     * 
     * @return returns true if the heuristic was successfully changed
     */
    public boolean updateSuccessorHeuristic(V successor, V targetVertex, double hParent, double edgeWeight, Map<V, Double> hScoreMap);
    
    /**
     * It is assumed that this type will only be used with inconsistent heuristics.
     * If a consistent or undetermined heuristic is to be used, an implementation of 
     * {@link AStarAdmissibleHeuristic} is advised. 
     *
     * @param graph graph to test heuristic on
     * @param <E> graph edges type
     * @return false by assumption
     */
    default <E> boolean isConsistent(Graph<V, E> graph)
    {
        return false;
    }
}
