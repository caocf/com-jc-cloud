// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CreateGroupNodeHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			CreateNodeHandler

public class CreateGroupNodeHandler extends CreateNodeHandler
{

	public CreateGroupNodeHandler()
	{
		this("/SESSIONS");
	}

	protected CreateGroupNodeHandler(String id)
	{
		super(id, null);
	}
}
