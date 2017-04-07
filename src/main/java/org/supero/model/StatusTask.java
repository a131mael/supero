package org.supero.model;

public enum StatusTask {

	//TODO internacionalizar
	ABERTA("Aberta"),
	CONCLUIDA("Concluída"),
	REMOVIDA("Removida");
	
	private String label;
	
	StatusTask(String name){
		this.label = name;
	}
	
    public String getLabel() {
        return label;
    }
}
