/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.supero.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.supero.model.StatusTask;

/**
 *
 * @author abimael Fidencio Combos na aplicação.
 */
@ManagedBean(name = "CombosEspeciaisMB")
@LocalBean
@ApplicationScoped
public class CombosEspeciaisMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static List<StatusTask> getStatusTask() {
		List<StatusTask> avaliableStatus = new ArrayList<>();
		avaliableStatus.add(StatusTask.ABERTA);
		avaliableStatus.add(StatusTask.CONCLUIDA);
		return avaliableStatus;
	}


}
