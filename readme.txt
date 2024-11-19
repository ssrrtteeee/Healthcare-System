Nurses
Format is Number, Username, Password
1, test, test
2, username, password
3, AlbC, goodPassword
4, Myk, newPassword


Jacob Wilson stored procedure:
USE cs3230f24h;

DROP PROCEDURE IF EXISTS update_test_result;
DELIMITER $
CREATE PROCEDURE update_test_result(IN appointment_time DATETIME, IN doctor_id INT, IN test_code INT, IN test_datetime DATETIME, IN test_result VARCHAR(50), IN test_abnormality TINYINT(1))
BEGIN
	UPDATE test_for_visit tr
    SET tr.test_datetime = test_datetime, tr.test_result = test_result, tr.test_abnormality = test_abnormality
    WHERE tr.appointment_time = appointment_time AND tr.doctor_id = doctor_id AND tr.test_code = test_code;
END$
DELIMITER ;