echo "Starting import of ny_cab_data_cab_trip_data_full.sql ............"

mysql --host=localhost --user=root --password=secret  trips < /data/ny_cab_data_cab_trip_data_full.sql;

echo "Import of ny_cab_data_cab_trip_data_full.sql complete ............"