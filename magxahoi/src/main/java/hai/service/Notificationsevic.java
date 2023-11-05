package hai.service;

import hai.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Notificationsevic {
    @Autowired
    private DataSource dataSource;
    public Notification getLatestNotificationWithSender(int receiverId) {
        Connection connection = null;
        Notification notification = null;

        try {
            connection = dataSource.getConnection();
            CallableStatement callSt = connection.prepareCall("{CALL GetLatestNotificationWithSender(?)}");

            // Đặt giá trị cho tham số đầu vào của stored procedure
            callSt.setInt(1, receiverId);

            ResultSet resultSet = callSt.executeQuery();

            if (resultSet.next()) {
                int notificationId = resultSet.getInt("notification_id");
                String notificationContent = resultSet.getString("notification_content");
                boolean notificationStatus = resultSet.getBoolean("notification_status");
                Date notificationCreatedDate = resultSet.getDate("notification_createdDate");
                String senderName = resultSet.getString("sender_name");
                int senderId = resultSet.getInt("sender_id");

                notification = new Notification(
                        notificationId, notificationContent, receiverId, senderId, notificationCreatedDate, senderName,notificationStatus);

            }

            return notification;
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
