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
package org.supero.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import org.supero.model.StatusTask;
import org.supero.model.Task;

@ApplicationScoped
public class TaskRepository {

    @Inject
    private EntityManager em;

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

    public List<Task> findAll(boolean all) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = cb.createQuery(Task.class);
        Root<Task> member = criteria.from(Task.class);
        CriteriaQuery<Task> cq = criteria.select(member);
        
        if(!all){
        	final List<Predicate> predicates = new ArrayList<Predicate>();
            Predicate pred = cb.equal(member.get("statustask"), StatusTask.ABERTA);
            predicates.add(pred);
            Predicate pred2 = cb.equal(member.get("statustask"), StatusTask.CONCLUIDA);
            predicates.add(pred2);
            cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
         	
        }
        
        cq.orderBy(cb.asc(member.get("dataCriacao")));
        return em.createQuery(criteria).getResultList();
    }
}
