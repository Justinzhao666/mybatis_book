package top.justin.ch01.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import top.justin.ch01.model.Country;
import top.justin.ch02.mapper.BaseMapperTest;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CountryMapperTest extends BaseMapperTest {

    /*继承后不需要了*/
//    private static SqlSessionFactory sqlSessionFactory;

//    @BeforeClass
//    public static void init(){
//        try {
//            // 读取配置文件 创建SQLSessionFactory，同时解析mapper.xml文件获取到方法的解析。
//            // sqlSessionFactory就包含了所有配置+sql数据
//            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//            reader.close();
//        } catch (IOException ignore) {
//            ignore.printStackTrace();
//        }
//    }

    @Test
    public void testSelectAll(){
        try (SqlSession sqlSession = getSqlSession()) {
            // sqlSession会找到对应mapper下的对应方法,然后执行sql语句。
            // ---不是的他会找所有的sqlsession识别的到的mapper文件下该方法id
            // 如果只要一个需要使用全限定类名来指定。
            List<Country> countryList = sqlSession.selectList("top.justin.ch02.CountryMapper.selectAll");
            printCountryList(countryList);
        }
    }

    private void printCountryList(List<Country> countryList){
        for(Country country : countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }

}