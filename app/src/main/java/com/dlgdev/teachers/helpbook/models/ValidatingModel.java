package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;

public abstract class ValidatingModel extends Model {
	public void safelySave() {
		if(checkConstraints()) {
			save();
		} else {
			throw new InvalidModelException("The model data isn't valid. The data wasn't saved");
		}
	}

	protected abstract boolean checkConstraints();

	public boolean isPersisted() {
		return this.getId() != null;
	}
}
