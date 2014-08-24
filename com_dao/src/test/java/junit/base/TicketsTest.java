
package junit.base;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jc.dao.app.ITb2;
import com.jc.dao.app.ITickets;
import com.jc.dao.base.Dao;
import com.jc.domain.OrderEntity;
import com.jc.domain.Tb2Entity;
import com.jc.domain.base.TicketsEntity;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/daoxmlconfigs/applicationContext.xml")
public class TicketsTest extends AbstractJUnit4SpringContextTests {
	@Autowired
    private ITickets ticketsDao;
    @Test
    public void saveTest() {
    /*	TicketsEntity entity=new TicketsEntity();
    	entity.setStub("e");
    	Integer incre=ticketsDao.save(entity);
    	System.out.println("Return the global id:"+incre);*/
    
    }
}
