package application.module.templete;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 模版方法
 */
public abstract class JdbcTemplete {

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    protected PreparedStatement statement;

    protected ResultSet resultSet;

    public final void transaction(String sql, Object entity) throws SQLException {
        getStatement(sql);
        checkSql(sql);
        crud(entity);
        releaseResources();
    }

    protected void getStatement(String sql) throws SQLException {
        connection = dataSource.getConnection();
        this.statement = connection.prepareStatement(sql);
    }

    public String checkSql(String sql){
        if(sql.contains("select")){
            return "param";
        }
        return "execute";
    }

    protected abstract void crud(Object entity) throws SQLException;

    private void releaseResources() throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}
