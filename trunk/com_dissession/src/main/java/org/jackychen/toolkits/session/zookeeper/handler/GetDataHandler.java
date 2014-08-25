
package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.zookeeper.AbstractZookeeperHandler;
import org.jackychen.toolkits.utils.SerializationUtils;


public class GetDataHandler extends AbstractZookeeperHandler
{

	protected String key;

	protected GetDataHandler(String id)
	{
		super(id);
	}

	public GetDataHandler(String id, String key)
	{
		this(id);
		this.key = key;
	}

	public Object handle()
		throws Exception
		
	{	
		String result=null;
		try
		{
		if (zookeeper != null)
		{
	/*		String dataPath = (new StringBuilder()).append("/SESSIONS/").append(id).append("/").append(key).toString();
			*/
			String path = "/SESSIONS/"+id.toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat != null)
			{
				String dataPath = "/SESSIONS/"+id.toString()+"/"+key.toString();
				stat = zookeeper.exists(dataPath, false);
				Object obj = null;
				if (stat != null)
				{
					byte data[] = zookeeper.getData(dataPath, false, null);
					if (data != null)
						//obj = SerializationUtils.deserialize(data);
						obj= new String(data);
				}
				return obj;
			}
		/*	byte[] bytes = zookeeper.getData("/SESSIONS/"+id.toString()+"/"+key.toString(), null, null);
			result = new String(bytes);*/
		
		}
		}catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
		//System.out.println(result);
		return (Object)result;
	}
}
