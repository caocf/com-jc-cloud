package org.jackychen.toolkits.component;

import java.util.EventListener;

public interface LifeCycle
{

	public abstract void start()
		throws Exception;

	public abstract void stop()
		throws Exception;

	public abstract boolean isStarted();

	public abstract boolean isStopped();
	
}