package junit.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencloudb.sequence.handler.IncrSequenceMySQLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jc.dao.app.IEmployee;
import com.jc.dao.app.ITb2;
import com.jc.domain.EmployeeEntity;
import com.jc.domain.Tb2Entity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/daoxmlconfigs/applicationContext.xml")
public class EmployeeTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private IEmployee employeeDao;

	@Test
	public void test() {

	}

	@Test
	public void saveTest() {
	/*	EmployeeEntity entity = new EmployeeEntity();
		for (int i = 1; i < 2; i++) {
			entity.setId((int) IncrSequenceMySQLHandler.getInstance().nextId(
					"EMPLOYEE"));
			entity.setName("jackychen" + i);
			entity.setSharding_id(i % 2 == 0 ? 10000 : 10010);
			Integer incre = employeeDao.save(entity);
			System.out.println(incre);
		}*/
		/*
		 * for(int i=1;i<2000;i++) { Tb2Entity entity=new Tb2Entity();
		 * entity.setId(i); entity.setVarset(String.valueOf(i));
		 * tb2Dao.save(entity); }
		 * 
		 * System.out.println("Done");
		 */
	}

	/**
	 * 测试update
	 */
	/*
	 * @Test public void update() { Tb2Entity entity=new Tb2Entity();
	 * entity.setId(2323); entity.setVarset("chenzhao"); tb2Dao.update(entity);
	 * }
	 */
	/**
	 * 测试deleteByIds
	 */
	/*
	 * @Test public void deleteByIds() { String ids="1,2,2323,513";
	 * tb2Dao.deleteByIds(ids); System.out.println("Done"); }
	 */
	/**
	 * 测试getById
	 */

	/*@Test
	public void getById() {
		Tb2Entity entity = tb2Dao.getById(1022);
		System.out.println(entity == null ? "Nothing" : entity.getId());
	}*/

	/**
	 * 测试queryAll
	 */
	@Test
	public void queryAll() {
		List<EmployeeEntity> lEntity = employeeDao.queryAll();
		for (EmployeeEntity entity : lEntity) {
			System.out.println(entity.getId());
		}
	}

	/**
	 * 测试queryByMap
	 */
	@Test
	public void queryByMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "jackychen1");
		List<EmployeeEntity> lEntity = employeeDao.queryByMap(map);
		for (EmployeeEntity entity : lEntity) {
			System.out.println(entity.getName() + "___"
					+ entity.getSharding_id());
		}

	}
}
