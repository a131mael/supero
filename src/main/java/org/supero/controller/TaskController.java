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
package org.supero.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.supero.model.Task;
import org.supero.service.TaskRegistration;
import org.supero.util.Util;

@Named
@ViewScoped
public class TaskController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private TaskRegistration memberRegistration;

	@Produces
	@Named
	private Task newMember;

	@PostConstruct
	public void initNewMember() {
		if (newMember == null) {
			Object obj = Util.getAtributoSessao("task");
			if (obj != null) {
				newMember = (Task) obj;
			} else {
				newMember = new Task();
			}
		}
	}

	public String addTask() {
		Util.addAtributoSessao("task", new Task());
		return "registerTask";
	}

	public String removeTask(Task task) {
		try {
			memberRegistration.remove(task);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removed!", "Remove successful");
			facesContext.addMessage(null, m);
			return "index";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Remove unsuccessful");
			facesContext.addMessage(null, m);
			return "error";
		}

	}

	public String editTask(Task task) {
		try {
			Util.addAtributoSessao("task", task);
			return "registerTask";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Edit unsuccessful");
			facesContext.addMessage(null, m);
			return "error";
		}

	}

	public String register() throws Exception {
		try {
			memberRegistration.register(newMember);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMember();
			return "index";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
			return "error";
		}

	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
