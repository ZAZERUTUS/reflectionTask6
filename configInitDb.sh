#POSTGRES_DB="test"
#POSTGRES_USER="admin"
#POSTGRES_PASSWORD="admin"
#PGDATA="/var/lib/postgresql/data/pgdata"

#sudo chmod +w /docker-entrypoint-initdb.d/
function verifyConfig() {
  isInit=0

  file_path="/config.yml"

  while IFS= read -r line; do
    if [[ $line == *"initDb: true"* ]]
    then
      isInit=1
    fi
  done < "$file_path"

  echo $isInit
}

initDb=$(verifyConfig)
echo "START SCR"
echo "---$initDb"
if [[ $initDb == "0" ]]
then
  echo "START delete scr db"
#  psql -h "127.0.0.1" -p "5432" -U "admin" -d "test" -W "admin" -f "/tst/dockerPG/initDbScr/scr.sql"
  rm /docker-entrypoint-initdb.d/scr.sql
  echo "END delete scr db"
fi

#function verifyConfig() {
#  isInit=0
#  for str in $(cat config.yml | awk '{print}')
#  do
#    if [ $str == "initDb: true" ]
#    then
#      isInit=1
#    fi
#  done
#
#  echo isInit
#}

#mkdir -p /var/lib/postgresql/data/pgdata

#initdb -D /var/lib/postgresql/data/pgdata

#postgres -c max_connections=50 -c shared_buffers=1GB -c effective_cache_size=4GB -c work_mem=16MB -c maintenance_work_mem=512MB -c random_page_cost=1.1 -c temp_file_limit=10GB -c log_min_duration_statement=200ms -c idle_in_transaction_session_timeout=10s -c lock_timeout=1s -c statement_timeout=60s -c shared_preload_libraries=pg_stat_statements -c pg_stat_statements.max=10000 -c pg_stat_statements.track=all
