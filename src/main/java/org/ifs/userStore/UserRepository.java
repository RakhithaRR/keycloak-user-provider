package org.ifs.userStore;

import org.keycloak.storage.ReadOnlyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//import oracle.jdbc.driver.OracleDriver;

public class UserRepository {

    private List<User> users;
        private final String SELECT_QUERY = "SELECT USERNAME, USER_ID, ORACLE_USER, WEB_USER, DESCRIPTION FROM\n" +
                                            "DBA_USERS LEFT JOIN FND_USER_TAB\n" +
                                            "ON DBA_USERS.USERNAME = FND_USER_TAB.IDENTITY";

    private Connection connection = null;

    public UserRepository() {
        users = new ArrayList<>();

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
//            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
            System.out.println("Oracle driver not found!");
            System.out.println(System.getProperty("java.class.path"));
        }

        try{
            connection = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=cmbpde2293)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=s2293)))","ifsapp", "ifsapp");
        }
        catch (SQLException e){
            System.out.println("Connection error!");
        }

        if(connection != null){
            Statement stmt = null;
            System.out.println("Connected to OracleDB!");
            try{
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_QUERY);
                while(rs.next()){
                    String id = rs.getString("user_id");
                    String username = rs.getString("username");
                    String description = rs.getString("description");
                    String fname = "";
                    String lname = "";
                    if(description != null){
                        String[] names = description.split(" ");
                        if(names.length != 2){
                            fname = description;
                        }
                        else{
                            fname = names[0];
                            lname = names[1];
                        }

                    }

                    User user = new User(id,username,fname,lname);
                    users.add(user);
                }
                connection.close();
            }
            catch (SQLException e){
                System.out.println(e);

            }
        }
    }

    public List<User> getAllUsers() {
        return users;
    }

    public int getUsersCount() {
        return users.size();
    }

    public User findUserById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }

    public User findUserByUsernameOrEmail(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst().get();
    }

    public List<User> findUsers(String query) {
        return users.stream()
                .filter(user -> user.getUsername().contains(query))
                .collect(Collectors.toList());
    }

    public boolean validateCredentials(String username, String password) {
//        return findUserByUsernameOrEmail(username).getPassword().equals(password);
        try{
            connection = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=cmbpde2293)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=s2293)))",username, password);
            return true;
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("Connection error!");
            return false;
        }

//        if(connection != null){
//            return true;
//        }
//        return false;
    }

    public boolean updateCredentials(String username, String password) {
//        findUserByUsernameOrEmail(username).setPassword(password);
//        return true;
        throw new ReadOnlyException("Read-only User!");
    }
}
