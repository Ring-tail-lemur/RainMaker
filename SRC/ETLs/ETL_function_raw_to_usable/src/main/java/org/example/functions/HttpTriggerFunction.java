package org.example.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // 트랜잭션 시작

        Commit commit = new Commit();
        commit.setCommit_id(6L);
        commit.setCommit_num(289345798L);
        em.persist(commit);

        tx.commit();


        em.close();
        emf.close();

        return request.createResponseBuilder(HttpStatus.OK).body("Hello, ").build();
    }


    public void jdbc_Connection (){
        Connection conn = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://rainmaker-analytics-logic-mariadb.mariadb.database.azure.com:3306/rainmaker-mariadb-schema";
            conn = DriverManager.getConnection(url, "rainmaker@rainmaker-analytics-logic-mariadb", "dkffkrRhfldudndnjstnddl!");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO commit_copy (commit_copy_num) SELECT commit_copy_num FROM commit;";
            boolean execute = stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
