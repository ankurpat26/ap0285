-- Insert data into ToolType table
INSERT INTO TOOL_TYPE (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES ('Ladder', 1.99, true, true, false);
INSERT INTO TOOL_TYPE (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES ('Chainsaw', 1.49, true, false, true);
INSERT INTO TOOL_TYPE (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES ('Jackhammer', 2.99, true, false, false);

-- Insert data into Tool table
INSERT INTO TOOL (tool_code, tool_type, brand, tool_type_id) VALUES ('CHNS', 'Chainsaw', 'Stihl', 'Chainsaw');
INSERT INTO TOOL (tool_code, tool_type, brand, tool_type_id) VALUES ('LADW', 'Ladder', 'Werner', 'Ladder');
INSERT INTO TOOL (tool_code, tool_type, brand, tool_type_id) VALUES ('JAKD', 'Jackhammer', 'DeWalt', 'Jackhammer');
INSERT INTO TOOL (tool_code, tool_type, brand, tool_type_id) VALUES ('JAKR', 'Jackhammer', 'Ridgid', 'Jackhammer');
