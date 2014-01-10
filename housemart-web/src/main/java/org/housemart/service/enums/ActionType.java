package org.housemart.service.enums;

public enum ActionType {

	Commit(1),  //登盘
	Update(2),  //更新
	Add(3),   //添加
	Delete(4), //删除
	View(5); //查看
	
	private int type;	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	private ActionType(int type){
		this.type = type;
	}
	
	
}
