package my.sample.model.acl;

import java.util.ArrayList;
import java.util.List;

import my.sample.dao.primary.entity.AppResource;
import my.sample.model.BaseModel;

/**
 * 角色所拥有的菜单
 */
public class MenuModel extends BaseModel {

	private boolean column = false;// 是否为栏目
	private boolean hasColumns = false; // 是否存在子栏目
	private AppResource resource;// 菜单资源
	private List<MenuModel> columnMenu = new ArrayList<>();

	public boolean isColumn() {
		return column;
	}

	public void setColumn(boolean column) {
		this.column = column;
	}

	public boolean isHasColumns() {
		return hasColumns;
	}

	public void setHasColumns(boolean hasColumns) {
		this.hasColumns = hasColumns;
	}

	public AppResource getResource() {
		return resource;
	}

	public void setResource(AppResource resource) {
		this.resource = resource;
	}

	public List<MenuModel> getColumnMenu() {
		return columnMenu;
	}

	public void setColumnMenu(List<MenuModel> columnMenu) {
		this.columnMenu = columnMenu;
	}

}
