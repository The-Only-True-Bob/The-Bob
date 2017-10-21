#!/usr/bin/env bash

# Make sure environment variables are defined on the server:
# CONFIG_FILE
# CONFIRMATION_CODE
# GROUP_ID
# TOKEN
# APP_ID
# APP_TOKEN

sed -i -e "s/group.confirmation.code=/group.confirmation.code=$CONFIRMATION_CODE/g" $CONFIG_FILE
sed -i -e "s/group.id=/group.id=$GROUP_ID/g" $CONFIG_FILE
sed -i -e "s/group.token=/group.token=$GROUP_TOKEN/g" $CONFIG_FILE
sed -i -e "s/app.id=/app.id=$APP_ID/g" $CONFIG_FILE
sed -i -e "s/app.token=/app.token=$APP_TOKEN/g" $CONFIG_FILE
