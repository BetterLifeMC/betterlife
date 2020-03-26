package me.gt3ch1.betterlife.commands;

public enum Commands {
	TOGGLEDOWNFALL("toggledownfall");
	
	private String command;
	Commands(String command) {
		this.command = command;
	}
	public String getCommand() {
		return this.command;
	}
}
