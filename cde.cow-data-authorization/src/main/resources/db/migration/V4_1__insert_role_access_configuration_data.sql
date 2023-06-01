-- devices
INSERT INTO role_access_configuration(role_id, method, path) VALUES('1', 'GET', '/cowdatavisualization/devices');
INSERT INTO role_access_configuration(role_id, method, path) VALUES('3', 'GET', '/cowdatavisualization/devices');

-- farms
INSERT INTO role_access_configuration(role_id, method, path) VALUES('1', 'GET', '/cowdatavisualization/farms');
INSERT INTO role_access_configuration(role_id, method, path) VALUES('3', 'GET', '/cowdatavisualization/farms');
INSERT INTO role_access_configuration(role_id, method, path) VALUES('1', 'PUT', '/cowdatavisualization/farms');
INSERT INTO role_access_configuration(role_id, method, path) VALUES('3', 'PUT', '/cowdatavisualization/farms');