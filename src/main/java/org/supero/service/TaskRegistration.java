/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.supero.service;

import org.supero.model.StatusTask;
import org.supero.model.Task;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.Date;
import java.util.logging.Logger;

@Stateless
public class TaskRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Task> memberEventSrc;

    public void register(Task task) throws Exception {
        if(task.getId() == null){
        	log.info("Registering " + task.getTitulo());
            task.setDataCriacao(new Date());
            task.setStatustask(StatusTask.ABERTA);	
        }else{
        	log.info("Editing " + task.getTitulo());
        	String titulo = task.getTitulo();
        	StatusTask status = task.getStatustask();
        	task = em.find(Task.class, task.getId());
        	task.setStatustask(status);
        	task.setTitulo(titulo);
            task.setDataEdicao(new Date());
            task.setStatustask(task.getStatustask());
        }
    	
        em.persist(task);
        memberEventSrc.fire(task);
    }
    
    public void remove(Task task) throws Exception {
        log.info("Removing " + task.getTitulo());
        task = em.find(Task.class, task.getId());
        task.setDataRemocao(new Date());
        task.setStatustask(StatusTask.REMOVIDA);
        em.persist(task);
        memberEventSrc.fire(task);
    }
}
