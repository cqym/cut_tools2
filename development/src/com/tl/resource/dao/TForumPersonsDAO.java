package  com.tl.resource.dao;

import com.tl.resource.dao.pojo.TForumPersons;
import com.tl.resource.dao.pojo.TForumPersonsExample;
import java.util.List;

public interface TForumPersonsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int countByExample(TForumPersonsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int deleteByExample(TForumPersonsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    void insert(TForumPersons record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    void insertSelective(TForumPersons record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    List selectByExample(TForumPersonsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    TForumPersons selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int updateByExampleSelective(TForumPersons record, TForumPersonsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int updateByExample(TForumPersons record, TForumPersonsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int updateByPrimaryKeySelective(TForumPersons record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_forum_persons
     *
     * @ibatorgenerated Sat Jun 01 09:30:46 CST 2013
     */
    int updateByPrimaryKey(TForumPersons record);
}