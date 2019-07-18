package my.sample.dao.entity.dict;

public enum TreeState {

	selected(){},//节点处于被选中状态
	opened(){},//节点处于打开状态
	disabled(){},//节点不可选
	checked(){},//用于checkbox插件 - 勾选该checkbox(只有当 tie_selection 处于 false时有效)
	undetermined(){},//用于checkbox插件 - 状态待定 (只有启用懒加载并且节点没有被加载时生效)
	;
	
	private TreeState()
	{
		
	}
}
