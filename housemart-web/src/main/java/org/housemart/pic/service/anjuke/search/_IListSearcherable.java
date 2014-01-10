/**
 * Created on 2013-4-5
 * 
 */
package org.housemart.pic.service.anjuke.search;

import java.util.List;

public interface _IListSearcherable<EntitiesWrapper, Entity> {

	EntitiesWrapper search(int city, String name, int pageSize, int page) throws Exception;

	String searchJsonResult(int city, String name, int pageSize, int page) throws Exception;

	List<Entity> search(int city, String name) throws Exception;

}
