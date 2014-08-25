package org.jackychen.toolkits.session.filter;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class UsersOnlineCountListener implements HttpSessionBindingListener,Serializable {
	int uid;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
		
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println(uid + "œ¬œﬂ¡À" );
	}
}