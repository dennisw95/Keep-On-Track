package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		Dialog.show("About", "Name: Dennis Worley JR\nCourse: CSC 133\nVersion No. 3", "OK", null);
	}
}
