delete from flights;

insert into flights (flight_id, direction, departure_time, flight_time, seats, start_price, price, seats_num_available)
    values (1, 'to Lwiw', '12.12.2020 - 16:30', '12:23', 15, 50, 50, 15),
           (2, 'to Berlin', '23.09.2020 - 12:11', '03:44', 25, 30, 30, 25);