#!/bin/sh

mysql -u root --password=root -p UniPrinter -e "delete from lighter where endtime < date_sub(curdate(),interval 2 day)"
mysql -u root --password=root -p UniPrinter -e "delete from shell where endtime < date_sub(curdate(),interval 2 day)"
mysql -u root --password=root -p UniPrinter -e "delete from tshirt where endtime < date_sub(curdate(),interval 2 day)"
find /opt/apache-tomcat-8.0.36/webapps/ROOT/ -type f -mtime +2 -exec rm -f {} \;
