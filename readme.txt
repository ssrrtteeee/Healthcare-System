Employees/Nurses (Username, Password)
test, test
username, password
AlbC, goodPassword
Myk, newPassword
TrainQuest, wasdwasd123

Admin (Username, Password)
adminJW, adminJW
adminSR, adminSR
adminRS, adminRS
adminAK, adminAK
adminLR, adminLR

Test Query 1
SELECT 
    a.appointment_time, 
    a.doctor_id, 
    CONCAT(d.f_name, " ", d.l_name) AS doctor_name, 
    v.recording_nurse_id, 
    CONCAT(n.f_name, " ", n.l_name) AS nurse_name, 
    v.systolic_bp, 
    v.diastolic_bp,
    t.name,
    tv.test_result,
    v.initial_diagnosis,
    v.final_diagnosis
FROM 
    appointment a
JOIN 
    visit_details v ON a.appointment_time = v.appointment_time AND a.doctor_id = v.doctor_id
JOIN 
    doctor d ON a.doctor_id = d.id
JOIN 
    nurse n ON v.recording_nurse_id = n.id
LEFT OUTER JOIN
	test_for_visit tv ON a.appointment_time = tv.appointment_time AND a.doctor_id = tv.doctor_id
LEFT OUTER JOIN
	test t ON tv.test_code = t.test_code
WHERE 
    patient_id = 1;


Test Query 2
SELECT
    p.id,
    concat(p.f_name, " ", p.l_name) as patient_name,
    DATE(tv.test_datetime),
    COUNT(tv.test_code) AS total_tests
FROM
    patient p
JOIN 
    appointment a ON p.id = a.patient_id
JOIN
	test_for_visit tv ON a.appointment_time = tv.appointment_time AND a.doctor_id = tv.doctor_id
GROUP BY
    p.id, DATE(tv.test_datetime)
HAVING
    COUNT(tv.test_code) >= 2
ORDER BY
    p.id,
    DATE(tv.test_datetime);
