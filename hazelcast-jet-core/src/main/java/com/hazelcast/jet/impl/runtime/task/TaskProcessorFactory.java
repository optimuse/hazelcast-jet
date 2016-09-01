/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.impl.runtime.task;

import com.hazelcast.jet.dag.Vertex;
import com.hazelcast.jet.impl.actor.Consumer;
import com.hazelcast.jet.impl.actor.Producer;
import com.hazelcast.jet.impl.job.JobContext;
import com.hazelcast.jet.processor.Processor;
import com.hazelcast.jet.processor.ProcessorContext;

/**
 * Interface which represents factory to create tasks' execution processors;
 * <p>
 * Each processor has following structure:
 * <p>
 * Producers --&gt; Processor -&gt; Consumers;
 */
public interface TaskProcessorFactory {
    /**
     * Construct processors in case when there is no producers or consumers;
     *
     * @param processor        - user-level processor;
     * @param jobContext       - job context;
     * @param processorContext - user-level processor context;
     * @param vertex           - corresponding vertex of DAG;
     * @param taskID           - id of task;
     * @return - task processor;
     */
    TaskProcessor simpleTaskProcessor(Processor processor,
                                      JobContext jobContext,
                                      ProcessorContext processorContext, Vertex vertex,
                                      int taskID);

    /**
     * Construct processors in case when there are just consumers without producers;
     *
     * @param consumers        - list of output consumers;
     * @param processor        - user-level processor;
     * @param jobContext       - job context;
     * @param processorContext - user-level processor context;
     * @param vertex           - corresponding vertex of DAG;
     * @param taskID           - id of task;
     * @return - task processor;
     */
    TaskProcessor consumerTaskProcessor(Consumer[] consumers,
                                        Processor processor,
                                        JobContext jobContext,
                                        ProcessorContext processorContext, Vertex vertex,
                                        int taskID);

    /**
     * Construct processors in case when there are just producers without consumers;
     *
     * @param producers        -   list of input  producers;
     * @param processor        -   user-level processor;
     * @param jobContext       -   job context;
     * @param processorContext -   user-level processor context;
     * @param vertex           -   corresponding vertex of DAG;
     * @param taskID           -   id of task;
     * @return - task processor;
     */
    TaskProcessor producerTaskProcessor(Producer[] producers,
                                        Processor processor,
                                        JobContext jobContext,
                                        ProcessorContext processorContext, Vertex vertex,
                                        int taskID);

    /**
     * @param producers        - list of the input producers;
     * @param consumers        - list of the output consumers;
     * @param processor        - user-level processor;
     * @param jobContext       - job context;
     * @param processorContext - user-level processor context;
     * @param vertex           - corresponding vertex of DAG;
     * @param taskID           - id of task;
     * @return - task processor;
     */
    TaskProcessor actorTaskProcessor(Producer[] producers,
                                     Consumer[] consumers,
                                     Processor processor,
                                     JobContext jobContext,
                                     ProcessorContext processorContext, Vertex vertex,
                                     int taskID);

    /**
     * Determines type of processor (empty, producer-only, consumer-only, producer-consumer);
     *
     * @param producers        - list of input producers;
     * @param consumers        - list of output consumers;
     * @param jobContext       - job context;
     * @param processorContext - user-level processor context;
     * @param processor        - user-level processor;
     * @param vertex           - corresponding vertex of DAG;
     * @param taskID           - id of task;
     * @return - task processor;
     */
    TaskProcessor getTaskProcessor(Producer[] producers,
                                   Consumer[] consumers,
                                   JobContext jobContext,
                                   ProcessorContext processorContext,
                                   Processor processor,
                                   Vertex vertex,
                                   int taskID);
}