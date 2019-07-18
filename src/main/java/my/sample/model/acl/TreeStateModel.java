package my.sample.model.acl;

import my.sample.model.BaseModel;

public class TreeStateModel extends BaseModel {
	private boolean selected;//节点处于被选中状态
	private boolean opened;//节点处于打开状态
	private boolean disabled;//节点不可选
	private boolean checked;//用于checkbox插件 - 勾选该checkbox(只有当 tie_selection 处于 false时有效)
	private boolean undetermined;//用于checkbox插件 - 状态待定 (只有启用懒加载并且节点没有被加载时生效)
	
	public boolean isSelected() {
		return selected;
	}

	public TreeStateModel setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public boolean isOpened() {
		return opened;
	}

	public TreeStateModel setOpened(boolean opened) {
		this.opened = opened;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public TreeStateModel setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	public boolean isChecked() {
		return checked;
	}

	public TreeStateModel setChecked(boolean checked) {
		this.checked = checked;
		return this;
	}

	public boolean isUndetermined() {
		return undetermined;
	}

	public TreeStateModel setUndetermined(boolean undetermined) {
		this.undetermined = undetermined;
		return this;
	}

}
