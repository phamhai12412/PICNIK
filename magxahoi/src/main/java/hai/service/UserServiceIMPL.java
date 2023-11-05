package hai.service;

import hai.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserServiceIMPL implements IGenericService<User, Integer> {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer userID) {
        User user = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetUserByID(?)}");
            callSt.setInt(1, userID);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(rs.getBoolean("Role"));
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(Integer integer) {

    }

    public User findUserByEmailAndPassword(String email, String password) {
        User user = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetUserByEmailAndPassword(?, ?)}");
            callSt.setString(1, email);
            callSt.setString(2, password);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(rs.getBoolean("Role"));
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
    public void updateUserAvatar(int userId, String newAvatar) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL UpdateUserAvatar(?, ?)}");
            callSt.setInt(1, userId);
            callSt.setString(2, newAvatar);
            callSt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertUser(User newUser) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL AddUser(?, ?, ?, ?)}");
            callSt.setString(1, newUser.getName());
            callSt.setString(2, newUser.getEmail());
            callSt.setString(3, newUser.getPassword());
            callSt.setString(4, newUser.getPhone());
            callSt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean checkEmailExists(String email) {
        Connection connection = null;
        boolean emailExists = false;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL CheckEmailExists(?, ?)}");
            callSt.setString(1, email);
            callSt.registerOutParameter(2, Types.BOOLEAN);
            callSt.execute();

            emailExists = callSt.getBoolean(2);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return emailExists;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetAllUsers()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(rs.getBoolean("Role"));

                userList.add(user);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return userList;
    }
    public void updateUserStatus(int userId, boolean newStatus) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL UpdateUserStatus(?, ?)}");

            // Đặt giá trị cho các tham số đầu vào của stored procedure
            callSt.setInt(1, userId);
            callSt.setBoolean(2, newStatus);

            callSt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<User> searchUsers(String searchKeyword) {
        Connection connection = null;
        List<User> userList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL SearchUsers(?)}");
            callSt.setString(1, searchKeyword);

            ResultSet resultSet = callSt.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                boolean status = resultSet.getBoolean("status");


                User user = new User(userId, name, email, status);
                userList.add(user);
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return userList;
    }
    public int checkFriendship(int id1, int id2) {
        Connection connection = null;
        int  friendStatus=0;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL CheckFriendship(?, ?, ?)}");

            callSt.setInt(1, id1);
            callSt.setInt(2, id2);
            callSt.registerOutParameter(3, Types.INTEGER); // friendshipStatus

            callSt.execute();
friendStatus=callSt.getInt(3);


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return friendStatus;
    }

    public void deleteFriendship(int iduse1, int iduse2) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL DeleteFriendship(?, ?)}");

            // Đặt giá trị cho các tham số đầu vào của stored procedure
            callSt.setInt(1, iduse1);
            callSt.setInt(2, iduse2);

            callSt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void addFriendship(int iduse1, int iduse2) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL AddFriendship(?, ?)}");

            // Đặt giá trị cho các tham số đầu vào của stored procedure
            callSt.setInt(1, iduse1);
            callSt.setInt(2, iduse2);

            callSt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}