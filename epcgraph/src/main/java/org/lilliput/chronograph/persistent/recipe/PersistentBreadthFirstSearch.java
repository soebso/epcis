package org.lilliput.chronograph.persistent.recipe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BsonArray;

import org.lilliput.chronograph.common.LoopPipeFunction;
import org.lilliput.chronograph.common.TemporalType;
import org.lilliput.chronograph.common.Tokens.AC;
import org.lilliput.chronograph.common.Tokens.FC;
import org.lilliput.chronograph.common.Tokens.Position;
import org.lilliput.chronograph.persistent.ChronoGraph;
import org.lilliput.chronograph.persistent.ChronoVertex;
import org.lilliput.chronograph.persistent.VertexEvent;
import org.lilliput.chronograph.persistent.engine.TraversalEngine;

import com.tinkerpop.pipes.PipeFunction;

/**
 * Copyright (C) 2016-2017 Jaewook Byun
 * 
 * Temporal Property Graph Recipes
 * 
 * @author Jaewook Byun, Ph.D candidate
 * 
 *         Korea Advanced Institute of Science and Technology (KAIST)
 * 
 *         Real-time Embedded System Laboratory (RESL)
 * 
 *         bjw0829@kaist.ac.kr, bjw0829@gmail.com
 * 
 */
public class PersistentBreadthFirstSearch {

	private ConcurrentHashMap<ChronoVertex, Long> gamma = new ConcurrentHashMap<ChronoVertex, Long>();

	@SuppressWarnings("rawtypes")
	public Map compute(ChronoGraph g, VertexEvent source, BsonArray labels, TemporalType typeOfEvent, AC tt, AC s, AC e,
			AC ss, AC se, AC es, AC ee, Position pos, String order) {

		// order = forward / backward

		gamma.put(source.getVertex(), source.getTimestamp());

		PipeFunction<VertexEvent, Boolean> exceedBound2 = new PipeFunction<VertexEvent, Boolean>() {
			@Override
			public Boolean compute(VertexEvent ve) {
				if (order.equals("forward")) {
					if (gamma.containsKey(ve.getVertex()) && (ve.getTimestamp() >= gamma.get(ve.getVertex()))) {
						return false;
					}
				} else {
					if (gamma.containsKey(ve.getVertex()) && (ve.getTimestamp() <= gamma.get(ve.getVertex()))) {
						return false;
					}
				}

				return true;
			}
		};

		PipeFunction<List<VertexEvent>, Object> storeGamma = new PipeFunction<List<VertexEvent>, Object>() {
			@Override
			public Object compute(List<VertexEvent> vertexEvents) {
				vertexEvents.parallelStream().forEach(ve -> {
					gamma.put(ve.getVertex(), ve.getTimestamp());
				});
				return null;
			}
		};

		LoopPipeFunction exitIfEmptyIterator = new LoopPipeFunction() {
			@Override
			public boolean compute(Object argument, Map<Object, Object> currentPath, int loopCount) {

				List list = (List) argument;
				// System.out.println(list.size());
				if (list == null || list.size() == 0)
					return false;

				return true;
			}
		};

		TraversalEngine pipeLine = new TraversalEngine(g, source, true, true, VertexEvent.class);
		pipeLine = pipeLine.as("s");
		pipeLine = pipeLine.scatter();
		if (order.equals("forward"))
			pipeLine = pipeLine.oute(labels, typeOfEvent, tt, s, e, ss, se, es, ee, pos);
		else
			pipeLine = pipeLine.ine(labels, typeOfEvent, tt, s, e, ss, se, es, ee, pos);
		pipeLine = pipeLine.filter(exceedBound2);
		pipeLine = pipeLine.gather();
		// 방문한 버텍스 중 최소만을 꼽는다 해당 스텝에서 도달 한 것 중
		if (order.equals("forward"))
			pipeLine = pipeLine.elementDedup(FC.$min);
		else
			pipeLine = pipeLine.elementDedup(FC.$max);
		// lower bound 보다 크면 필터한다.
		// lower bound 가 없는 것은 무한대
		// pipeLine = pipeLine.transform(exceedBound, List.class);
		pipeLine = pipeLine.sideEffect(storeGamma);
		// pipeLine = pipeLine.pathEnabledTransform(historyPipe, List.class);
		// pipeLine.storeTimestamp(bound);
		// pipeLine.pathFilter(bound, );
		// pipeLine = pipeLine.sideEffect(storeCurrentVertexEvents);
		pipeLine = pipeLine.loop("s", exitIfEmptyIterator);
		return pipeLine.path();
	}
}
