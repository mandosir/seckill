package com.imooc.mvcdemo.enums;



public enum SeckillStateEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改");
	
	private int state;
	
	private String stateInfo;

	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @return the stateInfo
	 */
	public String getStateInfo() {
		return stateInfo;
	}
	
	//根据id获取秒杀状态
	public static SeckillStateEnum stateOf(int index) {
		for (SeckillStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
